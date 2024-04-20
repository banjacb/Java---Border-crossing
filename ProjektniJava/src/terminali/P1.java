package terminali;

import fajlovi.FileWatcher;
import javafx.scene.paint.Color;
import osobe.Putnik;
import simulacija.Simulacija;
import vozila.*;

public class P1 extends PolicijskiTerminal{

	public static final int IdTERMINALA=1;

    
	public P1() {
		super(IdTERMINALA,false);
		setZaKamion(false);
		setRadiDokImaSta(true);
	}


	

	
}
