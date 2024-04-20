package simulacija;


import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;


import application.Main;
import application.PrvaContoller;
import fajlovi.FileWatcher;
import osobe.Putnik;
import terminali.C1;
import terminali.CK;
import terminali.CarinskiTerminal;
import terminali.P1;
import terminali.P2;
import terminali.PK;
import terminali.PolicijskiTerminal;
import vozila.Auto;
import vozila.Autobus;
import vozila.Kamion;
import vozila.Vozilo;

public class Simulacija implements Serializable {

	
	
	public static List<Vozilo> listaVozila= new ArrayList<Vozilo>();
	public static ConcurrentLinkedQueue<Vozilo> redVozila= new ConcurrentLinkedQueue<Vozilo>();
	//public static SynchronousQueue<Vozilo> redVozila=new SynchronousQueue<>();
	public static List<PolicijskiTerminal> policiskiTerminali= new ArrayList<PolicijskiTerminal>();
	public static List<CarinskiTerminal> carinskiTerminali= new ArrayList<CarinskiTerminal>();
	public FileWatcher fileWatcher;
	
	
	public static final int KAMIONI=10;
	private static final int AUTA=35;
	private static final int AUTOBUSI=5;
	
	Random rand= new Random();
	
	public Simulacija()
	{
		napraviVozila();
	    for (Vozilo v : listaVozila) {
	        try {
	            redVozila.add(v); 
	          
	            System.out.println(" poziicjaa " + v.getIdVozila()) ;
	          
	        } catch (IllegalStateException e) {
	        	 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
	            System.out.println("Red vozila je pun. Vozilo " + v.getIdVozila() + " nije dodato. Trenutna veličina reda: " + redVozila.size());
	        }
	    }
	    
	   napraviPolicisjkeTerminale();
	   napraviCarinskeTerminale();
	   generisiDokumentaciju();
	   generisiDokumetacijuStvarneMase();
	   fileWatcher= new FileWatcher();
	}
	
	public static int validnostFalse=0;
	public static int ukupanBrojPutnika=0;
	
	
	public void generisiDokumentaciju()
	{
		 int ukupanBrojPutnika = Auto.brojiPutnikeAuto + Autobus.brojiPutnikeAutobus + Kamion.brojiPutnikeKamion;
		    int procenatPutnikaFalse = ukupanBrojPutnika * 3 / 100;
		    int validnostFalse = 0;
		    Random random = new Random();

		    List<Putnik> sviPutnici = new ArrayList<>();

		    for (Vozilo v : redVozila) {
		        sviPutnici.addAll(v.getPutnici());
		    }

		    while (validnostFalse < procenatPutnikaFalse) {
		        int nasumicanIndeks = random.nextInt(sviPutnici.size());
		        Putnik putnik = sviPutnici.get(nasumicanIndeks);
		        if (putnik.isValidnostDokumentacije()) {
		            putnik.setValidnostDokumentacije(false);
		            validnostFalse++;
		            System.out.println("Putnik " + putnik.getIdPutnika() + " postavljen na false.");
		        }
		    }

		    System.out.println("Ukupno je " + ukupanBrojPutnika + " putnika, a nevažećih je " + validnostFalse);
	
	}
	
	public static int getPosition(Vozilo vozilo) {
	    if (redVozila.contains(vozilo)) {
	        LinkedList<Vozilo> tempQueue = new LinkedList<>(redVozila);
	        return tempQueue.indexOf(vozilo);
	    }
	    return -1; 
	}
	
	

	
	public static void napraviVozila()
	{
		for (int i=0;i<AUTA;i++)
		{
			Auto auto=new Auto();
			listaVozila.add(auto);
		
			
		}
		for(int i=0;i<KAMIONI;i++)
		{
			Kamion kamion= new Kamion();
			listaVozila.add(kamion);
	
		}
		for(int i=0;i<AUTOBUSI;i++)
		{
			
			Autobus autobus=new Autobus();
			listaVozila.add(autobus);
		
		}
	
		
		Collections.shuffle(listaVozila);
		
	
}
	
	public static void napraviPolicisjkeTerminale()
	{
		 P1 p1 = new P1(); 	
		 P2 p2 = new P2(); 
		 PK pk = new PK();
		
		 		 
		 policiskiTerminali.add(p1);		 
		 policiskiTerminali.add(p2); 
		 policiskiTerminali.add(pk); 
		
		 
	}
	
	public static void napraviCarinskeTerminale()
	{
		C1 c1=new C1();
		CK ck=new CK();
		 carinskiTerminali.add(c1);
		 carinskiTerminali.add(ck);
	
	}
		

	
    
    public void pokreni()
    { 
    	
    	 for(PolicijskiTerminal p : policiskiTerminali)
       	 {
       	 p.start();
       	 }
    	 
    	 for(CarinskiTerminal c :carinskiTerminali)
    	 {
    		c.start();
    	 }
    	
    	 for(Vozilo v: Simulacija.redVozila)
    	 {
    		// System.out.println("Ovo je funkcija pokreni"+ v.getIdVozila());
    		 v.start();
    	 }
    	 
    	fileWatcher.provjeravajPauze();
    	 
    }

 
	
   

   private static void generisiDokumetacijuStvarneMase() {
       int brojUvecanih = KAMIONI * 20 / 100;
       for (Vozilo v : redVozila) {
           if (v instanceof Kamion) {
        	   if(((Kamion) v).imaCarinskuDokumentaciju)
        	   { if (brojUvecanih > 0) {
                   ((Kamion) v).uvecajTeret();
                   brojUvecanih--;
                   System.out.println("OVO JE TAJ KAMION" + v.getIdVozila()+", a masa je: " +((Kamion)v).getTeret().getDeklarisanaMasa() +"-"+((Kamion)v).getTeret().getStvarnaMasa());  
               }
           }}
       }
   }
   
	
   public static boolean provjeriAktivnaVozila()
	{
	    
		for(Vozilo v: Simulacija.redVozila)
		{
			if(!v.kraj)
			
				return true;
				
		}
		

		return false;
						
	}
   
		

}
