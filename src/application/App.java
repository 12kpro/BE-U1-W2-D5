package application;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(App.class);
		// TODO Auto-generated method stub
		Archivio catalogo = new Archivio();

		catalogo.aggiungiPubblicazione(
				new Libro(8845290050L, "Il signore degli anelli", 2002, 1000, "Tolkien", "Fantasy"));
		catalogo.aggiungiPubblicazione(
				new Libro(8845290051L, "Il signore degli asinelli", 2002, 1000, "Tolkien", "Fantasy"));
		catalogo.aggiungiPubblicazione(
				new Libro(8845290052L, "Il signore degli muli", 2002, 1000, "J.R.R.Tolkien", "Fantasy"));
		catalogo.aggiungiPubblicazione(new Rivista(8845290053L, "Quattroruote", 2022, 110, Periodicita.MENSILE));
		catalogo.aggiungiPubblicazione(new Rivista(8845290054L, "LinuxMagazine", 2022, 250, Periodicita.MENSILE));

		try {
			catalogo.salvaArchivio();

			catalogo.caricaArchivio();

			List<Libro> ricercaPerAutore = catalogo.cercaPerAutore("Tolkien");
			Pubblicazione cercaPerIsbn = catalogo.cercaPerIsbn(8845290052L);
			logger.info("{} con ISBN: {}", cercaPerIsbn.getTitolo(), cercaPerIsbn.getCodice());
			List<Pubblicazione> cercaPerAnnoPubblicazione = catalogo.cercaPerAnnoPubblicazione(2002);
			ricercaPerAutore.forEach(p -> logger.info("{} dell'autore: {}", p.getTitolo(), p.getAutore()));
			cercaPerAnnoPubblicazione
					.forEach(p -> logger.info("{} pubblicato nel: {}", p.getTitolo(), p.getAnnoPubblicazione()));

			catalogo.rimuoviPublicazione(8845290052L);
		} catch (IOException e) {
			logger.error("Errore durante la lettura/scrittura", e);
		}
	}

}
