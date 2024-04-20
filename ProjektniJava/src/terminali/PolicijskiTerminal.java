package terminali;

import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;

import application.Main;
import application.PrvaContoller;
import fajlovi.RadSaFajlovima;
import javafx.scene.paint.Color;
import osobe.Putnik;
import simulacija.Simulacija;
import vozila.Auto;
import vozila.Vozilo;


public class PolicijskiTerminal extends Terminal {

	
	public PolicijskiTerminal(int id,boolean pauza) {
		super(id,pauza);
	
	}
	
	@Override
	public void run()
	{
		
		while(Simulacija.provjeriAktivnaVozila() || this.v!=null || radiDokImaSta)
		{					
		if(this.v!=null)
		{
			
			synchronized (this) { 
				
				provjeriRadTerminalaKlikom();
				provjeriRadTerminala();
				
				if(this.v!=null && !this.v.zavrsiPolicijski) 
				{
					
					
					System.out.println("Vozilo stiglo na terminal  "+ v.getIdVozila() +" - "+ this.getIdTerminala());
					System.out.println("Vozilo ima putnika: " + v.getPutnici().size());
					int obradjeniSviPutnici=v.getPutnici().size();
					for(int i=0;this.v != null && i<v.getPutnici().size();i++)
					{
						
						obradjeniSviPutnici--;
						try {
							Thread.sleep(v.getVrijemeNaPolicijskom());
						}
						catch (Exception e) {
							// TODO: handle exception
							 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
						}
						
						Putnik putnik = v.getPutnici().get(i);
						
						if(putnik != null && putnik.isVozac() && !putnik.validnostDokumentacije) //uslov da se izbaci auto
						{
							RadSaFajlovima.upisiUFajl(RadSaFajlovima.kaznjenaVozila, v.getIdVozila() + "- vozilo kaznjeno. Vozac ima neispravnu dokumentaciju, id: " +putnik.getIdPutnika());
							System.out.println("Ovo auto se izbacuje: " + v.getIdVozila() +"a id putnika je "+ putnik.getIdPutnika());
							PrvaContoller.ukloniGui(this.getIdTerminala());
							RadSaFajlovima.upisiPutnikaBinarno(putnik);
							v.getPutnici().clear();
							v.kraj=true;
							v.zavrsiPolicijski=true;
							PrvaContoller.ispisiSpecifDog("Na terminalu "+ this.getIdTerminala() +" je izbaceno vozilo "+ v.getIdVozila());
							v.policijskiTerminal=null;
							//obraniti slucaj da je zadnje vozilo neispravno
							this.v=null;									
							break;
						}
						
						if (putnik != null && !putnik.isVozac() && !putnik.validnostDokumentacije)
						{
							
							System.out.println("Ovaj putnik se izbacuje: " + putnik.getIdPutnika() +" iz vozila "+ v.getIdVozila());
							RadSaFajlovima.upisiPutnikaBinarno(putnik);
							RadSaFajlovima.upisiUFajl(RadSaFajlovima.vozilaKojaSuPreslaGranicu, v.getIdVozila() +" - preslo policijski terminal i ide na carinski. Putnik imao neispravnu dokumentaciju i izbacen je: "+ putnik.getIdPutnika());
							PrvaContoller.ispisiSpecifDog("Na terminalu "+ this.getIdTerminala() +" je izbacen putnik: " + putnik.getIdPutnika()+" zbog nevalidne dokumentacije iz vozila: "+ v.getIdVozila());
							v.getPutnici().remove(putnik);
						
						
						}
						if(obradjeniSviPutnici==0)
						{
			
			                v.zavrsiPolicijski = true;		               
			               PrvaContoller.obrisiInfoZaTerminale(this.getIdTerminala());
						}			
					}								
				}	      
            }		
			}
		}
	}
		
	private void provjeriRadTerminala() {
	    while (pauza) {
	        //System.out.println("PAUZA NA POLICIJSKOM TERMINALU" +this.getIdTerminala());
	        try {
	            Thread.sleep(1000); 
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	private void provjeriRadTerminalaKlikom() {
	    while (PrvaContoller.pauzaKlikom) {
	        //System.out.println("PAUZA NA POLICIJSKOM TERMINALU KLIKOM" +this.getIdTerminala());
	        try {
	            Thread.sleep(1000); 
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}

}
