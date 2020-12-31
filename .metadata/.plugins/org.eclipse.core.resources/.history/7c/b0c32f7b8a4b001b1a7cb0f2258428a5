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
			
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void pregledPoslednjegPCR() {
		// TODO Auto-generated method stub
		
	}


	private static void pregledPoslednjegBrzog() throws IOException {// mogla bi da bude i metoda jer se vise puta ponavlja slicno, ali aj za sad cu ovako seljacki pa posle ulepsaj
		try {
			GregorianCalendar datum = (GregorianCalendar) ulazniZaObjekte.readObject();
			String status = ulazniTok.readLine();
			if(datum==null || status==null) {
				System.out.println("Nema podataka o poslednjem brzom testu.");
			}else {
				System.out.println("Datum poslednjeg brzog testa: "+ datum.getTime() +" Status: "+ status );
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
				//videcemo kako cu oba da realizujem
				break;
			// vidi da li ovo skroz prekida izvrsavanje
			default:
				System.out.println("Molimo Vas izaberite validnu opciju");
				break;// mislim da ovaj break nije ni potreban
			}
			}
	}


	private static void PCRtest() {
		// TODO Auto-generated method stub
		
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
