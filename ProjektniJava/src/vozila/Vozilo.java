package vozila;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import application.Main;
import application.PrvaContoller;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import osobe.Putnik;
import simulacija.Simulacija;
import terminali.CarinskiTerminal;
import terminali.PolicijskiTerminal;
import terminali.Terminal;


public abstract class Vozilo extends Thread{
	
	protected long vrijemeNaPolicijskom;
	protected long vrijemeNaCarinskom;

	protected int idVozila;
	public ArrayList<Putnik> putnici;
	static private int pom=1;
	public Color color;
	public PolicijskiTerminal policijskiTerminal;
	public CarinskiTerminal carinskiTerminal;
	 
	public static Object izaberiPolicijskiTerminal = new Object();
	public static Object izaberiCarinskiTerminal = new Object();
	
	 public boolean kraj=false;
	
	public boolean predjiNaPolicijski= false;
	public boolean predjiNaCarinski= false;

	public  boolean cekamObraduNaPolicijskom=true;
	public boolean cekamObraduNaCarinskom=true;
	
	public boolean zavrsiPolicijski=false;
	public boolean zavrsiCarisni=false;

	
	static int poml=50;
	
	public long getVrijemeNaPolicijskom() {
		return vrijemeNaPolicijskom;
	}



	public void setVrijemeNaPolicijskom(long vrijemeNaPolicijskom) {
		this.vrijemeNaPolicijskom = vrijemeNaPolicijskom;
	}



	public long getVrijemeNaCarinskom() {
		return vrijemeNaCarinskom;
	}



	public void setVrijemeNaCarinskom(long vrijemeNaCarinskom) {
		this.vrijemeNaCarinskom = vrijemeNaCarinskom;
	}



	public Color getColor() {
		return color;
	}

	

	public int getCarinskiTerminal() {
		return carinskiTerminal.getIdTerminala();
	}



	public void setCarinskiTerminal(CarinskiTerminal carinskiTerminal) {
		this.carinskiTerminal = carinskiTerminal;
	}



	public void setColor(Color color) {
		this.color = color;
	}


	public Vozilo() {
	
		this.idVozila = pom++;
	
	}

	public int getIdVozila() {
		return idVozila;
	}

	

	
	public ArrayList<Putnik> getPutnici() {
		return putnici;
	}


	public void setPutnici(ArrayList<Putnik> putnici) {
		this.putnici = putnici;
	}
    
	abstract boolean izborTerminala(Terminal t);

	
	@Override
	public void run()
	{
		
		while (!kraj) 
		{
		
		if (Simulacija.getPosition(this) == 0) 
        {

            synchronized (izaberiPolicijskiTerminal) {
                do {
                    izaberiJedanPoliciskiTerminal();
                } while (!this.predjiNaPolicijski);
            		}
            
        
            synchronized (this) {
                while (this.cekamObraduNaPolicijskom) {
                    try {
                       
                        if (this.zavrsiPolicijski) {
                            break;
                        }
                        sleep(500);
                    } catch (InterruptedException e) {
                    	 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
                    }
                }
               System.out.println("Ovjde je zavrsio policijski");  
                cekamObraduNaPolicijskom = false;           
                             
            }    
            
        } 
        
        else if(this.zavrsiPolicijski && !this.kraj)
        {
        	
        	synchronized(this.policijskiTerminal)
        	{
        		PrvaContoller.ukloniGui(policijskiTerminal.getIdTerminala());
        	}
        	synchronized (izaberiCarinskiTerminal) {
        		do {
                    izaberiJedanCarinskiTerminal();
                } while (!this.predjiNaCarinski);
			}
        	
        	synchronized (this.policijskiTerminal) {
        		//PrvaContoller.ukloniGui(policijskiTerminal.getIdTerminala());
        		policijskiTerminal.v=null;	
        		this.policijskiTerminal=null;
        		
			}
        	
        	
        	  synchronized (this) 
        	  {
	                while (this.cekamObraduNaCarinskom) {
	                    try {
	                    	if (this.zavrsiCarisni) {
	                            break;
	                        }
	                        sleep(500); 
	                    } catch (InterruptedException e) {
	                    	 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
	                    } 
	                    
	                }
	                
	             this.cekamObraduNaCarinskom=false; 
	           
	        	 PrvaContoller.ukloniGui(this.carinskiTerminal.getIdTerminala());
		        kraj=true;
	       
     	
        	  }
        }
        	
     
        
        else if (Simulacija.getPosition(this) > 0 && !zavrsiPolicijski) {
            synchronized (this) {
                while (Simulacija.getPosition(this) > 0 && !zavrsiPolicijski) {
                    try {
                       sleep(500); 
                    } catch (InterruptedException e) {
                    	 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
                    }
                    
                                      
                    if(Simulacija.getPosition(this)<5 )
                    {
                    	PrvaContoller.azurirajPrvih5Vozila(Simulacija.redVozila,5);
                    }
          
                }
                
               
            }
            
        }
        
        
        
        if(this.kraj)
        {
        poml--;
        	System.out.println("Ovo je kraj  za : " + this.getIdVozila() + " a ostalo je jos " + poml);
        }
        
    
      
      
	}
		
}
			
		

			
			
				
	
		
	
	
	
	
	public void izaberiJedanPoliciskiTerminal() 
	{	
		for(PolicijskiTerminal t: Simulacija.policiskiTerminali)
		{
		    		if ( t.v == null && !this.predjiNaPolicijski && this.izborTerminala(t))
		    		{
		            synchronized (this) {
		            	
		                this.predjiNaPolicijski = true;
		              //  System.out.println("Vozilo naslo policijski terminal:  " + this.idVozila + " a to je " + t.getIdTerminala());
		                this.policijskiTerminal = t;
		                 
		            }

		            synchronized (t) {
		              // System.out.println("Dodijeli auto nekom terminalu.");
		                t.v = this;
		               dodajGuiZaTerminale(this, this.getPolicijskiTerminal());            
		            	}  
		           
		            break;
		    		}  
			}
		 Simulacija.redVozila.remove(this);
		}
	
	
	
	public void izaberiJedanCarinskiTerminal()
	{
		
		for( CarinskiTerminal ct: Simulacija.carinskiTerminali)
		{
			if(ct.v == null && !this.predjiNaCarinski && this.izborTerminala(ct))
			{
				synchronized (this) {
			
					this.predjiNaCarinski=true;
					this.carinskiTerminal=ct;
					
					
				}
				
				synchronized (ct) {
					//System.out.println("zadnjihhhhhhhhhhhh Vozilo naslo carinski terminal:  " + this.idVozila + " a to je " + ct.getIdTerminala());
					ct.v=this;
					dodajGuiZaTerminale(this, this.getCarinskiTerminal());
				}
			break;
			
			}
		}
		
	}
	

	
	public int getPolicijskiTerminal() {
		return policijskiTerminal.getIdTerminala();
	}


	public void setPolicijskiTerminal(PolicijskiTerminal policijskiTerminal) {
		this.policijskiTerminal = policijskiTerminal;
	}


	public void dodajGuiZaTerminale(Vozilo v,int idTerminal)
	{
		if(idTerminal==1)//4-0
			{
			
			PrvaContoller.dodajGui( v.getColor(), v.getIdVozila(),idTerminal);
		    PrvaContoller.dodajInfoZaTerminale("Na terminalu se trenutno obrađuje vozilo " + this.getIdVozila(), PrvaContoller.pomP1);
		 
			}
		
		else if(idTerminal==2) //4-2
		{
			 PrvaContoller.dodajGui( v.getColor(), v.getIdVozila(),idTerminal);	
			 PrvaContoller.dodajInfoZaTerminale("Na terminalu se trenutno obrađuje vozilo " + this.getIdVozila(), PrvaContoller.pomP2);
		}
		else if(idTerminal==3) //4-4
		{
			 PrvaContoller.dodajGui( v.getColor(), v.getIdVozila(),idTerminal);	
			 PrvaContoller.dodajInfoZaTerminale("Na terminalu se trenutno obrađuje vozilo " + this.getIdVozila(), PrvaContoller.pomPK);
		}
		else if(idTerminal==4) //1-1
		{
			 PrvaContoller.dodajGui( v.getColor(), v.getIdVozila(),idTerminal);
			 PrvaContoller.dodajInfoZaTerminale("Na terminalu se trenutno obrađuje vozilo " + this.getIdVozila(), PrvaContoller.pomC1);
		}
		else if(idTerminal==5)//1-4
		{
			 PrvaContoller.dodajGui(v.getColor(), v.getIdVozila(),idTerminal);
			 PrvaContoller.dodajInfoZaTerminale("Na terminalu se trenutno obrađuje vozilo " + this.getIdVozila(), PrvaContoller.pomCK);
		}
	}
	
	

	
	
}
