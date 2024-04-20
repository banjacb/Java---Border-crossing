package vozila;

import java.util.ArrayList;
import java.util.Random;

import application.PrvaContoller;
import javafx.scene.paint.Color;
import osobe.Putnik;
import simulacija.Simulacija;
import terminali.Terminal;

public class Kamion extends Vozilo{

public boolean imaCarinskuDokumentaciju;
private final static long vrijemeZaustavljanjaPoPutniku=500;
private Teret teret;
	
	private final static int maxBrojPutnika=3;


	public Color getColor() {
		return color;
	}
	
	

	public boolean isImaCarinskuDokumentaciju() {
		return imaCarinskuDokumentaciju;
	}



	public void setImaCarinskuDokumentaciju(boolean imaCarinskuDokumentaciju) {
		this.imaCarinskuDokumentaciju = imaCarinskuDokumentaciju;
	}



	public Kamion() {
		
		Random r=new Random();
		putnici=generisiPutnike();
		this.imaCarinskuDokumentaciju=r.nextBoolean(); 
		teret= new Teret();
	    color=Color.web("#c370c4");
	    setVrijemeNaPolicijskom(vrijemeZaustavljanjaPoPutniku);
	    setVrijemeNaCarinskom(vrijemeZaustavljanjaPoPutniku);
	
	}
	
	
	
	 public Teret getTeret() {
		return teret;
	}



	public void setTeret(Teret teret) {
		this.teret = teret;
	}



	boolean izborTerminala(Terminal t)
	 {
		 if(t.isZaKamion())
	            return true;
	        return false;
	 }

	public ArrayList<Putnik> getPutnici() {
		return putnici;
	}

	public void setPutnici(ArrayList<Putnik> putnici) {
		this.putnici = putnici;
	}
	


	
	public static int brojiPutnikeKamion=0;
	static public ArrayList<Putnik> generisiPutnike()
	{
		 ArrayList<Putnik> generisaniPutnici = new ArrayList<>();
		 Random rand= new Random();
		 int brojPutnika = rand.nextInt(maxBrojPutnika) + 1;
		   brojiPutnikeKamion+=brojPutnika;
		   
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
		if(teret.getStvarnaMasa()==teret.getDeklarisanaMasa())
		return "Kamion:" +getIdVozila() + ", broj putnika:"+ putnici.size() + ", carinska dokumentacija="+imaCarinskuDokumentaciju+ " ,stvarna masa="+teret.getStvarnaMasa()+ ", deklarisana masa="+teret.getDeklarisanaMasa() ;
		else
			return 
					"Kamion:" +getIdVozila() + ", broj putnika:"+ putnici.size() + " ,stvarna i deklarisana masa nisu iste.";
	}

	
	 public void uvecajTeret()
	    {
	        teret.setStvarnaMasa(teret.getDeklarisanaMasa()+ teret.getDeklarisanaMasa()*30/100);
	    }
	 



	
	
}
