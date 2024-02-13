package it.progetto.energy.runner;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import it.progetto.energy.csv.ComuneCorrettoCSV;
import it.progetto.energy.csv.ProvinciaCSV;
import it.progetto.energy.impl.model.ERoleAccess;
import it.progetto.energy.impl.model.RoleAccess;
import it.progetto.energy.impl.model.User;
import it.progetto.energy.impl.repository.RoleAccessRepository;
import it.progetto.energy.impl.repository.UserRepository;
import it.progetto.energy.mapper.csvtoentiy.AddressCSVMapper;
import it.progetto.energy.mapper.csvtoentiy.ComuneCSVMapper;
import it.progetto.energy.mapper.csvtoentiy.ProvinciaCSVMapper;
import it.progetto.energy.persistence.entity.ComuneEntity;
import it.progetto.energy.persistence.entity.CustomerEntity;
import it.progetto.energy.persistence.entity.InvoiceEntity;
import it.progetto.energy.persistence.entity.ProvinciaEntity;
import it.progetto.energy.persistence.repository.AddressRepository;
import it.progetto.energy.persistence.repository.ComuneRepository;
import it.progetto.energy.persistence.repository.CustomerRepository;
import it.progetto.energy.persistence.repository.InvoiceRepository;
import it.progetto.energy.persistence.repository.ProvinciaRepository;
import it.progetto.energy.utils.AggiornaAnniThread;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class Runner implements ApplicationRunner {

	private static final String PATH_PROVINCE = "csv/province-italiane.csv";
	private static final String PATH_COMUNI = "csv/comuni.csv";
	private static final String PATH_INDIRIZZI = "csv/indirizzi.csv";

	private final CustomerRepository customerRepository;
	private final InvoiceRepository invoiceRepository;
	private final ComuneRepository comuneRepository;
	private final ProvinciaRepository provinciaRepository;
	private final AddressRepository addressRepository;
	private final RoleAccessRepository roleAccessRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final ProvinciaCSVMapper provinciaCSVMapper;
	private final ComuneCSVMapper comuneCSVMapper;
	private final AddressCSVMapper addressCSVMapper;

	@Qualifier("clienteDefault")
	private final CustomerEntity customer;

	@Qualifier("invoiceDefault")
	private final InvoiceEntity invoice;

	AggiornaAnniThread aggiornaAnniThread = new AggiornaAnniThread();

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		aggiornaAnniThread.run();

		if(customerRepository.existsById(1L)){
			CustomerEntity saved = customerRepository.save(customer);
			log.info("Customer id {} created", saved.getId());
		}

		if(invoiceRepository.existsByCustomer_Id(1L)){
			InvoiceEntity saved = invoiceRepository.save(invoice);
			log.info("Invoice id {} created", saved.getId());
		}

		/*
		 * IMPORTAZIONE PROVINCE
		 */
		File fileProvince = new ClassPathResource(PATH_PROVINCE).getFile();
		List<ProvinciaCSV> provinciaCSVList = extractPojoListFromFile(fileProvince, ProvinciaCSV.class);

		List<ProvinciaEntity> provinciaEntityList = provinciaCSVMapper
				.fromProvinciaCSVListToProvinciaEntityList(provinciaCSVList).stream()
				.filter(provinciaEntity -> !provinciaRepository.existsBySiglaAllIgnoreCase(provinciaEntity.getSigla()))
				.toList();

		provinciaRepository.saveAll(provinciaEntityList);

		log.info("{} Provincie added", provinciaEntityList.size());

		/*
		 * IMPORTAZIONE COMUNI
		 */
		File fileComuni = new ClassPathResource(PATH_COMUNI).getFile();
		List<ComuneCorrettoCSV> comuneCSVList = extractPojoListFromFile(fileComuni, ComuneCorrettoCSV.class);

		List<ComuneEntity> comuneEntityList = comuneCSVMapper.fromComuneCSVListToComuneEntityList(comuneCSVList).stream()
				.map(comuneEntity -> {
					ProvinciaEntity provincia = provinciaRepository
							.findBySiglaAllIgnoreCase(comuneEntity.getProvincia().getSigla())
							.stream()
							.findFirst()
							.orElse(null);
					comuneEntity.setProvincia(provincia);
					return comuneEntity;
				}).filter(comuneEntity -> !comuneRepository.existsByName(comuneEntity.getName()))
				.toList();

		comuneRepository.saveAll(comuneEntityList);
		log.info("{} Comuni added", comuneEntityList.size());

		/*
		 * IMPORTAZIONE INDIRIZZI
		 */
//		File fileIndirizzi = new ClassPathResource(PATH_INDIRIZZI).getFile();
//
//		List<AddressCSV> addressCSVList = extractPojoListFromFile(fileIndirizzi, AddressCSV.class);
//
//		List<AddressEntity> addressEntityList = addressCSVMapper.fromAddressCSVListToAddressEntityList(addressCSVList).stream()
//				.map(addressEntity -> {
//					addressEntity.setComune(comuneRepository
//							.findByNameAllIgnoreCase(addressEntity.getLocation()).stream()
//							.findFirst()
//							.orElse(null));
//					addressEntity.setCustomer(customerRepository
//							.findById(1L)
//							.orElse(null));
//					return addressEntity;
//				})
//				.toList();
//
//		addressRepository.saveAll(addressEntityList);

		/*
		 * INSERT DEFAULT USERS
		 */
		RoleAccess roleAdmin = new RoleAccess();
		roleAdmin.setRoleName(ERoleAccess.ROLE_ADMIN);
		if(!roleAccessRepository.existsByRoleName(roleAdmin.getRoleName())){
			roleAccessRepository.save(roleAdmin);
			log.info("Role {} added", roleAdmin.getRoleName());
		}

		RoleAccess roleUser = new RoleAccess();
		roleUser.setRoleName(ERoleAccess.ROLE_USER);
		if(!roleAccessRepository.existsByRoleName(roleUser.getRoleName())){
			roleAccessRepository.save(roleUser);
			log.info("Role {} added", roleUser.getRoleName());
		}

		Set<RoleAccess> roleAccessSet = new HashSet<>(Set.of(roleAdmin, roleUser));

		if(userRepository.existsByUsernameIgnoreCase("user")) {
			User userDefault = User.builder()
					.name("default")
					.surname("default")
					.username("user")
					.password(passwordEncoder.encode("123"))
					.email("user@email.com")
					.roles(Set.of(roleUser))
					.accountAttivo(true)
					.build();

			userRepository.save(userDefault);
			log.info("User {} created", userDefault.getUsername());
		}

		if(userRepository.existsByUsernameIgnoreCase("Fedegrad")) {
			User userAdmin = User.builder()
					.name("Federico")
					.surname("Fox")
					.username("Fedegrad")
					.password(passwordEncoder.encode("fox"))
					.email("federico.fox@email.com")
					.roles(roleAccessSet)
					.accountAttivo(true)
					.build();

			userRepository.save(userAdmin);
			log.info("User {} created", userAdmin.getUsername());
		}

	}

	private <T> List<T> extractPojoListFromFile(File file, Class<T> type) throws IOException {
		char separator = detectWordSeparator(file);

		CsvSchema csvSchema =
				CsvSchema.emptySchema().withColumnSeparator(separator).withHeader();

		try (MappingIterator<T> valueTabMapped = new CsvMapper()
				.readerWithTypedSchemaFor(type)
				.with(csvSchema)
				.readValues(file)) {

			return new ArrayList<>(valueTabMapped.readAll());
		} catch (IOException e) {
			throw new IOException("SOMETING WRONG: " + e.getMessage());
		}
	}

	private char detectWordSeparator(File file) throws IOException {
		String content = new String(Files.readAllBytes(file.toPath()));
		String[] split = content.split("");
		if(Arrays.asList(split).contains(";")){
			return ';';
		} else if(Arrays.asList(split).contains(",")) {
			return ',';
		} else {
			log.info("NO SEPARATOR DETECTED");
			return ' ';
		}
	}

}
