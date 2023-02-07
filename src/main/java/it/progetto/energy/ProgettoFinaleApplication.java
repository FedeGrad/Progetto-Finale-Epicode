package it.progetto.energy;

import it.progetto.energy.model.Operazione;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Arrays;

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
