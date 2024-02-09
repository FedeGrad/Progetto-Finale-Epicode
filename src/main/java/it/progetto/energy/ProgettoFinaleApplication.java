package it.progetto.energy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * L'annotazione @SpringBootApplication è comprensiva di:
 * @Configuration (annota come classe di configurazione che gestisce i vari BEAN)
 * @ComponentScan (ricerca tutte le classi annotate con @Component e @Configuration )
 */
@SpringBootApplication
public class ProgettoFinaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgettoFinaleApplication.class, args);
	}

}
