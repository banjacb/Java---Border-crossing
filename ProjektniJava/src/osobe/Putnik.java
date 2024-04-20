package osobe;

import java.io.Serializable;
import java.util.Random;

import vozila.Auto;
import vozila.Autobus;
import vozila.Kamion;
import vozila.Vozilo;

public class Putnik extends Osoba implements Serializable {

	private Kofer kofer;
	private boolean imaKofer;
	private boolean isVozac;
	public boolean validnostDokumentacije;

	public Putnik()
	{
		    this.isVozac=false;
			this.imaKofer=false;
			this.kofer=null;
			this.validnostDokumentacije=true;
			//generisiDokumentaciju();
	}

	
	
	
	
	public Putnik(boolean isVozac)
	{
		
		    this.isVozac=isVozac;
			this.imaKofer=false;
			this.kofer=null;
			
	}


	public Kofer getKofer() {
		return kofer;
	}

	public void setKofer(Kofer kofer) {
		this.kofer = kofer;
	}

	public boolean isImaKofer() {
		return imaKofer;
	}

	public void setImaKofer(boolean imaKofer) {
		this.imaKofer = imaKofer;
	}

	public boolean isVozac() {
		return isVozac;
	}

	public void setVozac(boolean isVozac) {
		this.isVozac = isVozac;
	}

	public boolean isValidnostDokumentacije() {
		return validnostDokumentacije;
	}


	public  void setValidnostDokumentacije(boolean validnostDokumentacije) {
		this.validnostDokumentacije = validnostDokumentacije;
	}
		

	@Override
	public String toString() {
		if(imaKofer== false)
		{return "\nPutnik id=" + getIdPutnika()+ "[kofer=" + kofer + ", imaKofer=" + imaKofer + ", isVozac=" + isVozac
				+ ", validnostDokumentacije=" + validnostDokumentacije +"]\n";}
		else
			{return "\nPutnik id=" + getIdPutnika()+ "[kofer=" + kofer.ispravnost + ", imaKofer=" + imaKofer + ", isVozac=" + isVozac
					+ ", validnostDokumentacije=" + validnostDokumentacije +"]\n";
			}
			
	
	}

	
	
	
	
	
	
}
