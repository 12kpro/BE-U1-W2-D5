package application;

public class Rivista extends Pubblicazione {
	private Periodicita periodicita;

	public Rivista(Long codice, String titolo, int annoPubblicazione, int mumPagine, Periodicita periodicita) {
		super(codice, titolo, annoPubblicazione, mumPagine);
		setPeriodicita(periodicita);
	}

	public Periodicita getPeriodicita() {
		return periodicita;
	}

	public void setPeriodicita(Periodicita periodicita) {
		this.periodicita = periodicita;
	}
}
