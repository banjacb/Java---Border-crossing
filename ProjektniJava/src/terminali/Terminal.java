package terminali;

import vozila.Vozilo;

public abstract class Terminal extends Thread{

	private int id;
	
	public Vozilo v;
	protected boolean zaKamion;
	protected boolean pauza;
	protected static boolean radiDokImaSta=true;
	
	
	
	public boolean isRadiDokImaSta() {
		return radiDokImaSta;
	}

	

	public static void setRadiDokImaSta(boolean radiDokImaSta) {
		Terminal.radiDokImaSta = radiDokImaSta;
	}



	public boolean isPauza() {
		return pauza;
	}

	public void setPauza(boolean pauza) {
		this.pauza = pauza;
	}

	public boolean isZaKamion() {
		return zaKamion;
	}

	public void setZaKamion(boolean zaKamion) {
		this.zaKamion = zaKamion;
	}

	

	public Terminal(int id,boolean pauza)
	{
		this.id=id;
		this.pauza=pauza;
	}
	public int getIdTerminala() {
		return id;
	}

	public void setIdTerminala(int id) {
		this.id = id;
	}

	

	
}
