package terminali;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class C1 extends CarinskiTerminal  {

	public static final int IdTERMINALA=4;
	public C1()
	{
		super(IdTERMINALA,false);
		setZaKamion(false);
		setRadiDokImaSta(true);
	}

}
