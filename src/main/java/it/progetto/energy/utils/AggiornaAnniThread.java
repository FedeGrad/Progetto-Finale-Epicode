package it.progetto.energy.utils;

import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.persistence.entity.Cliente;
import it.progetto.energy.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;

@Slf4j
@RequiredArgsConstructor
public class AggiornaAnniThread {

	private final CustomerRepository clienteRepo;

	class TassaAnnuale extends TimerTask {
		@Override
		public void run() {
			long minuto = 10000*60;
			long ora = minuto*60;
			long giorno = ora*24;
			long anno = giorno*365;
			try {
				List<Cliente> clienti = clienteRepo.findAll();
				if(clienti == null) {
					log.info("No One Client");
				} else {
					Timer timer = new Timer();
					timer.schedule(new TassaAnnuale(), anno);

					clienti.forEach(cliente -> cliente.setAnni(LocalDate.now().getYear()));
					log.info("importo annuo");
				}
			} catch (Exception e){
				throw new NotUpdatableException(ERROR_ONE); //TODO
			}
		}
	}

}
