package glavniPaket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class Klijent {
	
    static Socket soketZaKomunikaciju=null;
	static BufferedReader ulazniTok = null;
	static PrintStream izlazniTok = null;
	static BufferedReader tastatura = null;
	static ObjectOutputStream izlazniZaObjekte=null;
	static ObjectInputStream ulazniZaObjekte=null;
	
	public static void main(String[] args) {
		try {
			soketZaKomunikaciju = new Socket("localhost", 9027);
			
			ulazniTok= new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			izlazniTok = new PrintStream(soketZaKomunikaciju.getOutputStream());
			izlazniZaObjekte = new ObjectOutputStream(soketZaKomunikaciju.getOutputStream());
			ulazniZaObjekte = new ObjectInputStream(soketZaKomunikaciju.getInputStream());
			
			tastatura = new BufferedReader(new InputStreamReader(System.in));
			
			boolean kraj=false;
			//za sad je ideja da se prvo uloguje ili registruje a posle u beskonacnom while-u mu iskacu nove opcije koje oce da bira
			while(!kraj) {
			System.out.println("Izaberite jednu od sledecih opcija unosom odgovarajuceg karaktera:");
			System.out.println("1-Prijavite se");
			System.out.println("2-Ulogujte se");
			System.out.println("3-Prekid komunikacije");
			String prvaOpcija = tastatura.readLine();
			switch (prvaOpcija) {
			case "1":
				izlazniTok.println(prvaOpcija);
				prijava();
				kraj=true;
				break;
			case "2":
				//MORA PRINTLN, NE MOZE SAMO PRINT, NE POPIJE GA LEPO, VIDI TO!
				izlazniTok.println(prvaOpcija);
				logovanje();
				kraj=true;
				break;
			case "3":
			izlazniTok.println(prvaOpcija);
			System.out.println("Dovidjenja");
			soketZaKomunikaciju.close();
			return;
			// vidi da li ovo skroz prekida izvrsavanje
			default:
				System.out.println("Molimo Vas izaberite validnu opciju");
				break;
			}
			
			}
			
			String admin = ulazniTok.readLine();
			if(admin.equals("admin")) {
				//System.out.println("Prosli smo kao admin");
				adminoveOpcije();
			}else {
				
//			System.out.println(ulazniTok.readLine());
			System.out.println("Ulogovani ste na svoj nalog");
			//ovde ce biti vise opcija
			kraj =false;
			// vidi da li ce ovde biti beskonacni while pa gde ce da ga vraca, koje sve opcije ima i to
			//NA KRAJU CES GA VRACATI STALNO NA OVAJ MENI, ZNACI OVDE NEGDE CE BESKONACNI WHILE!!!!
			while(!kraj) {
			System.out.println("Izaberite neku od sledecih opcija:");
			System.out.println("1-Test za samoprocenu");
			System.out.println("2-Podaci o poslednjem testu samoprocene");
			System.out.println("3-Prekid komunikacije");
			System.out.println("4-Podaci o poslednjem brzom testu");
			System.out.println("5-Podaci o poslednjem PCR testu");
			String drugaOpcija=tastatura.readLine();
			switch (drugaOpcija) {
			case "1":
				izlazniTok.println(drugaOpcija);//Ni ne mora svaki put ovako da se salje, jer je svakako mora poslati, moze u opstem slucaju da posalje,a  tek onda case-evi
				testSamoprocene();
				kraj=true;
				break;
			case "2":
				//MORA PRINTLN, NE MOZE SAMO PRINT, NE POPIJE GA LEPO, VIDI TO!
				izlazniTok.println(drugaOpcija);
				pregledPoslednjegSamoprocene();
				kraj=true;
				break;
			case "3":
			izlazniTok.println(drugaOpcija);
			System.out.println("Dovidjenja");
			soketZaKomunikaciju.close();
			return;
			case "4":
				izlazniTok.println(drugaOpcija);
				pregledPoslednjegBrzog();
				kraj=true;
				break;
			case "5":
				izlazniTok.println(drugaOpcija);
			    pregledPoslednjegPCR();
			    kraj=true;//Skloni posle ove samo, mozda cu time da resim to da uvek ostaje vamo i da bira opcije
			    break;
			// vidi da li ovo skroz prekida izvrsavanje
			default:
				System.out.println("Molimo Vas izaberite validnu opciju");
				break;// mislim da ovaj break nije ni potreban
			}
			}
			
			
		}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

private static void adminoveOpcije() throws IOException {
		//prvo treba odma da mu se ispisu sve liste koje ima u onom zahtevu, to cu posle
		System.out.println("Dobrodosli Admine!");
		boolean kraj=false;
		while(!kraj) {
			System.out.println("Izaberite neku od sledecih opcija:");
			System.out.println("1-Lista svih korisnika i njihovo stanje");
			System.out.println("2-Lista pozitivnih korisnika");
			System.out.println("3-Lista negativnih korisnika");
			System.out.println("4-Lista korisnika pod nadzorom");
			System.out.println("5-Statistika o broju testiranja, pozitivnih testova, negativnih testova i pacijenata pod nadzorom");
			System.out.println("6-Prekid komunikacije");
			String drugaOpcija=tastatura.readLine();
			switch (drugaOpcija) {
			case "1":
				izlazniTok.println(drugaOpcija);//Ni ne mora svaki put ovako da se salje, jer je svakako mora poslati, moze u opstem slucaju da posalje,a  tek onda case-evi
				listaSvihKorisnika();
				kraj=true;
				break;
			case "2":
				//MORA PRINTLN, NE MOZE SAMO PRINT, NE POPIJE GA LEPO, VIDI TO!
				izlazniTok.println(drugaOpcija);
				listaOdredjenihKorisnika();
				kraj=true;
				break;
			case "3":
				izlazniTok.println(drugaOpcija);
				listaOdredjenihKorisnika();
				kraj=true;
				break;
			case "4":
				izlazniTok.println(drugaOpcija);
				listaOdredjenihKorisnika();
				kraj=true;
				break;
			case "5":
				izlazniTok.println(drugaOpcija);
			    statistika();
			    kraj=true;//Skloni posle ove samo, mozda cu time da resim to da uvek ostaje vamo i da bira opcije
			    break;
			// vidi da li ovo skroz prekida izvrsavanje
			case "6":
				izlazniTok.println(drugaOpcija);
				System.out.println("Dovidjenja");
				soketZaKomunikaciju.close();
				return;
			default:
				System.out.println("Molimo Vas izaberite validnu opciju");
				break;// mislim da ovaj break nije ni potreban
			}
			}
		
	}

	private static void listaOdredjenihKorisnika() throws IOException {
		String prijem = ulazniTok.readLine();
		int broj = Integer.valueOf(prijem);
		LinkedList<KlijentPodaciZaAdmina> podaci = new LinkedList<>();
		for (int i = 0; i < broj; i++) {
			String imePrezime = ulazniTok.readLine();
			String email = ulazniTok.readLine();
//			String trenutnoStanje = ulazniTok.readLine();
			KlijentPodaciZaAdmina k = new KlijentPodaciZaAdmina(imePrezime, email, null);//nije bitno necu mu ni pristupati
			podaci.add(k);
		}
		if(podaci.isEmpty()) {
			System.out.println("Trenutno nema korisnika sa trazenim stanjem");
		}else {
		int brojac =1;
		for (KlijentPodaciZaAdmina k : podaci) {
			System.out.println(brojac+". "+ k.toStringBezStanja());
			brojac++;
		}
		}
	
}

	private static void listaSvihKorisnika() throws IOException {
		String prijem = ulazniTok.readLine();
		int broj = Integer.valueOf(prijem);
		LinkedList<KlijentPodaciZaAdmina> podaci = new LinkedList<>();
		for (int i = 0; i < broj; i++) {
			String imePrezime = ulazniTok.readLine();
			String email = ulazniTok.readLine();
			String trenutnoStanje = ulazniTok.readLine();
			KlijentPodaciZaAdmina k = new KlijentPodaciZaAdmina(imePrezime, email, trenutnoStanje);
			podaci.add(k);
		}
		if(podaci.isEmpty()) {
			System.out.println("Trenutno nema korisnika u bazi podataka");
		}else {
		int brojac =1;
		for (KlijentPodaciZaAdmina k : podaci) {
			System.out.println(brojac+". "+ k.toString());
			brojac++;
		}
		}
	
}

	private static void statistika() throws IOException {
		String brojTestovaSamoProcene = ulazniTok.readLine();// proveri da li ovde misle da se broje svi testovi
		String brojBrzihTestova = ulazniTok.readLine();
		String brojPCRTestova = ulazniTok.readLine();
		String brojPozitivnih =ulazniTok.readLine();
		String brojNegativnih = ulazniTok.readLine();
		String brojPodNadzorom = ulazniTok.readLine();
		System.out.println("Broj uradjenih testova samoprocene: " + brojTestovaSamoProcene);
		System.out.println("Broj uradjenih brzih testova: " + brojBrzihTestova);
		System.out.println("Broj uradjenih PCR testova: " + brojPCRTestova);
		System.out.println("Broj pozitivnih: " + brojPozitivnih);
		System.out.println("Broj negativnih: " + brojNegativnih);
		System.out.println("Broj pacijenata pod nadzorom: " + brojPodNadzorom);
}

//	private static void listaKorisnika(String string) {
//	// TODO Auto-generated method stub
//	
//}

// mislim da nema nekog smisla slati stanje, jer je ono gotovo, cim je poslednji, znaci to je neki vec gotov
	private static void pregledPoslednjegPCR() throws IOException {
		try {
			GregorianCalendar datum = (GregorianCalendar) ulazniZaObjekte.readObject();
			String status = ulazniTok.readLine();
			if(datum==null || status==null) {
				System.out.println("Nema podataka o poslednjem PCR testu.");
			}else {
				System.out.println("Datum poslednjeg PCR testa: "+ datum.getTime() +" Status: "+ status );
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Neka greska sa objektom");
			e.printStackTrace();
		} 
		
	}


	private static void pregledPoslednjegBrzog() throws IOException {// mogla bi da bude i metoda jer se vise puta ponavlja slicno, ali aj za sad cu ovako seljacki pa posle ulepsaj
		try {
			GregorianCalendar datum = (GregorianCalendar) ulazniZaObjekte.readObject();
			String status = ulazniTok.readLine();
			if(datum==null || status==null) {
				System.out.println("Nema podataka o poslednjem brzom testu.");
			}else {
				System.out.println("Datum poslednjeg brzog testa: "+ datum.getTime() +" Status: "+ status );//Treba preraditi format ispisa datuma, ta bude srp
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Neka greska sa objektom");
			e.printStackTrace();
		} 
		
		
	}


	private static void pregledPoslednjegSamoprocene() throws IOException {
		try {
			GregorianCalendar datum = (GregorianCalendar) ulazniZaObjekte.readObject();
			String status = ulazniTok.readLine();
			if(datum==null || status==null) {
				System.out.println("Nema podataka o poslednjoj samoproceni");
			}else {
				System.out.println("Datum poslednje samoprocene: "+ datum.getTime() +" Status: "+ status );
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Neka greska sa objektom");
			e.printStackTrace();
		} 
		
		
	}


	private static void testSamoprocene() throws IOException {// vidi za exception
		int brojac=0;// ovde cu da prebrojim, nema potrebe da server sve obradjuje, njega samo zanima dal ih vise od dva ili ne
		//ovaj deo moze pametnije, ne pada mi nista sada na pamet 
		GregorianCalendar datumTesta = new GregorianCalendar();
		izlazniZaObjekte.writeObject(datumTesta);
		String[] nizPitanja = {"Da li ste putovali van Srbije u okviru 14 dana pre pocetka simptoma?","Da li ste bili u kontaku sa zarazenim osobama?","Da li imate povisenu temperaturu?","Da li imate kasalj?","Da li se osecate malaksalo?","Da li gubite culo mirisa?","Da li gubite culo ukusa?"};
		for (String pitanje : nizPitanja) {
			if(pitanjeOdgovor(pitanje))brojac++;
		}
//		izlazniTok.println(datumTesta);// ovo server prvo prima svakako
		if(brojac<2) {
			System.out.println("Nema potrebe da radite dalje testove");
			izlazniTok.println("nemoj dalje");
		}else {
			izlazniTok.println("jos testova");
			pcrIBrzi();
		}
		
	}
	private static void pcrIBrzi() throws IOException {
		 boolean kraj=false;
		while(!kraj) {
			System.out.println("Potrebno je dalje testiranje, molimo Vas izaberite jednu od opcija:");
			System.out.println("1-Brzi test");
			System.out.println("2-PCR test");
			System.out.println("3-Prekid komunikacije");
			System.out.println("4- Brzi i PCR test");
			System.out.println();
			String trecaOpcija=tastatura.readLine();
			switch (trecaOpcija) {
			case "1":
				izlazniTok.println(trecaOpcija);
				brziTest();
				kraj=true;
				break;
			case "2":
				//MORA PRINTLN, NE MOZE SAMO PRINT, NE POPIJE GA LEPO, VIDI TO!
				izlazniTok.println(trecaOpcija);
				PCRtest();
				kraj=true;
				break;
			case "3":
			izlazniTok.println(trecaOpcija);
			System.out.println("Dovidjenja");
			soketZaKomunikaciju.close();
			return;
			case "4":
				izlazniTok.println(trecaOpcija);
				obaTesta();
				kraj=true;
				break;
			// vidi da li ovo skroz prekida izvrsavanje
			default:
				System.out.println("Molimo Vas izaberite validnu opciju");
				break;// mislim da ovaj break nije ni potreban
			}
			}
	}


	private static void obaTesta() throws IOException {
		brziTest();
		PCRtest();
	}

	private static void PCRtest() throws IOException {// Ako ostane skorz isto kao za brzi, onda moze i u jednu metodu
		GregorianCalendar datumTesta = new GregorianCalendar();
		izlazniZaObjekte.writeObject(datumTesta);
		//proveri da li smes na klijentskoj da radis delay
		String stanje1;
		String stanje2;
		String stanje3;
		stanje1 = ulazniTok.readLine();
		
		stanje2 = ulazniTok.readLine();
		
		stanje3 = ulazniTok.readLine();
		System.out.println("Trenutno stanje PCR testa je: "+stanje1);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Greska prilikom sleep-a");// vidi da li treba jos nekako da se obradjuje ovaj Exception
			e.printStackTrace();
		}
		System.out.println("Trenutno stanje PCR testa je: "+stanje2);
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Greska prilikom sleep-a");
			e.printStackTrace();
		}
		System.out.println("Trenutno stanje PCR testa je: "+stanje3);
		
		String pozitivan = ulazniTok.readLine();
		System.out.println("Rezultat PCR testa:" + pozitivan);
	}


	private static void brziTest() throws IOException {
		GregorianCalendar datumTesta = new GregorianCalendar();
		izlazniZaObjekte.writeObject(datumTesta);
		String pozitivan = ulazniTok.readLine();
		System.out.println("Rezultat brzog testa: "+ pozitivan );
	}


	private static boolean pitanjeOdgovor(String pitanje) throws IOException {//valjda ne pravi prob sto je static
		System.out.println(pitanje);
		String odgovor =tastatura.readLine();
		return odgovor.equalsIgnoreCase("da");
		
	}


	private static void logovanje() throws IOException {
		boolean kraj =false;
		while(!kraj) {
			String username;
			String lozinka;
			System.out.println("Unesite Vas username:");
			username = tastatura.readLine();
			izlazniTok.println(username);
			System.out.println("Unesite Vasu lozinku:");
			lozinka = tastatura.readLine();
			izlazniTok.println(lozinka);
			//server vraca karakter validan ako jeste, a nije ako nije
			String validan = ulazniTok.readLine();
			if(validan.equals("validan")) {
				System.out.println("Uspesno ste se ulogovali");
				kraj=true;
			}else {
				System.out.println("Molimo Vas da pokusate ponovo");
			}
		}
		
	}


	private static void prijava() throws IOException {// vidi da li je ovo dovoljno dobra obrada Exceptiona
		String zaSlanje;
		boolean kraj=false;
		while(!kraj) {
		System.out.println("Unesite Vas username:");
		zaSlanje = tastatura.readLine();
		izlazniTok.println(zaSlanje);
		System.out.println("Unesite Vasu lozinku:");
		zaSlanje = tastatura.readLine();
		izlazniTok.println(zaSlanje);
		System.out.println("Unesite Vase ime i prezime :");
		zaSlanje = tastatura.readLine();
		izlazniTok.println(zaSlanje);
		System.out.println("Unesite Vas pol :");
		zaSlanje = tastatura.readLine();
		izlazniTok.println(zaSlanje);
		System.out.println("Unesite Vas email:");
		zaSlanje = tastatura.readLine();
		izlazniTok.println(zaSlanje);
		
		String odgovor = ulazniTok.readLine();
		if(odgovor.equals("validan")) {
			System.out.println("Uspesno ste se registrovali");
			kraj=true;
		}else {
			System.out.println("Uneti username je vec zauzet, molimo vas da ponovo unesete podatke");
		}
		}
	}
	
	
}
