package glavni;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.GregorianCalendar;

public class ClientHandler extends Thread{
	
	
	public ClientHandler(Socket soketZaKomunikaciju) {
		super();
		this.soketZaKomunikaciju = soketZaKomunikaciju;
	}
	
	PrintStream izlazniTok = null;
	Socket soketZaKomunikaciju = null;
	BufferedReader ulazniTok = null;
//	String username;
//	String lozinka;
//	String imeIPrezime;
//	String pol;
//	String email;
	KlijentPodaci klijent=null;
	
	@Override
	public void run() {
		
		try {
			ulazniTok = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			izlazniTok = new PrintStream(soketZaKomunikaciju.getOutputStream());
			// logovanje ili registracija
			String prvaOpcija=ulazniTok.readLine();
			
			switch (prvaOpcija) {
			case "1":
				prijava();
				break;
			case "2":
				logovanje();
				break;
			case "3":
				soketZaKomunikaciju.close();
				return;//vidi da li ce ovo lepo prekinuti, mozda systemexit(0)?
				//break;
				
			
			}
			
			
//			izlazniTok.println("Prosao si");
			String drugaOpcija =ulazniTok.readLine();
			switch (drugaOpcija) {
			case "1":
				testSamoprocene();
				break;
			case "2":
				pregledPoslednjePrijave();
				break;
			case "3":
				soketZaKomunikaciju.close();
				return;//vidi da li ce ovo lepo prekinuti, mozda systemexit(0)?
				//break;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("GRIJESKA");
			
//			e.printStackTrace();
			System.out.println("Klijent se nasilno iskljucio");
		}
		
		
		
	}

	private void pregledPoslednjePrijave() {
		// TODO Auto-generated method stub
		
	}

	private void testSamoprocene() throws IOException {
//		GregorianCalendar datumSamoProcene = ulazniTok.read
		String dalje = ulazniTok.readLine();
		if(dalje.equals("jos testova")) {
			
		}else {
			//videcemo sta cemo ovde
		}
		
	}

	private void logovanje() throws IOException {
		boolean kraj =false;
		String username;
		String lozinka;
		while(!kraj) {
			username = ulazniTok.readLine();
			lozinka=ulazniTok.readLine();
			if(Server.listaRegistrovanih.isEmpty()) {
				izlazniTok.println("nije");
				continue;
			}
			for (KlijentPodaci trenutni : Server.listaRegistrovanih) {
				if(trenutni.username.equals(username) && trenutni.lozinka.equals(lozinka)) {
					kraj =true;
					klijent=trenutni;
					izlazniTok.println("validan");
				}else {
					izlazniTok.println("nije");
				}
			}
		}
		
	}

	private void prijava() throws IOException {
		boolean kraj = false;
		while(!kraj) {
		String username=ulazniTok.readLine();
		String lozinka=ulazniTok.readLine();
		String imeIPrezime=ulazniTok.readLine();
		String pol=ulazniTok.readLine();
		String email=ulazniTok.readLine();
		
		klijent = new KlijentPodaci(username, lozinka, imeIPrezime, pol, email);
//		klijent=new KlijentPodaci(username, lozinka, imeIPrezime, pol, email, testSamoprocene, brziTest)
		if(Server.listaRegistrovanih.contains(klijent)) {
			izlazniTok.println("zauzet");
			klijent=null;
		}else {
			izlazniTok.println("validan");
			dodajKlijenta();
			kraj=true;
		}
		
//		for (KlijentPodaci trenutni : Server.listaRegistrovanih) {
//			if(trenutni.username.equals(username)) {
//				izlazniTok.println("zauzet");
//				
//			}else {
//				izlazniTok.println("validan");
//				klijent = new KlijentPodaci(username, lozinka, imeIPrezime, pol, email);
//				dodajKlijenta();
//				kraj =true;
//			}
//		}
		
		}
		
	}
	//ovo treba da doda i da SERIJALIZUJE (vidi da li je bitno dal je public ili private)
	public void dodajKlijenta() {
		Server.listaRegistrovanih.add(klijent);
		//serijalizacija 
		try(FileOutputStream fOut = new FileOutputStream("baza.dat");
			BufferedOutputStream bOut = new BufferedOutputStream(fOut);
			ObjectOutputStream out = new ObjectOutputStream(bOut);	
				){
			for (int i = 0; i < Server.listaRegistrovanih.size(); i++) {
				out.writeObject(Server.listaRegistrovanih.get(i));
			}
			
		}catch (Exception e) {
			System.out.println("Greska prilikom serijalizacije"+ e.getMessage());
		}
		
	}
	
	
}
