package vozila;

public class Teret {
	
	private double stvarnaMasa;
	private double deklarisanaMasa;
	
	
	public Teret()
	{
		deklarisanaMasa=napraviDeklarisanaMasa();
		stvarnaMasa=deklarisanaMasa;	
	}
	
	
	public double getDeklarisanaMasa() {
		return deklarisanaMasa;
	}


	public void setDeklarisanaMasa(double deklarisanaMasa) {
		this.deklarisanaMasa = deklarisanaMasa;
	}


	public double napraviDeklarisanaMasa()
	{
		double minValue = 0.0;
	    double maxValue = 10000.0;
	    return minValue + (maxValue - minValue) * Math.random();
	}
	
	
	
	 public void setStvarnaMasa(double stvarnaMasa) {
		this.stvarnaMasa = stvarnaMasa;
	}
	 
	 
	 public double getStvarnaMasa() {
	       return this.stvarnaMasa;
	    }

	

}
