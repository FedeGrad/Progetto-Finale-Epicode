package it.progetto.energy.runner;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.progetto.energy.csv.ComuneCorrettoCSV;
import it.progetto.energy.csv.IndirizziCSV;
import it.progetto.energy.csv.ProvinciaCSV;
import it.progetto.energy.impl.ERole;
import it.progetto.energy.impl.Role;
import it.progetto.energy.impl.RoleRepository;
import it.progetto.energy.impl.User;
import it.progetto.energy.impl.UserRepository;
import it.progetto.energy.model.Comune;
import it.progetto.energy.model.IndirizzoLegale;
import it.progetto.energy.model.IndirizzoOperativo;
import it.progetto.energy.model.Provincia;
import it.progetto.energy.repository.ComuneRepository;
import it.progetto.energy.repository.IndirizzoLegaleRepository;
import it.progetto.energy.repository.IndirizzoOperativoRepository;
import it.progetto.energy.repository.ProvinciaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

//@Data
//@AllArgsConstructor
@Component
public class Runner implements ApplicationRunner {

	@Autowired
	ComuneRepository comuneRepo;
	@Autowired
	ProvinciaRepository provinciaRepo;
	@Autowired
	IndirizzoLegaleRepository indiLegRepo;
	@Autowired
	IndirizzoOperativoRepository indiOpRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	PasswordEncoder passEncod;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	@Qualifier("legaleUno") IndirizzoLegale indirizzoLegUno;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
/*
 * IMPORTAZIONE PROVINCE
 */
//		String fileProvinceCSV = "csv/province-italiane.csv";
//		File fileProvince = new ClassPathResource(fileProvinceCSV).getFile();
//		CsvSchema ProvinceCSVSchema = CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
//		CsvMapper mapper2 = new CsvMapper();
//		MappingIterator<List<String>> valueReader2 = mapper2.reader(ProvinciaCSV.class).with(ProvinceCSVSchema)
//				.readValues(fileProvince);
//
//		for (Object o : valueReader2.readAll()) {
//			Provincia prov = new Provincia();
//			BeanUtils.copyProperties(o, prov);
//			provinciaRepo.save(prov);
//		}
		/*
		 * IMPORTAZIONE COMUNI
		 */
//		String fileComuniCSV = "csv/comuni.csv";
//		CsvSchema comuniCSVSchema = CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
//		CsvMapper mapper1 = new CsvMapper();
//		File fileComuni = new ClassPathResource(fileComuniCSV).getFile();
//		MappingIterator<List<String>> valueReader1 = mapper1.reader(ComuneCorrettoCSV.class).with(comuniCSVSchema)
//				.readValues(fileComuni);
//		for (Object o : valueReader1.readAll()) {
//			Comune com = new Comune();
//			ComuneCorrettoCSV comCSV = (ComuneCorrettoCSV) o;
//			BeanUtils.copyProperties(o, com);
//			com.setProvincia(provinciaRepo.findBySiglaAllIgnoreCase(comCSV.getSiglaProvincia()));
//			comuneRepo.save(com);
//		}
		/*
		 * IMPORTAZIONE INDIRIZZI LEGALI
		 */
//		String fileIndirizziLegCSV = "csv/indirizzi-legali.csv";
//		CsvSchema indirizziLegCSVSchema = CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
//		CsvMapper mapper3 = new CsvMapper();
//		File fileIndirizziLeg = new ClassPathResource(fileIndirizziLegCSV).getFile();
//		MappingIterator<List<String>> valueReader3 = mapper3.reader(IndirizziCSV.class).with(indirizziLegCSVSchema)
//				.readValues(fileIndirizziLeg);
//		for (Object o : valueReader3.readAll()) {
//			IndirizzoLegale indiLeg = new IndirizzoLegale();
//			IndirizziCSV comCSV =  (IndirizziCSV) o;
//			BeanUtils.copyProperties(o, indiLeg);
//			String localita = comCSV.getLocalita();
//			indiLeg.setComune(comuneRepo.findByNomeAllIgnoreCase(localita));
//			indiLegRepo.save(indiLeg);
//		}
		/*
		 * IMPORTAZIONE INDIRIZZI OPERATIVI
		 */
//		String fileIndirizziOpCSV = "csv/indirizzi-operativi.csv";
//		CsvSchema indirizziOpCSVSchema = CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
//		CsvMapper mapper4 = new CsvMapper();
//		File fileIndirizziOp = new ClassPathResource(fileIndirizziOpCSV).getFile();
//		MappingIterator<List<String>> valueReader4 = mapper4.reader(IndirizziCSV.class).with(indirizziOpCSVSchema)
//				.readValues(fileIndirizziOp);
//		for (Object o : valueReader4.readAll()) {
//			IndirizzoOperativo indiOp = new IndirizzoOperativo();
//			IndirizziCSV comCSV =  (IndirizziCSV) o;
//			BeanUtils.copyProperties(o, indiOp);
//			indiOp.setComune(comuneRepo.findByNomeAllIgnoreCase(comCSV.getLocalita()));
//			indiOpRepo.save(indiOp);
//		}
/*
 * INSERIMENTO DELLA PARTE SICUREZZA
 */
//		Role admin = new Role();
//		admin.setRoleName(ERole.ROLE_ADMIN);
//		Role user = new Role();
//		user.setRoleName(ERole.ROLE_USER);
//		
//		User userAdmin = new User();
//		User userDefault = new User();
//		Set<Role> ruoli = new HashSet();
//		ruoli.add(admin);
//		userDefault.setUsername("user");
//		userDefault.setPassword(BCrypt.hashpw("123", BCrypt.gensalt()));
//		userDefault.setEmail("user@libero.it");
//		userDefault.getRoles().add(user);
//		userDefault.setAccountAttivo(true);
//		
//		userAdmin.setRoles(ruoli);
//		userAdmin.setUsername("federico");
//		userAdmin.setPassword(passEncod.encode("fox"));
//		userAdmin.setEmail("admin@libero.it");
//		userAdmin.setAccountAttivo(true);
//		userRepo.save(userAdmin);
//		userRepo.save(userDefault);
		
	}

}
