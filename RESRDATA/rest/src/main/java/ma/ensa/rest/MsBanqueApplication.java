package ma.ensa.rest;

import jakarta.transaction.Transactional;
import ma.ensa.rest.entities.Client;
import ma.ensa.rest.entities.Compte;
import ma.ensa.rest.entities.TypeCompte;
import ma.ensa.rest.repositories.ClientRepository;
import ma.ensa.rest.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Date;

@SpringBootApplication
public class MsBanqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBanqueApplication.class, args);
	}
	@Transactional
	@Bean
	CommandLineRunner start(CompteRepository compteRepository, ClientRepository clientRepository,
							RepositoryRestConfiguration restConfiguration) {
		return args -> {
			// Expose l'ID pour les entités via Spring Data REST
			restConfiguration.exposeIdsFor(Compte.class);

			// Création des clients
			Client c1 = clientRepository.save(new Client(null, "amal", null));
			Client c2 = clientRepository.save(new Client(null, "hana", null));

			// Création des comptes
			compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c1));
			compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, c2));
			compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c1));

			// Affichage des comptes créés
			compteRepository.findAll().forEach(c -> {
				System.out.println(c.toString());
			});
		};
	}
}