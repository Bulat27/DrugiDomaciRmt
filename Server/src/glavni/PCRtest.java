package glavni;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class PCRtest implements Serializable{
	
	private String status;
	private GregorianCalendar datum;
	private String stanje;
	
	public PCRtest(String status, GregorianCalendar datum, String stanje) {
		super();
		this.status = status;
		this.datum = datum;
		this.stanje = stanje;
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

	public String getStanje() {
		return stanje;
	}

	public void setStanje(String stanje) {
		this.stanje = stanje;
	}
	
	
	
}
