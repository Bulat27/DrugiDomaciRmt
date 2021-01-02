package glavni;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class TestSamoprocene implements Serializable{
	private String status;
	private GregorianCalendar datum;
//	private String izlazakIzNadzora;
	private int brojac;//broj uradjenih testova samoprocene, treba mi za izlazak iz nadzora
	
	public TestSamoprocene(String status, GregorianCalendar datum, int brojac) {
		super();
		this.status = status;
		this.datum = datum;
		this.brojac = brojac;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public GregorianCalendar getDatum() {
		return datum;
	}

	public void setDatum(GregorianCalendar datum) {
		this.datum = datum;
	}

//	public String getIzlazakIzNadzora() {
//		return izlazakIzNadzora;
//	}
//
//	public void setIzlazakIzNadzora(String izlazakIzNadzora) {
//		this.izlazakIzNadzora = izlazakIzNadzora;
//	}

	public int getBrojac() {
		return brojac;
	}

	public void setBrojac(int brojac) {
		this.brojac = brojac;
	}
	
	public void povecajBrojac() {
		brojac++;
	}
	
	
	
	
	
	
}
