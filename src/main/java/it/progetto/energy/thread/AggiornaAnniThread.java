package it.progetto.energy.thread;

import org.springframework.beans.factory.annotation.Autowired;

import it.progetto.energy.model.Cliente;
import it.progetto.energy.service.ClienteService;

public class AggiornaAnniThread extends Thread{

	@Autowired
	ClienteService clienteServ;
	
	@Override
	public void run() {
		clienteServ.aggiornaDataNascita();
	}
	
	

}
