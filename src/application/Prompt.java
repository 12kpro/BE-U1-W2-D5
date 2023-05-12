package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Prompt {
    public static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Archivio catalogo = new Archivio();

        Scanner input = new Scanner(System.in);
        String options[] = { "0 Per Uscire", "1 Aggiungi libro", "2 Aggiungi Rivista", "3 Salva Archivio su disco", "4 Leggi Archivio da disco" , "5 Cerca per ISBN", "6 Cerca per Autore", "7 Cerca per anno pubblicazione","8 Rimuovi pubblicazione per ISBN","9 Carica dati di esempio"};
        Long codice;
        String titolo;
        String autore;
        String genere;
        int annoPubblicazione;
        int numPagine;
        Periodicita periodicita;
        choice: while (true) {
            try {
                System.out.println("Seleziona una delle seguenti opzioni: ");
                System.out.println(Arrays.asList(options));
                int option = Math.abs(Integer.parseInt(input.nextLine()));
                switch (option) {
                    case 0:
                        input.close();
                        break choice;
                    case 1:
                        System.out.print("Inserisci ISBN:");
                        codice = Math.abs(Long.parseLong(input.nextLine()));
                        System.out.print("Inserisci titolo:");
                        titolo = input.nextLine();
                        System.out.print("Inserisci Anno Pubblicazione:");
                        annoPubblicazione = Math.abs(Integer.parseInt(input.nextLine()));
                        System.out.print("Inserisci Numero Pagine:");
                        numPagine = Math.abs(Integer.parseInt(input.nextLine()));
                        System.out.print("Inserisci Autore:");
                        autore = input.nextLine();
                        System.out.print("Inserisci Genere:");
                        genere = input.nextLine();
                        catalogo.aggiungiPubblicazione(new Libro(codice,titolo,annoPubblicazione,numPagine,autore,genere));
                        break;
                    case 2:
                        String periodicitaOpt[] = { "0: SETTIMANALE ", "1: MENSILE", "2 SEMESTRALE"};
                        System.out.print("Inserisci ISBN:");
                        codice = Math.abs(Long.parseLong(input.nextLine()));
                        System.out.print("Inserisci titolo:");
                        titolo = input.nextLine();
                        System.out.print("Inserisci Anno Pubblicazione:");
                        annoPubblicazione = Math.abs(Integer.parseInt(input.nextLine()));
                        System.out.print("Inserisci Numero Pagine:");
                        numPagine = Math.abs(Integer.parseInt(input.nextLine()));
                        System.out.print("Inserisci Periodicità:");
                        periodicitaLoop: while(true){
                            System.out.println(Arrays.asList(periodicitaOpt));
                            int periodicitaSel = Math.abs(Integer.parseInt(input.nextLine()));

                            switch (periodicitaSel){
                                case 0:
                                 periodicita = Periodicita.SETTIMANALE;
                                 break periodicitaLoop;
                                case 1:
                                    periodicita = Periodicita.MENSILE;
                                    break periodicitaLoop;
                                case 2:
                                    periodicita = Periodicita.SEMESTRALE;
                                    break periodicitaLoop;
                                default:
                                    System.out.println("L'opzione inserita " + periodicitaSel + " non è valida!");
                                    break;
                            }
                        }

                        catalogo.aggiungiPubblicazione(new Rivista(codice,titolo,annoPubblicazione,numPagine,periodicita));
                        break;
                    case 3:
                        if(catalogo.getArchivio().size() > 0) {
                            catalogo.salvaArchivio();
                        }else{
                            logger.info("Nessuna pubblicazzione da salvare");
                        }
                        break;
                    case 4:
                        catalogo.caricaArchivio();
                        break;
                    case 5:
                        System.out.print("Inserisci ISBN:");
                        codice = Math.abs(Long.parseLong(input.nextLine()));
                        Pubblicazione p = catalogo.cercaPerIsbn(codice);
                        logger.info("{} con ISBN: {}", p.getTitolo(), p.getCodice());
                        break;
                    case 6:
                        System.out.print("Inserisci Autore:");
                        autore = input.nextLine();
                        catalogo.cercaPerAutore(autore).forEach(pAut -> logger.info("{} dell'autore: {}", pAut.getTitolo(), pAut.getAutore()));
                        break;
                    case 7:
                        System.out.print("Inserisci Anno di pubblicazione:");
                        annoPubblicazione = Math.abs(Integer.parseInt(input.nextLine()));
                        System.out.print("Inserisci Numero Pagine:");
                        catalogo.cercaPerAnnoPubblicazione(annoPubblicazione).forEach(pAnno -> logger.info("{} pubblicato nel: {}", pAnno.getTitolo(), pAnno.getAnnoPubblicazione()));
                        break;
                    case 8:
                        System.out.print("Inserisci ISBN:");
                        codice = Math.abs(Long.parseLong(input.nextLine()));
                        catalogo.rimuoviPublicazione(codice);
                        break;
                    case 9:
                        catalogo.aggiungiPubblicazione(
                                new Libro(8845290050L, "Il signore degli anelli", 2002, 1000, "Tolkien", "Fantasy"));
                        catalogo.aggiungiPubblicazione(
                                new Libro(8845290051L, "Il signore degli asinelli", 2002, 1000, "Tolkien", "Fantasy"));
                        catalogo.aggiungiPubblicazione(
                                new Libro(8845290052L, "Il signore degli muli", 2002, 1000, "J.R.R.Tolkien", "Fantasy"));
                        catalogo.aggiungiPubblicazione(new Rivista(8845290053L, "Quattroruote", 2022, 110, Periodicita.MENSILE));
                        catalogo.aggiungiPubblicazione(new Rivista(8845290054L, "LinuxMagazine", 2022, 250, Periodicita.MENSILE));
                        break;
                    default:
                        System.out.println("L'opzione inserita " + option + " non è valida!");
                        break;
                }
            } catch (NumberFormatException e) {
                logger.info("Devi inserire un numero intero positivo!!");
            } catch (IOException e) {
                logger.error("Errore durante la lettura/scrittura {}", e.getMessage());
        }
    }
}}
