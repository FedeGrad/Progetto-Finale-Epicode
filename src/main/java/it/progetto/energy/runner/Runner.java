package it.progetto.energy.runner;

import it.progetto.energy.business.CsvExtractor;
import it.progetto.energy.csv.AddressCSV;
import it.progetto.energy.csv.ComuneCorrettoCSV;
import it.progetto.energy.csv.ProvinciaCSV;
import it.progetto.energy.mapper.csvtoentiy.AddressCSVMapper;
import it.progetto.energy.mapper.csvtoentiy.ComuneCSVMapper;
import it.progetto.energy.mapper.csvtoentiy.ProvinciaCSVMapper;
import it.progetto.energy.persistence.entity.AddressEntity;
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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
	//	private final RoleAccessRepository roleAccessRepository;
//	private final PasswordEncoder passwordEncoder;
//	private final UserRepository userRepository;
	private final ProvinciaCSVMapper provinciaCSVMapper;
	private final ComuneCSVMapper comuneCSVMapper;
	private final AddressCSVMapper addressCSVMapper;
	private final CsvExtractor csvExtractor;

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
		List<ProvinciaCSV> provinciaCSVList = csvExtractor
				.extractDataFromFileToListCsv(PATH_PROVINCE, ProvinciaCSV.class);

		List<ProvinciaEntity> provinciaEntityList = provinciaCSVMapper
				.fromProvinciaCSVListToProvinciaEntityList(provinciaCSVList).stream()
				.filter(provinciaEntity -> !provinciaRepository.existsBySiglaAllIgnoreCase(provinciaEntity.getSigla()))
				.toList();

		provinciaRepository.saveAll(provinciaEntityList);
		log.info("{} Provincie added", provinciaEntityList.size());

		/*
		 * IMPORTAZIONE COMUNI
		 */
		List<ComuneCorrettoCSV> comuneCSVList = csvExtractor
				.extractDataFromFileToListCsv(PATH_COMUNI, ComuneCorrettoCSV.class);

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
		List<AddressCSV> addressCSVList = csvExtractor.extractDataFromFileToListCsv(PATH_INDIRIZZI, AddressCSV.class);
		List<AddressEntity> addressEntityList = new ArrayList<>(20);
		if(!addressRepository.existsById(1L)) {
			addressEntityList = addressCSVMapper.fromAddressCSVListToAddressEntityList(addressCSVList).stream()
					.map(addressEntity -> {
						addressEntity.setComune(comuneRepository
								.findByNameAllIgnoreCase(addressEntity.getLocation()).stream()
								.findFirst()
								.orElse(null));
						addressEntity.setCustomer(customerRepository
								.findById(1L)
								.orElse(null));
						return addressEntity;
					})
					.toList();

			addressRepository.saveAll(addressEntityList);
		}
		log.info("{} Address added", addressEntityList.size());

		/*
		 * INSERT DEFAULT USERS
		 */
//		RoleAccess roleAdmin = new RoleAccess();
//		roleAdmin.setRoleName(ERoleAccess.ROLE_ADMIN);
//		if(!roleAccessRepository.existsByRoleName(roleAdmin.getRoleName())){
//			roleAccessRepository.save(roleAdmin);
//			log.info("Role {} added", roleAdmin.getRoleName());
//		}
//
//		RoleAccess roleUser = new RoleAccess();
//		roleUser.setRoleName(ERoleAccess.ROLE_USER);
//		if(!roleAccessRepository.existsByRoleName(roleUser.getRoleName())){
//			roleAccessRepository.save(roleUser);
//			log.info("Role {} added", roleUser.getRoleName());
//		}
//
//		Set<RoleAccess> roleAccessSet = new HashSet<>(Set.of(roleAdmin, roleUser));
//
//		if(userRepository.existsByUsernameIgnoreCase("user")) {
//			User userDefault = User.builder()
//					.name("default")
//					.surname("default")
//					.username("user")
//					.password(passwordEncoder.encode("123"))
//					.email("user@email.com")
//					.roles(Set.of(roleUser))
//					.accountAttivo(true)
//					.build();
//
//			userRepository.save(userDefault);
//			log.info("User {} created", userDefault.getUsername());
//		}
//
//		if(userRepository.existsByUsernameIgnoreCase("Fedegrad")) {
//			User userAdmin = User.builder()
//					.name("Federico")
//					.surname("Fox")
//					.username("Fedegrad")
//					.password(passwordEncoder.encode("fox"))
//					.email("federico.fox@email.com")
//					.roles(roleAccessSet)
//					.accountAttivo(true)
//					.build();
//
//			userRepository.save(userAdmin);
//			log.info("User {} created", userAdmin.getUsername());
//		}

	}

}
