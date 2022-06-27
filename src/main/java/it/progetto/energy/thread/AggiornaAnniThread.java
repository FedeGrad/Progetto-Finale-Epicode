package it.progetto.energy.thread;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.progetto.energy.model.Cliente;
import it.progetto.energy.repository.ClienteRepository;
import it.progetto.energy.service.ClienteService;

public class AggiornaAnniThread extends Thread{

	@Autowired
	ClienteRepository clienteRepo;

	@Override
	public void run() {
		List<Cliente> clienti = clienteRepo.findByNomeContattoContains("Luca");
		if(clienti != null) {
			for (Cliente cliente : clienti) {
				cliente.setAnni(Period.between(cliente.getDataDiNascita(), LocalDate.now()).getYears());
				clienteRepo.save(cliente);
			}
		} else {
			throw new NullPointerException("porcoddio");
		}
		try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
