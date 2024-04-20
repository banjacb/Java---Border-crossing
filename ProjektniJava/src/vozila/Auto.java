package vozila;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import application.PrvaContoller;
import javafx.scene.paint.Color;

import osobe.Putnik;
import simulacija.Simulacija;
import terminali.P1;
import terminali.P2;
import terminali.PolicijskiTerminal;
import terminali.Terminal;

public class Auto extends Vozilo{


	private final static int maxBrojPutnika=5;
	private final static int vrijemeZaustavljanjaPoPutniku=500;
	private final static int vrijemeZaustavljanjaNaCarinskom=2000;
	
	 boolean izborTerminala(Terminal t)
	 {
		 if(!t.isZaKamion())
	            return true;
	        return false; 
	 }
	  
	
	public Auto()
	{
		
		putnici=generisiPutnike();
		color=Color.web("#6fb49c");
		setVrijemeNaPolicijskom(vrijemeZaustavljanjaPoPutniku);
		setVrijemeNaCarinskom(vrijemeZaustavljanjaNaCarinskom);
		Putnik p=null;
		for(int i=0;i<putnici.size();i++)
		{
			p=putnici.get(i);
			if(!p.validnostDokumentacije)
			{
				System.out.println(this.getIdVozila());
			}
		}
	}
	
	public static int brojiPutnikeAuto=0;
	
	public static ArrayList<Putnik> generisiPutnike()
	{

		 ArrayList<Putnik> generisaniPutnici = new ArrayList<>();
		 Random rand= new Random();
		 int brojPutnika = rand.nextInt(maxBrojPutnika)+1;
		 brojiPutnikeAuto+= brojPutnika;
		 boolean vozacGenerisan = false;
		
		    
		    for (int i = 0; i < brojPutnika; i++) {
		        Putnik putnik = new Putnik();
		        
		        if (!vozacGenerisan) {
		            putnik.setVozac(true);
		            vozacGenerisan = true;
		        } else {
		            putnik.setVozac(false);
		          
		        }
		       
		        generisaniPutnici.add(putnik);
		        
		    }
		    
		 
		    return generisaniPutnici;
		  

	}

	

	@Override
	public String toString() {
		return "Auto:" + getIdVozila() + ", broj putnika:" + putnici.size() + putnici.toString(); 
	}


}
