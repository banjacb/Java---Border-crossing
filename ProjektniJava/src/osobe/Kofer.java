package osobe;

import java.util.Random;

import vozila.Autobus;

import java.io.Serializable;
import java.util.List;

public class Kofer implements Serializable{
	
	public boolean ispravnost;
	

	public Kofer(boolean ispravnost) {
		
		this.ispravnost =ispravnost;
	}
	
	
	public Kofer()
	{
		ispravnost=true;

	}
	
	public boolean isIspravnost() {
		return ispravnost;
	}

	public void setIspravnost(boolean ispravnost) {
		this.ispravnost = ispravnost;
	}
	


	@Override
	public String toString() {
		return " ispravnost kofera=" + ispravnost + "]"+ "\n";
	}


	
	
	
}
	
    
	
	


