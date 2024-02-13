package it.progetto.energy.runner;

import it.progetto.energy.impl.model.UserRepository;
import it.progetto.energy.impl.repository.RoleAccessRepository;
import it.progetto.energy.impl.repository.UserAccessRepository;
import it.progetto.energy.persistence.repository.AddressRepository;
import it.progetto.energy.persistence.repository.ComuneRepository;
import it.progetto.energy.persistence.repository.CustomerRepository;
import it.progetto.energy.persistence.repository.InvoiceRepository;
import it.progetto.energy.persistence.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Runner implements ApplicationRunner {

	private final CustomerRepository customerRepository;
	private final InvoiceRepository invoiceRepository;
	private final ComuneRepository comuneRepository;
	private final ProvinciaRepository provinciaRepository;
	private final AddressRepository addressRepository;
	private final UserAccessRepository userAccessRepository;
	private final RoleAccessRepository roleAccessRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

//	@Qualifier("clienteDefault")
//	private final Cliente cliente;
//
//	@Qualifier("fatturaDefault")
//	private final Fattura fattura;

//	AggiornaAnniThread aggiornaAnniThread = new AggiornaAnniThread(customerRepository);

	@Override
	public void run(ApplicationArguments args) throws Exception {

//		clienteRepository.save(cliente);
//		fatturaRepository.save(fattura);


//		aggiornaAnniThread.run();

		/*
		 * IMPORTAZIONE PROVINCE
		 */
//		 String fileProvinceCSV = "csv/province-italiane.csv";
//		 File fileProvince = new ClassPathResource(fileProvinceCSV).getFile();
//		 CsvSchema ProvinceCSVSchema =
//		 CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
//		 CsvMapper mapper2 = new CsvMapper();
//		 MappingIterator<List<String>> valueReader2 =
//		 mapper2.reader(ProvinciaCSV.class).with(ProvinceCSVSchema)
//		 .readValues(fileProvince);
//
//		 for (Object o : valueReader2.readAll()) {
//		 Provincia prov = new Provincia();
//		 BeanUtils.copyProperties(o, prov);
//		 provinciaRepo.save(prov);
//		 }

		/*
		 * IMPORTAZIONE COMUNI
		 */
//		 String fileComuniCSV = "csv/comuni.csv";
//		 CsvSchema comuniCSVSchema =
//		 CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
//		 CsvMapper mapper1 = new CsvMapper();
//		 File fileComuni = new ClassPathResource(fileComuniCSV).getFile();
//		 MappingIterator<List<String>> valueReader1 =
//		 mapper1.reader(ComuneCorrettoCSV.class).with(comuniCSVSchema)
//		 .readValues(fileComuni);
//		 for (Object o : valueReader1.readAll()) {
//		 Comune com = new Comune();
//		 ComuneCorrettoCSV comCSV = (ComuneCorrettoCSV) o;
//		 BeanUtils.copyProperties(o, com);
//		 com.setProvincia(provinciaRepo.findBySiglaAllIgnoreCase(comCSV.getSiglaProvincia()));
//		 comuneRepo.save(com);
//		 }

		/*
		 * IMPORTAZIONE INDIRIZZI LEGALI
		 */
//		 String fileIndirizziLegCSV = "csv/indirizzi-legali.csv";
//		 CsvSchema indirizziLegCSVSchema =
//		 CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
//		 CsvMapper mapper3 = new CsvMapper();
//		 File fileIndirizziLeg = new ClassPathResource(fileIndirizziLegCSV).getFile();
//		 MappingIterator<List<String>> valueReader3 =
//		 mapper3.reader(IndirizziCSV.class).with(indirizziLegCSVSchema)
//		 .readValues(fileIndirizziLeg);
//		 for (Object o : valueReader3.readAll()) {
//		 IndirizzoLegale indiLeg = new IndirizzoLegale();
//		 IndirizziCSV comCSV = (IndirizziCSV) o;
//		 BeanUtils.copyProperties(o, indiLeg);
//		 String localita = comCSV.getLocalita();
//		 indiLeg.setComune(comuneRepo.findByNomeAllIgnoreCase(localita));
//		 indiLegRepo.save(indiLeg);
//		 }

		/*
		 * IMPORTAZIONE INDIRIZZI OPERATIVI
		 */
//		 String fileIndirizziOpCSV = "csv/indirizzi-operativi.csv";
//		 CsvSchema indirizziOpCSVSchema =
//		 CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
//		 CsvMapper mapper4 = new CsvMapper();
//		 File fileIndirizziOp = new ClassPathResource(fileIndirizziOpCSV).getFile();
//		 MappingIterator<List<String>> valueReader4 =
//		 mapper4.reader(IndirizziCSV.class).with(indirizziOpCSVSchema)
//		 .readValues(fileIndirizziOp);
//		 for (Object o : valueReader4.readAll()) {
//		 IndirizzoOperativo indiOp = new IndirizzoOperativo();
//		 IndirizziCSV comCSV = (IndirizziCSV) o;
//		 BeanUtils.copyProperties(o, indiOp);
//		 indiOp.setComune(comuneRepo.findByNomeAllIgnoreCase(comCSV.getLocalita()));
//		 indiOpRepo.save(indiOp);
//		 }

		/*
		 * INSERT DEFAULT USERS
		 */
//		RoleAccess roleAdmin = new RoleAccess();
//		roleAdmin.setRoleName(ERoleAccess.ROLE_ADMIN);
//		if(!roleAccessRepository.existsByRoleName(roleAdmin.getRoleName())){
//			roleAccessRepository.save(roleAdmin);
//		}
//
//		RoleAccess roleUser = new RoleAccess();
//		roleUser.setRoleName(ERoleAccess.ROLE_USER);
//		if(!roleAccessRepository.existsByRoleName(roleUser.getRoleName())){
//			roleAccessRepository.save(roleUser);
//		}
//
//		Set<RoleAccess> roleAccessSet = new HashSet<>(Set.of(roleAdmin, roleUser));
//
//		if(!userRepository.existsByUsernameIgnoreCase("user")) {
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
//		if(!userRepository.existsByUsernameIgnoreCase("Fedegrad")) {
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
