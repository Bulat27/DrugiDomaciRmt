package glavni;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.Random;

public class ClientHandler extends Thread{
	
	
	public ClientHandler(Socket soketZaKomunikaciju) {
		super();
		this.soketZaKomunikaciju = soketZaKomunikaciju;
	}
	ObjectOutputStream izlazniZaObjekte=null;
	ObjectInputStream ulazniZaObjekte=null;
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
			izlazniZaObjekte= new ObjectOutputStream(soketZaKomunikaciju.getOutputStream());
			ulazniZaObjekte = new ObjectInputStream(soketZaKomunikaciju.getInputStream());
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
			
			if(klijent.username.equals("Admin")) {
				izlazniTok.println("admin");
//				System.out.println("Prosao je Admin");
				ispisListaPriUlasku();
				adminoveOpcije();
			}else {
			izlazniTok.println("nije admin");
//			izlazniTok.println("Prosao si");
			
			proveraPodNadzorom();
			
			
			while(true) {
			String drugaOpcija =ulazniTok.readLine();
			switch (drugaOpcija) {
			case "1":
				testSamoprocene();
				break;
			case "2":
				pregledPoslednjeSamoprocene();
				break;
			case "3":
				soketZaKomunikaciju.close();
				return;//vidi da li ce ovo lepo prekinuti, mozda systemexit(0)?
				//break;
			case "4":
				pregledPoslednjegBrzog();
				break;
			case "5":
				pregledPoslednjegPCR();
				break;
			}
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("GRIJESKA");
			
			e.printStackTrace();// ovo posle izbrisi
			System.out.println("Klijent se nasilno iskljucio");
		}
		
		
		
	}
	
	private void proveraPodNadzorom() throws IOException {
//		if(klijent.trenutnoStanje.equals("pod nadzorom") && prosaoDatum()) {
//			izlazniTok.println("obavezna samoprocena");
//		}
		if(klijent.trenutnoStanje.equals("pod nadzorom")) {
			izlazniTok.println("idi dalje");
			if(prosaoDatum()) {
				izlazniTok.println("obavezna samoprocena");
			}else {
				izlazniTok.println("nije obavezna");
			}
		}else {
			izlazniTok.println("nemoj dalje");
		}
	}

	private boolean prosaoDatum() throws IOException {
		try {
			GregorianCalendar datumLogina = (GregorianCalendar) ulazniZaObjekte.readObject();
			GregorianCalendar danPosle = klijent.testSamoprocene.getDatum();//ovo ovde ne moze biti null, jer ovde dolazi samo ako je pod nadzorom, a to znaci da je morao uraditi test samoprocene vec
			if(danPosle!=null)danPosle.add(GregorianCalendar.MINUTE, 1);
			if(datumLogina.after(danPosle)) {
				return true;
			}else {
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Greska prilikom citanja objekta");
			e.printStackTrace();
			return false;// vidi jos malo oko ovog i svih Exceptiona
		} 
		
		
	}

	private void ispisListaPriUlasku() throws IOException {
		try {
			GregorianCalendar datumPrethodnogLogina = (GregorianCalendar) (ulazniZaObjekte.readObject());
			int broj=vratiBrojNovih (datumPrethodnogLogina);
			String br = String.valueOf(broj);
			izlazniTok.println(br);
			for (KlijentPodaci k : Server.listaRegistrovanih) {
				GregorianCalendar azurnijiDatum = nadjiPoslednjiDatum(k);
				if(azurnijiDatum==null)continue;//ako nema datum testova, sigurno nece biti ni pozitivan pa se nece ni ispisati
				if(k.trenutnoStanje.equals("pozitivan") && (azurnijiDatum.after(datumPrethodnogLogina) || azurnijiDatum.equals(datumPrethodnogLogina) )) {
					izlazniTok.println(k.imeIPrezime);
					izlazniTok.println(k.email);
				}
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Greska prilikom prenosa objekta streamom");
			e.printStackTrace();
		} 
		
	}

	private int vratiBrojNovih(GregorianCalendar datumPrethodnogLogina) {
		if(datumPrethodnogLogina==null)return 0;
		int brojac=0;
		for (KlijentPodaci k : Server.listaRegistrovanih) {
			GregorianCalendar azurnijiDatum = nadjiPoslednjiDatum(k);
			if(azurnijiDatum==null)continue;//ako nema datum testova, sigurno nece biti ni pozitivan pa se nece ni ispisati
			if(k.trenutnoStanje.equals("pozitivan") && (azurnijiDatum.after(datumPrethodnogLogina) || azurnijiDatum.equals(datumPrethodnogLogina) )) {
				brojac++;
			}
		}
		return brojac;
	}

	private GregorianCalendar nadjiPoslednjiDatum(KlijentPodaci k) {
		GregorianCalendar datumBrzog = k.brziTest.getDatum();
		GregorianCalendar datumPCR = k.pcrTest.getDatum();
		if(datumBrzog==null)return datumPCR;
		if(datumPCR==null)return datumBrzog;
		if(datumBrzog.after(datumPCR)) {
			return datumBrzog;
		}else {
			return datumPCR;
		}
		
	}

	private void adminoveOpcije() throws IOException {
		while(true) {
		String drugaOpcija =ulazniTok.readLine();
		switch (drugaOpcija) {
		case "1":
			listaSvihKorisnika();
			break;
		case "2":
			listaOdredjenihKorisnika("pozitivan");
			break;
		case "3":
			listaOdredjenihKorisnika("negativan");
			break;
		case "4":
			listaOdredjenihKorisnika("pod nadzorom");
			break;
		case "5":
			statistika();
			break;
		case "6":
			soketZaKomunikaciju.close();
			return;//vidi da li ce ovo lepo prekinuti, mozda systemexit(0)?
			//break;
		}
		}
	}

	private void listaOdredjenihKorisnika(String trazenoStanje) {
		String broj = String.valueOf(brElemenataTrazeneListe(trazenoStanje));
		//moze sve da se cuva, pa da ne mora dva prolaza, ali okej je i ovako, nece ova adminska metoda biti preveliki broj puta pozivana, pa jos jedan prolaz i nije strasan
		izlazniTok.println(broj);
		for (KlijentPodaci k : Server.listaRegistrovanih) {
			if(k.trenutnoStanje.equals(trazenoStanje)) {
				izlazniTok.println(k.imeIPrezime);
				izlazniTok.println(k.email);
			}
		}
	}

	private int brElemenataTrazeneListe(String trazenoStanje) {
		int brojac = 0;
		// nista od ovoga ne moze biti null, zato nisu potrebne provere
		for (KlijentPodaci k : Server.listaRegistrovanih) {
			if(k.trenutnoStanje.equals(trazenoStanje))brojac++;
		}
		return brojac;
	}

	private void listaSvihKorisnika() {
		String broj = String.valueOf(Server.listaRegistrovanih.size());
		izlazniTok.println(broj);
		for (KlijentPodaci k : Server.listaRegistrovanih) {
			izlazniTok.println(k.imeIPrezime);
			izlazniTok.println(k.email);
			izlazniTok.println(k.trenutnoStanje);
		}
		
		
	}

//	private void pregledPoslednjegTesta() {
//		
//	}// OVO URADI AKO BUDES IMAO VREMENA, NAPRAVIS INTEFREJS TEST, PRIHVATA SVA TRI, VIDI KOJI JE I PROSLEDI U ODNOSU NA TO
	
	

	private void statistika() {
		//ako neki test ima datum, tj ako datum != null znaci da je test radjen, tako ih brojim
		// testovi ne mogu biti null u ovom trenutku jer cim postoji klijent, njegovi testovi su inicijalizovani
		int brSP = 0;
		int brBrzih = 0;
		int brPCR = 0;
		int brPozitivnih = 0;
		int brNegativnih = 0;
		int brPodNadzorom = 0;
		for (KlijentPodaci k : Server.listaRegistrovanih) {
			if(k.testSamoprocene.getDatum()!=null)brSP++;
			if(k.brziTest.getDatum()!=null)brBrzih++;
			if(k.pcrTest.getDatum()!=null)brPCR++;
			
			String trenutnoStanje = k.trenutnoStanje;
			// nista mi ne znaci elseif jer moze da bude i nepoznat pa ne mogu da kazem else: pa dodaj na nesto
			if(trenutnoStanje.equals("pozitivan"))brPozitivnih++;
			if(trenutnoStanje.equals("negativan"))brNegativnih++;
			if(trenutnoStanje.equals("pod nadzorom"))brPodNadzorom++;
		}
			izlazniTok.println(String.valueOf(brSP));
			izlazniTok.println(String.valueOf(brBrzih));
			izlazniTok.println(String.valueOf(brPCR));
			izlazniTok.println(String.valueOf(brPozitivnih));
			izlazniTok.println(String.valueOf(brNegativnih));
			izlazniTok.println(String.valueOf(brPodNadzorom));
		
	}

	private void pregledPoslednjegPCR() throws IOException {
		if(klijent.pcrTest!=null) {
			izlazniZaObjekte.writeObject(klijent.pcrTest.getDatum());
			izlazniTok.println(klijent.pcrTest.getStatus());
			}else {
				//trebalo bi da mu posaljem nullove da bi on tamo obraido greksu! Sljaka i bez toga, tj posalje mu nullove
			}
		
	}

	private void pregledPoslednjegBrzog() throws IOException {
		if(klijent.brziTest!=null) {
			izlazniZaObjekte.writeObject(klijent.brziTest.getDatum());
			izlazniTok.println(klijent.brziTest.getStatus());
			}else {
				//trebalo bi da mu posaljem nullove da bi on tamo obraido greksu!  Sljaka i bez toga, tj posalje mu nullove
			}
			
		
	}

	private void pregledPoslednjeSamoprocene() throws IOException {
		if(klijent.testSamoprocene!=null) {
		izlazniZaObjekte.writeObject(klijent.testSamoprocene.getDatum());
		izlazniTok.println(klijent.testSamoprocene.getStatus());
		}else {
			//trebalo bi da mu posaljem nullove da bi on tamo obraido greksu, i onako mu posalje nullove, tako da sljaka!
		}
		
	}

	private void testSamoprocene() throws IOException {
		GregorianCalendar datumSamoProcene=null;
		try {
			 datumSamoProcene = (GregorianCalendar) ulazniZaObjekte.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("PROBLEM SAM CITANJEM OBJEKTA");
			e.printStackTrace();
		} 
		if(klijent.testSamoprocene==null)klijent.testSamoprocene = new TestSamoprocene(null, null,0);//mislim da ovo ovde nije neophodno
		klijent.testSamoprocene.setDatum(datumSamoProcene);
//		if(datumSamoProcene!=null) {
//			System.out.println(datumSamoProcene.getTime());
//		}
		
		String dalje = ulazniTok.readLine();
		if(dalje.equals("jos testova")) {
//			Server.statistika.povecajBrojTestiranja();
//			azurirajStatistiku();
			klijent.testSamoprocene.setStatus("dalje testiranje");
			serijalizuj();// mora posle svake promene da se radi!
			String trecaOpcija =ulazniTok.readLine();
			switch (trecaOpcija) {
			case "1":
				brziTest();
				break;
			case "2":
				PCRtest();
				break;
			case "3":
				soketZaKomunikaciju.close();
//				return;//vidi da li ce ovo lepo prekinuti, mozda systemexit(0)? Ovde ne radi posao, prekida sao ovu metodu, a ne celu run metodu
//				System.exit(0);
				//break;
			case "4":
				obaTesta();
				//PCR i brzi, videcu kako cu to
				break;
			}
			
		}else {
			if(klijent.testSamoprocene.getBrojac()%2==0) {// ako je taj prvi test, ide pod nadzor, ako je drugi, onda izlazi iz nadzora i vodi se kao negativan
				//Ako oce opet da ulazi, onda opet ima priliku da udje pod nadzor
			klijent.testSamoprocene.setStatus("pod nadzorom");
			klijent.trenutnoStanje = "pod nadzorom";
			serijalizuj();
			}else {
				klijent.testSamoprocene.setStatus("negativan");
				klijent.trenutnoStanje = "negativan";
				serijalizuj();
			}
			//videcemo sta cemo ovde
			klijent.testSamoprocene.povecajBrojac();
			serijalizuj();
		}
		
	}

//	private void azurirajStatistiku() {
//		try(FileOutputStream fOut = new FileOutputStream("statistika.dat");
//			BufferedOutputStream bOut = new BufferedOutputStream(fOut);
//			ObjectOutputStream out = new ObjectOutputStream(bOut);	
//					){
////			for (int i = 0; i < Server.listaRegistrovanih.size(); i++) {
////					out.writeObject(Server.listaRegistrovanih.get(i));
////				}
//				out.writeObject(Server.statistika);
//			}catch (Exception e) {
//				System.out.println("Greska prilikom serijalizacije statistike"+ e.getMessage());
//				e.printStackTrace();
//			}
//		
//	}

	private void obaTesta() throws IOException {
		brziTest();
		PCRtest();
		
	}

	private void PCRtest() throws IOException {
		GregorianCalendar datumTesta=null;
		try {
			 datumTesta = (GregorianCalendar) ulazniZaObjekte.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("Greska prilikom ucitavanja objekta");
			e.printStackTrace();
		} 
		//mislim da u ovom trenutku ne moze biti null jer sam ga napravio kako napravim klijenta, ali videcemo, ako pukne, stavis null proveru pa obradis nekako
		klijent.pcrTest.setDatum(datumTesta);
		Random r = new  Random();
		int broj = r.nextInt(2);
		if(broj==0) {
			klijent.pcrTest.setStatus("negativan");
//			Server.statistika.povecajBrojTestiranja();
//			Server.statistika.povecajBrojNegativnih();
//			azurirajStatistiku();
		}else {
			klijent.pcrTest.setStatus("pozitivan");
//			Server.statistika.povecajBrojTestiranja();
//			Server.statistika.povecajBrojPozitivnih();
//			azurirajStatistiku();
		}
		klijent.trenutnoStanje=klijent.pcrTest.getStatus();
		serijalizuj();
//		System.out.println(klijent.trenutnoStanje);
		//Mislim da je okej ovako
		klijent.pcrTest.setStanje("na cekanju");
		izlazniTok.println(klijent.pcrTest.getStanje());
//		try {
//			sleep(3000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			System.out.println("Greska prilikom sleepa");
//			e1.printStackTrace();
//		}
		klijent.pcrTest.setStanje("u obradi");
		izlazniTok.println(klijent.pcrTest.getStanje());
//		try {
//			sleep(30000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Greska prilikom sleepa");
//			Thread.currentThread().interrupt();//Ovo sam nasao kao resenje na netu, mozda obrisem posle
//			e.printStackTrace();
//		}
		klijent.pcrTest.setStanje("gotov");
		izlazniTok.println(klijent.pcrTest.getStanje());
		izlazniTok.println(klijent.pcrTest.getStatus());
		
	}

	private void brziTest() throws IOException {
		GregorianCalendar datumTesta=null;
		try {
			 datumTesta = (GregorianCalendar) ulazniZaObjekte.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("Greska prilikom ucitvanja objekta");
			e.printStackTrace();
		} 
		//mislim da u ovom trenutku ne moze biti null jer sam ga napravio kako napravim klijenta, ali videcemo, ako pukne, stavis null proveru pa obradis nekako
		klijent.brziTest.setDatum(datumTesta);
		Random r = new  Random();
		int broj = r.nextInt(2);
		if(broj==0) {
			klijent.brziTest.setStatus("negativan");
//			Server.statistika.povecajBrojTestiranja();
//			Server.statistika.povecajBrojNegativnih();
//			azurirajStatistiku();
		}else {
			klijent.brziTest.setStatus("pozitivan");
//			Server.statistika.povecajBrojTestiranja();
//			Server.statistika.povecajBrojPozitivnih();
//			azurirajStatistiku();
		}
		klijent.trenutnoStanje=klijent.brziTest.getStatus();
		serijalizuj();
		izlazniTok.println(klijent.brziTest.getStatus());
//		System.out.println(klijent.trenutnoStanje);
	}

	private void serijalizuj() {
		try(FileOutputStream fOut = new FileOutputStream("baza.dat");
				BufferedOutputStream bOut = new BufferedOutputStream(fOut);
				ObjectOutputStream out = new ObjectOutputStream(bOut);	
					){
				for (int i = 0; i < Server.listaRegistrovanih.size(); i++) {
					out.writeObject(Server.listaRegistrovanih.get(i));
				}
				
			}catch (Exception e) {
				System.out.println("Greska prilikom serijalizacije"+ e.getMessage());
				e.printStackTrace();
			}
		
	}

	private void logovanje() throws IOException {
		boolean kraj =false;
		boolean nasao=false;
		String username;
		String lozinka;
		while(!kraj) {
			username = ulazniTok.readLine();
			lozinka=ulazniTok.readLine();
			
		if((username.equals("Admin") && lozinka.equals("NBJMVS"))){
			kraj=true;
			nasao=true;
			klijent=new KlijentPodaci("Admin", "NBJMVS", null, null, null, null, null, null, null);// mislim da nema potrebe ni da ga serijalizujem, samo cu zabraniti tamo prijavu kao admin
			izlazniTok.println("validan");//mozda i da bude println("admin")
			break;
		}
		if(Server.listaRegistrovanih.isEmpty()) {
			izlazniTok.println("nije");
			continue;
		}
			// mozda moze lepse da se resi sa contains metodom
			for (KlijentPodaci trenutni : Server.listaRegistrovanih) {
				if(trenutni.username.equals(username) && trenutni.lozinka.equals(lozinka)) {// malo je glupo proveravati ovako i admina svaki put, posle doteraj to
					kraj =true;
					nasao=true;
					klijent=trenutni;
					izlazniTok.println("validan");
				}//else {
					//izlazniTok.println("nije");
				//}
			}
			if(!nasao)izlazniTok.println("nije");
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
		
		klijent = new KlijentPodaci(username, lozinka, imeIPrezime, pol, email,new TestSamoprocene(null, null,0),new BrziTest(null, null),new PCRtest(null, null, null),"nepoznato");
//		klijent=new KlijentPodaci(username, lozinka, imeIPrezime, pol, email, testSamoprocene, brziTest)
		if(username.equals("Admin") || Server.listaRegistrovanih.contains(klijent)) {
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
			e.printStackTrace();
		}
		
	}
	
	
	
}
