package it.progetto.energy.thread;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.progetto.energy.model.ClientDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import it.progetto.energy.repository.ClienteRepository;

@Slf4j
public class AggiornaAnniThread {

	@Autowired
	ClienteRepository clienteRepo;

	class TassaAnnuale extends TimerTask {
		@Override
		public void run() {
			long minuto = 10000*60;
			long ora = minuto*60;
			long giorno = ora*24;
			long anno = giorno*365;
			try {
				List<ClientDomain> clienti = (List<ClientDomain>) clienteRepo.findAll();
				if(clienti == null) {
					throw new NullPointerException("lista vuota");
				} else {
					Timer timer = new Timer();
					timer.schedule(new TassaAnnuale(), anno);
					//TODO SETTARE GLI ANNI
//					clienti.stream().map(cliente -> cliente.setAnni());
					log.info("importo annuo");
				}
			} catch (NullPointerException e){
				log.info("errore " + e.getMessage());
			}
			catch (Exception e){
				log.info(e.getMessage());
				e.printStackTrace();
			}
		}
	}


}
