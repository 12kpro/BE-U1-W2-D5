package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Archivio {
	private static File FILE = new File("archivio.txt");
	private Map<Long, Pubblicazione> archivio;
	Logger logger = LoggerFactory.getLogger(Archivio.class);

	public Archivio() {
		this.archivio = new HashMap<Long, Pubblicazione>();
	}

	public void aggiungiPubblicazione(Pubblicazione p) {

		if (archivio.put(p.getCodice(), p) == null)
			logger.info("{} aggiunto in archivio con ISBN: {}", p.getTitolo(), p.getCodice());

	}

	public void rimuoviPublicazione(Long isbn) {
		Pubblicazione pubblicazioneRimossa = archivio.remove(isbn);
		if (pubblicazioneRimossa != null)
			logger.info("{} rimosso dall'archivio con ISBN: {}", pubblicazioneRimossa.getTitolo(),
					pubblicazioneRimossa.getCodice());
	}

	public Pubblicazione cercaPerIsbn(Long isbn) {
		return archivio.get(isbn);
	}

	// map.values().stream() to get a stream of all the values in the map
	public List<Pubblicazione> cercaPerAnnoPubblicazione(int annoPubblicazione) {
		return archivio.values().stream().filter(p -> p.getAnnoPubblicazione() == annoPubblicazione).toList();
	}

	public List<Libro> cercaPerAutore(String autore) {
		return archivio.values().stream().filter(p -> p instanceof Libro).map(p -> (Libro) p)
				.filter(l -> autore.equals(l.getAutore())).toList();
	}

	public void salvaArchivio() throws IOException {
		for (Pubblicazione p : archivio.values()) {
			String record = "";
			if (p instanceof Libro) {
				Libro l = (Libro) p;
				record += Libro.class.getSimpleName() + "," + l.getCodice() + "," + l.getTitolo() + ","
						+ l.getAnnoPubblicazione() + "," + l.getNumPagine() + "," + l.getAutore() + "," + l.getGenere()
						+ System.lineSeparator();
			} else if (p instanceof Rivista) {
				Rivista r = (Rivista) p;
				record += Rivista.class.getSimpleName() + "," + r.getCodice() + "," + r.getTitolo() + ","
						+ r.getAnnoPubblicazione() + "," + r.getNumPagine() + "," + r.getPeriodicita()
						+ System.lineSeparator();
			}
			FileUtils.writeStringToFile(FILE, record, "UTF-8", true);
			logger.info("Dati salvati correttamente sul file " + FILE);

		}
	}

	public void caricaArchivio() throws IOException {
		archivio.clear();
		List<String> readRecords = new ArrayList<String>();
		readRecords = FileUtils.readLines(FILE, "UTF-8");
		for (String s : readRecords) {
			String[] r = s.split(",");
			if (r[0] == "Libro") {
				this.aggiungiPubblicazione(new Libro(Long.parseLong(r[1]), r[2], Integer.parseInt(r[2]),
						Integer.parseInt(r[3]), r[4], r[5]));
			} else if (r[0] == "Rivista") {
				this.aggiungiPubblicazione(new Rivista(Long.parseLong(r[1]), r[2], Integer.parseInt(r[2]),
						Integer.parseInt(r[3]), Periodicita.valueOf(r[4])));
			}
		}
	}

}
/*
 * NOTE https://sematext.com/blog/slf4j-tutorial/ // Registrare un messaggio con
 * livello debug e degli argomenti
 * logger.debug("Questo è un messaggio di livello debug con {} argomenti", 2);
 */