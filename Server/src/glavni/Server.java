package glavni;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
	
	public static LinkedList<KlijentPodaci> listaRegistrovanih = new LinkedList<>();//mora cu svaki put da je ucitivam iz tog fajla, ali necu ovu listu ipak, nego onu listu sa Klijent Podaci
	// Korisnici nezavisno rade, tako da mi nije bitno koji su trenutno ulogovani
	
	public static void main(String[] args) {
		
		ucitajListuIzFajla();
		int port = 9027;
		ServerSocket serverSoket=null;
		Socket soketZaKomunikaciju=null;
		
		
		try {
			serverSoket  = new ServerSocket(port);
			
			while(true) {
				System.out.println(">>>Cekam na konekciju");
				soketZaKomunikaciju = serverSoket.accept();
				System.out.println(">>>Doslo je do konekcije");
				
				ClientHandler klijent = new ClientHandler(soketZaKomunikaciju);
//				listaUlogovanih.add(klijent);
				klijent.start();
				
				
			}
			
			
			
			
		} catch (IOException e) {//Malo bolje odradi ovaj exception, vidi tacno sta je i to
			System.out.println("Greska prilikom pokretanja servera");
		}
		
		
	}

	
	
	
	
	private static void ucitajListuIzFajla() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
