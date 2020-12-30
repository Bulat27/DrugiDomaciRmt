package glavniPaket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

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
			
			System.out.println(ulazniTok.readLine());
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	}
	
	
}
