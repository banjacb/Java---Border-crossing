package fajlovi;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import application.Main;
import osobe.Putnik;

public class RadSaFajlovima {

	public static final File kazneNaPolicijskomPrelazuFile = new File(System.getProperty("user.dir") + File.separator + "kazne" + System.currentTimeMillis() + ".ser");
	public static final File kazneNaCarinskomPrelazuFile= new File(System.getProperty("user.dir")+ File.separator + "kaznee" + System.currentTimeMillis() +".txt");
	public static final File vozilaKojaSuPreslaGranicu = new File(System.getProperty("user.dir") + File.separator + "predjeni.txt");
	public static final File kaznjenaVozila = new File(System.getProperty("user.dir") + File.separator + "kaznjeni.txt");
	
	public static synchronized void upisiPutnikaBinarno(Putnik putnik)
    {
	    File f = kazneNaPolicijskomPrelazuFile;
	    if (!f.exists()) {
	        try {
	            f.createNewFile();
	        } catch (IOException e) {
	        	 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
	        }
	    }

	    List<Putnik> putnici;
	    if (f.length() > 0) {
	        putnici = deserijalizujPutnikeBinarno();
	    } else {
	        putnici = new ArrayList<>();
	    }
	    putnici.add(putnik);

	    try {
	        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
	        for (Putnik p : putnici) {
	            oos.writeObject(p);
	        }
	        oos.close();
	    } catch (IOException e) {
	    	 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
	    }

    }

	public static synchronized List<Putnik> deserijalizujPutnikeBinarno() {
	    List<Putnik> putnici = new ArrayList<>();
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(kazneNaPolicijskomPrelazuFile))) {
	        while (true) {
	            Object o = ois.readObject();
	            if (o instanceof Putnik) {
	                Putnik p = (Putnik) o;
	                putnici.add(p);
	            }
	        }
	    } catch (EOFException e) {
	        // Očekujemo EOF izuzetak kad dođemo do kraja datoteke
	    } catch (FileNotFoundException e) {
	        System.out.println("Još nema serijalizovanih podataka.");
	    } catch (IOException | ClassNotFoundException e) {
	        Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
	    }
	    return putnici;
	}
    
    public static synchronized void upisiUFajl(File file, String text) {
    	   if (!file.exists()) {
    	        try {
    	            file.createNewFile();
    	        } catch (IOException e) {
    	        	 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
    	        }
    	    }
            PrintWriter out = null;
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                out.println(text);
            } catch (IOException e) {
            	 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
            } finally {
                if (out != null) {
                    out.close();
                }
            }
    }
    
    public static List<String> deserializujFile(File f)
    {
        if(!f.exists())
        {
            List<String> l = new ArrayList<>();
          //  l.add("");
            return l;
        }
        try {
            return Files.readAllLines(f.toPath());
        } catch (Exception e) {
        	 Main.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }
        return null;
    }
    
  
    
}
