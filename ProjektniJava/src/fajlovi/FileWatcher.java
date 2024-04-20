package fajlovi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.logging.*;
import application.Main;
import application.PrvaContoller;
import simulacija.Simulacija;
import terminali.Terminal;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FileWatcher extends Thread{

	 private static final String provjeraPauza = "AktivnostTerminala.txt";
	 
	 public void provjeravajPauze() {
		    Path dir = Paths.get(System.getProperty("user.dir"));
		    try {
		        WatchService watchService = FileSystems.getDefault().newWatchService();
		        dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

		        while (!Simulacija.redVozila.isEmpty()) {  // Provjera statusa simulacije
		            WatchKey key = watchService.take();

		            for (WatchEvent<?> event : key.pollEvents()) {
		                WatchEvent.Kind<?> kind = event.kind();

		                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
		                    Path modifiedFile = (Path) event.context();
		                    if (modifiedFile.getFileName().toString().equals(provjeraPauza)) {
		                        handleModifiedFile(modifiedFile);
		                    }
		                }
		            }

		            key.reset();
		        }
		    } catch (IOException | InterruptedException e) {
		        e.printStackTrace();
		    }
		}
		




	 
    public  void handleModifiedFile(Path file) throws IOException {
        List<String> content = Files.readAllLines(file);
        for (String line : content) {
            String[] args = line.split(",");
            if (args.length >= 2) {
                String id = args[0];
                boolean pauza = Boolean.parseBoolean(args[1]);
                {
                	if (pauza==true) {
                    zaustaviTerminal(id);
                } else if(pauza==false) {
                	System.out.println("PAUZA NA FALSEEE");
                    pokreniTerminal(id);
                }
            }
            }
        }
    }
    
	    private void zaustaviTerminal(String id) {
	    	
	    	 for (Terminal t : Simulacija.policiskiTerminali) {
		            if (t.getIdTerminala() == Integer.parseInt(id) && !t.isPauza()) {
		                t.setPauza(true);
		               
		            }
		        }
	    	 
	        for (Terminal t : Simulacija.carinskiTerminali) {
	            if (t.getIdTerminala() == Integer.parseInt(id) && !t.isPauza()) {
	                t.setPauza(true);
	               
	            }
	        }
	        
	       
	    }

	    private void pokreniTerminal(String id) {
	    	
	    	for (Terminal t : Simulacija.policiskiTerminali) {
	            if (t.getIdTerminala() == Integer.parseInt(id) && t.isPauza()) {
	                t.setPauza(false);
	               
	            }
	        }
	    	 for (Terminal t : Simulacija.carinskiTerminali) {
		            if (t.getIdTerminala() == Integer.parseInt(id) && t.isPauza()) {
		                t.setPauza(false);
		               
		            }
		        }
		        
		        
	     
	    }
	
}
