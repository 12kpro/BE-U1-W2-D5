package application;

public class Libro extends Pubblicazione {
	private String autore;
	private String genere;

	public Libro(Long codice, String titolo, int annoPubblicazione, int numPagine, String autore, String genere) {
		super(codice, titolo, annoPubblicazione, numPagine);
		setAutore(autore);
		setGenere(genere);
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}
}
