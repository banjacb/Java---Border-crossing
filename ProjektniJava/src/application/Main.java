package application;
	
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import osobe.Kofer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import simulacija.Simulacija;
import java.util.logging.*;
import vozila.Auto;
import vozila.Vozilo;


public class Main extends Application {
	
	   public static Handler handler;
	    public static Logger logger;
	    
	    static {
	        try {
	            handler = new FileHandler("terminaliLogger.log", true);
	            logger = Logger.getLogger(Main.class.getName());
	            logger.addHandler(handler);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root= FXMLLoader.load(getClass().getResource("Prva.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {	
		 launch(args);	
	
}

}
