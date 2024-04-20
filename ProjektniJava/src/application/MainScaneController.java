package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import simulacija.Simulacija;
import vozila.Vozilo;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScaneController implements Initializable {


    @FXML
    private ScrollPane scrollOstalaVozila;
    
 
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	dodajPreostalaVozila();
    }
    
    
    public  void dodajPreostalaVozila ()
    {
    	
    	VBox box = new VBox();
    	box.setSpacing(10);
        int brojac = 0;
        for (Vozilo vozilo : Simulacija.redVozila) {
          
        	if (brojac >= 5) {
                Pane pane = new Pane();
                pane.setPrefSize(40, 40);
                String colorString = String.format("#%02x%02x%02x",
                        (int) (vozilo.getColor().getRed() * 255),
                        (int) (vozilo.getColor().getGreen() * 255),
                        (int) (vozilo.getColor().getBlue() * 255));
                pane.setStyle("-fx-background-color: " + colorString + ";");

                Label label = new Label();
                label.setText(String.valueOf(vozilo.getIdVozila()));
                pane.getChildren().add(label); 

               
                box.getChildren().addAll(pane);
            }
            brojac++;
        }
        


            scrollOstalaVozila.setContent(box);
       
    	//System.out.println("Nakon dodavanja svih vozila velicina je "+ Simulacija.redVozila.size());
    }
    
}
