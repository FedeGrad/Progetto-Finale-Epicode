package it.progetto.energy.utils;

import it.progetto.energy.persistence.entity.CustomerEntity;
import it.progetto.energy.persistence.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class AggiornaAnniThread extends TimerTask{
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public void run() {
		long minuto = 10000*60;
		long ora = minuto*60;
		long giorno = ora*24;
		long anno = giorno*365;
		List<CustomerEntity> clienti = customerRepository.findAll();

		if(clienti.isEmpty()) {
			log.info("No One Client");
		} else {
			new Timer().schedule(new AggiornaAnniThread(), anno);

			clienti.forEach(cliente -> cliente.setAge(LocalDate.now().getYear()));
			log.info("importo annuo");
		}
	}

}
