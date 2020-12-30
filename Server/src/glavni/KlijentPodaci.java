package glavni;

public class KlijentPodaci {
	
	String username;
	String lozinka;
	String imeIPrezime;
	String pol;
	String email;
	
	public KlijentPodaci(String username, String lozinka, String imeIPrezime, String pol, String email) {
		super();
		this.username = username;
		this.lozinka = lozinka;
		this.imeIPrezime = imeIPrezime;
		this.pol = pol;
		this.email = email;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof KlijentPodaci)) {
			return false;
		}
		KlijentPodaci k = (KlijentPodaci) (obj);
		return username.equals(k.username);// Glupo je razlikovati ih po lozinci, nesigurno je, a po ime i prezime moze da bude isto, takod a cemo za sad po username-u
	}
	
	
	
	
}
