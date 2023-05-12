package application;

public class Rivista extends Pubblicazione {
	private Periodicita periodicita;

	public Rivista(Long codice, String titolo, int annoPubblicazione, int numPagine, Periodicita periodicita) {
		super(codice, titolo, annoPubblicazione, numPagine);
		setPeriodicita(periodicita);
	}

	public Periodicita getPeriodicita() {
		return periodicita;
	}

	public void setPeriodicita(Periodicita periodicita) {
		this.periodicita = periodicita;
	}
}
