package vozila;

import java.util.ArrayList;
import java.util.Random;

import application.PrvaContoller;
import javafx.scene.paint.Color;
import osobe.Kofer;
import osobe.Putnik;
import simulacija.Simulacija;
import terminali.Terminal;

public class Autobus extends Vozilo {

	
	private final static int maxBrojPutnika=52;
	private final static long vrijemeZaustavljanjaPoPutniku=100;
	
	
	public Autobus() {
       
        
        putnici=generisiPutnike();
        color = Color.YELLOW;
        setVrijemeNaPolicijskom(vrijemeZaustavljanjaPoPutniku);
        setVrijemeNaCarinskom(vrijemeZaustavljanjaPoPutniku);
        
    }
	
	public Color getColor() {
		return color;
	}
	
	 boolean izborTerminala(Terminal t)
	 {
		 if(!t.isZaKamion())
	            return true;
	        return false;
			 
	 }

	public ArrayList<Putnik> getPutnici() {
		return putnici;
	}

	public void setPutnici(ArrayList<Putnik> putnici) {
		this.putnici = putnici;
	}
	
	
	public static int brojiPutnikeAutobus=0;
	
	public static ArrayList<Putnik> generisiPutnike()
	{
		
		ArrayList<Putnik> putnici = new ArrayList<>();
	    Random rand = new Random();
	    
	    int brojPutnika = rand.nextInt(maxBrojPutnika-1)+1; 
	    brojiPutnikeAutobus+=brojPutnika;
	    int brojPutnikaSaKoferom = (int) (brojPutnika * 0.7);
	  
	    
	    boolean vozacGenerisan = false;
	    
	    for (int i = 0; i < brojPutnika; i++) {
	        Putnik putnik = new Putnik();

	        if (!vozacGenerisan) {
	            putnik.setVozac(true);
	            vozacGenerisan = true;
	        } else {
	            putnik.setVozac(false);
	            if (brojPutnikaSaKoferom > 0) {
	                Kofer kofer = new Kofer();
	                putnik.setKofer(kofer);
	                putnik.setImaKofer(true);
	                if (rand.nextInt(100) < 10) {
	                    kofer.setIspravnost(false);
	                    putnik.setImaKofer(true);
	                }
	                brojPutnikaSaKoferom--;
	               
	            }
	        }

	        putnici.add(putnik);
	    }
	    return putnici;
	}
		
		

		@Override
		public String toString() {
			
			return "Autobus:"+ getIdVozila() + ", broj putnika:" + putnici.size() + putnici.toString() +brojiPutnikeAutobus  ;
		}

	
}
