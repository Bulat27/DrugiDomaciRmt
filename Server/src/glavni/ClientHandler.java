package glavni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

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
			
			
			izlazniTok.println("Prosao si");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		String username=ulazniTok.readLine();
		String lozinka=ulazniTok.readLine();
		String imeIPrezime=ulazniTok.readLine();
		String pol=ulazniTok.readLine();
		String email=ulazniTok.readLine();
		klijent = new KlijentPodaci(username, lozinka, imeIPrezime, pol, email);
		dodajKlijenta();
		
	}
	//ovo treba da doda i da SERIJALIZUJE
	private void dodajKlijenta() {
		Server.listaRegistrovanih.add(klijent);
		//serijalizacija 
		
		
	}
	
	
}
