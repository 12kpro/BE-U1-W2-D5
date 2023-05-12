package application;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Archivio {
    private static File FILE = new File("archivio.csv");
    private Map<Long, Pubblicazione> archivio;
    Logger logger = LoggerFactory.getLogger(Archivio.class);

    public Archivio() {
        this.archivio = new HashMap<Long, Pubblicazione>();
    }
    public Map<Long, Pubblicazione> getArchivio() {
        return archivio;
    }
    public void aggiungiPubblicazione(Pubblicazione p) {
        Pubblicazione pubblicazioneAggiunta = archivio.put(p.getCodice(), p);
        if ( pubblicazioneAggiunta == null) {
            logger.info("{} aggiunto in archivio con ISBN: {}", p.getTitolo(), p.getCodice());
        } else {
            logger.info("Titolo: {} ISBN: {} aggiornato con successo", p.getTitolo(), p.getCodice());
        }
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
        if (FILE.delete()) {
            logger.info("File cancellato con successo!");
        }
        String record = "";
        for (Pubblicazione p : archivio.values()) {
            if (p instanceof Libro) {
                Libro l = (Libro) p;
                record += String.format("%s,%d,%s,%d,%d,%s,%s%n", Libro.class.getSimpleName(), l.getCodice(), l.getTitolo(),
                        l.getAnnoPubblicazione(), l.getNumPagine(), l.getAutore(), l.getGenere());
            } else if (p instanceof Rivista) {
                Rivista r = (Rivista) p;
                record += String.format("%s,%d,%s,%d,%d,%s%n", Rivista.class.getSimpleName(), r.getCodice(), r.getTitolo(),
                        r.getAnnoPubblicazione(), r.getNumPagine(), r.getPeriodicita());
            }
        }
        FileUtils.writeStringToFile(FILE, record, "UTF-8", true);
        logger.info("Dati salvati correttamente sul file " + FILE);
    }

    public void caricaArchivio() throws IOException {
        if (!FILE.exists()) {
            throw new IOException("File non esistente");
        }
        archivio.clear();
        logger.info("Dati letti correttamente dal file ");
        FileUtils.readLines(FILE, "UTF-8").forEach(p -> {
            String[] r = p.split(",");
            if (r[0].equals("Libro")) {
                this.aggiungiPubblicazione(new Libro(Long.parseLong(r[1]), r[2], Integer.parseInt(r[3]),
                        Integer.parseInt(r[4]), r[5], r[6]));
            } else if (r[0].equals("Rivista")) {
                this.aggiungiPubblicazione(new Rivista(Long.parseLong(r[1]), r[2], Integer.parseInt(r[3]),
                        Integer.parseInt(r[4]), Periodicita.valueOf(r[5])));
            }
        });
    }

}
/*
 * NOTE https://sematext.com/blog/slf4j-tutorial/ // Registrare un messaggio con
 * livello debug e degli argomenti
 * logger.debug("Questo è un messaggio di livello debug con {} argomenti", 2);
 */