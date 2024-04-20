package osobe;

public abstract class Osoba {

	private int id;
	public static int pomm=0;
	
	public Osoba() {
		this.id=++pomm;
		
	}


	public int getIdPutnika() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	
}
