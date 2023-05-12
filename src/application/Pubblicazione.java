package application;

public abstract class Pubblicazione {
	protected Long codice;
	protected String titolo;
	protected int annoPubblicazione;
	protected int numPagine;

	public Pubblicazione(Long codice, String titolo, int annoPubblicazione, int mumPagine) {
		setTitolo(titolo);
		setAnnoPubblicazione(annoPubblicazione);
		setNumPagine(numPagine);
		setCodice(codice);
	}

	public void setCodice(Long codice) {
		this.codice = codice;
	}

	public long getCodice() {
		return codice;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public int getAnnoPubblicazione() {
		return annoPubblicazione;
	}

	public void setAnnoPubblicazione(int annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}

	public int getNumPagine() {
		return numPagine;
	}

	public void setNumPagine(int numPagine) {
		this.numPagine = numPagine;
	}
}
