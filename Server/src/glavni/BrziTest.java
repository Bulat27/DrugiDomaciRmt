package glavni;

import java.util.GregorianCalendar;

public class BrziTest {
	private String status;
	private GregorianCalendar datum;
	
	public BrziTest(String status, GregorianCalendar datum) {
		super();
		this.status = status;
		this.datum = datum;
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
	
	
	
	
	
}