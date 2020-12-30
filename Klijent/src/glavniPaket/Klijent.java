package glavniPaket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.GregorianCalendar;

public class Klijent {
	
    static Socket soketZaKomunikaciju=null;
	static BufferedReader ulazniTok = null;
	static PrintStream izlazniTok = null;
	static BufferedReader tastatura = null;
	
	
	public static void main(String[] args) {
		try {
			soketZaKomunikaciju = new Socket("localhost", 9027);
			
			ulazniTok= new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			izlazniTok = new PrintStream(soketZaKomunikaciju.getOutputStream());
			
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
			while(!kraj) {
			System.out.println("Izaberite neku od sledecih opcija:");
			System.out.println("1-Test za samoprocenu");
			System.out.println("2-Podaci o poslednjoj prijavi");
			System.out.println("3-Prekid komunikacije");
			String drugaOpcija=tastatura.readLine();
			switch (drugaOpcija) {
			case "1":
				izlazniTok.println(drugaOpcija);
				testSamoprocene();
				kraj=true;
				break;
			case "2":
				//MORA PRINTLN, NE MOZE SAMO PRINT, NE POPIJE GA LEPO, VIDI TO!
				izlazniTok.println(drugaOpcija);
				pregledPoslednjegPrijave();
				kraj=true;
				break;
			case "3":
			izlazniTok.println(drugaOpcija);
			System.out.println("Dovidjenja");
			soketZaKomunikaciju.close();
			return;
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


	private static void pregledPoslednjegPrijave() {
		// TODO Auto-generated method stub
		
	}


	private static void testSamoprocene() throws IOException {// vidi za exception
		int brojac=0;// ovde cu da prebrojim, nema potrebe da server sve obradjuje, njega samo zanima dal ih vise od dva ili ne
		//ovaj deo moze pametnije, ne pada mi nista sada na pamet 
		GregorianCalendar datumTesta = new GregorianCalendar();
		String[] nizPitanja = {"Da li ste putovali van Srbije u okviru 14 dana pre pocetka simptoma?","Da li ste bili u kontaku sa zarazenim osobama?","Da li imate povisenu temperaturu?","Da li imate kasalj?","Da li se osecate malaksalo?","Da li gubite culo mirisa?","Da li gubite culo ukusa?"};
		for (String pitanje : nizPitanja) {
			if(pitanjeOdgovor(pitanje))brojac++;
		}
		izlazniTok.println(datumTesta);// ovo server prvo prima svakako
		if(brojac<2) {
			System.out.println("Nema potrebe da radite dalje testove");
			izlazniTok.println("nemoj dalje");
		}else {
			izlazniTok.println("jos testova");
			
		}
		
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
