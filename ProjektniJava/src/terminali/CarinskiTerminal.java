package terminali;

import java.util.logging.Level;

import application.Main;
import application.PrvaContoller;
import fajlovi.RadSaFajlovima;
import osobe.Putnik;
import simulacija.Simulacija;
import vozila.Auto;
import vozila.Autobus;
import vozila.Kamion;
import vozila.Vozilo;

public class CarinskiTerminal extends Terminal {

	
	
	public CarinskiTerminal(int id,boolean pauza) {
		super(id,pauza);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() 
	{
		
		while(Simulacija.provjeriAktivnaVozila() || this.v!=null || radiDokImaSta)
		{
			provjeriRadTerminala();
			provjeriRadTerminalaKlikom();
			
			/*if(this.v==null)
			{ 
				try {
					sleep(500);
				} catch (InterruptedException e) {
					 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
				}
			}*/
			
			 if( this.v!=null && !v.zavrsiCarisni)
			{
		
				synchronized (this) {
					
					if(v instanceof Auto)
					{			
						try {
							sleep(v.getVrijemeNaCarinskom());
						} catch (InterruptedException e) {
							
							 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
							}
	
						v.zavrsiCarisni=true;	
						PrvaContoller.obrisiInfoZaTerminale(this.getIdTerminala());
						RadSaFajlovima.upisiUFajl(RadSaFajlovima.vozilaKojaSuPreslaGranicu, v.getIdVozila() + " - preslo granicu. AUTO");
						
					this.v=null;
					System.out.println("CARINSKI*********************Ovdje je obradjen auto.");
					}
					else if (v instanceof Autobus)
					{
						try {
							sleep(v.getVrijemeNaCarinskom());
						} catch (InterruptedException e) {
							
							 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
							} 
							for(int i=0;i< v.getPutnici().size();i++)
							{
								Putnik putnik= v.getPutnici().get(i);
								if(putnik.isImaKofer())
								{
									if(!putnik.getKofer().isIspravnost())
									{
								
										RadSaFajlovima.upisiUFajl(RadSaFajlovima.kazneNaCarinskomPrelazuFile, v.getIdVozila() +" - u autobusu putnik sa nedozvoljenim stvarima u koferu, id putnika:"+ putnik.getIdPutnika());
										System.out.println("Putnik "+ putnik.getIdPutnika() +" ima nedozvoljene stvari a nalazi se u vozilu " + v.getIdVozila());
									
										PrvaContoller.ispisiSpecifDog("Na terminalu "+ this.getIdTerminala() +" je izbacen putnik: " + putnik.getIdPutnika()+" iz autobusa zbog nedozvoljenih stavri");
										v.getPutnici().remove(putnik);
										
										i++;
										
									}
								}
								
							}
							RadSaFajlovima.upisiUFajl(RadSaFajlovima.vozilaKojaSuPreslaGranicu, v.getIdVozila() +" - preslo granicu. AUTOBUS");
							PrvaContoller.obrisiInfoZaTerminale(this.getIdTerminala());
							System.out.println("CARINSKI*********************Ovdje je obradjen autobus.");
							v.zavrsiCarisni=true;
							this.v=null;
					}
					else if( v instanceof Kamion)
					{
						try {
							sleep(v.getVrijemeNaCarinskom());
						} catch (InterruptedException e) {
							
							 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
						}
						
						if(((Kamion)v ).isImaCarinskuDokumentaciju())
						{
							System.out.println("DEKLARISANA/STVARNA >>>>" + ((Kamion)v).getTeret().getDeklarisanaMasa() +" /"+((Kamion)v).getTeret().getStvarnaMasa());
							if(((Kamion) v).getTeret().getStvarnaMasa() > (((Kamion)v).getTeret().getDeklarisanaMasa()))
							{
								System.out.print("OOOOOOOOOOO");
								RadSaFajlovima.upisiUFajl(RadSaFajlovima.kaznjenaVozila, v.getIdVozila() + "- vozilo ,kamion, kaznjeno na carinskom terminalu. Pretovar kamiona, stvarna masa:deklarisana masa - " + ((Kamion) v).getTeret().getStvarnaMasa() + ":"+((Kamion) v).getTeret().getDeklarisanaMasa());
								RadSaFajlovima.upisiUFajl(RadSaFajlovima.kazneNaCarinskomPrelazuFile, v.getIdVozila() +" - kamion kaznjen na carinskom prelazu.");
								System.out.println("IMAMO NEISPRAVAN KAMION--------------------------.");
								PrvaContoller.ispisiSpecifDog("Na terminalu "+ this.getIdTerminala() +"  je vracen kamion :"+ v.getIdVozila() +" zbog pretovara mase.");
								v.getPutnici().clear();
							
							}
							else
							{
								RadSaFajlovima.upisiUFajl(RadSaFajlovima.vozilaKojaSuPreslaGranicu, v.getIdVozila() +" - preslo granicu. KAMION");
								System.out.println("Dokumentacija je ispravna.");
							}
						}
						
						else 
						{
							RadSaFajlovima.upisiUFajl(RadSaFajlovima.vozilaKojaSuPreslaGranicu, v.getIdVozila() +" - preslo granicu.KAMION");
							System.out.println("Kamion ide dalje, nije potrebna dokumentacija.");
							
							
						}
						
							System.out.println("CARINSKI*********************Ovdje je obradjen kamion.");
							PrvaContoller.obrisiInfoZaTerminale(this.getIdTerminala());
							v.zavrsiCarisni=true;
							this.v=null;
					}
				}
				
				
			}
		}
	}

	private void provjeriRadTerminala() {
	    while (pauza) {
	       // System.out.println("PAUZA NA Carinskom TERMINALU " +this.getIdTerminala());
	        try {
	            Thread.sleep(1000); 
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	private void provjeriRadTerminalaKlikom() {
	    while (PrvaContoller.pauzaKlikom) {
	       // System.out.println("PAUZA NA Carinskom TERMINALU KLIKOM " +this.getIdTerminala());
	        try {
	            Thread.sleep(1000); 
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}



	
}
