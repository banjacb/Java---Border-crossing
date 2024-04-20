package application;




import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

import fajlovi.RadSaFajlovima;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import osobe.Putnik;
import simulacija.Simulacija;
import terminali.Terminal;
import vozila.Auto;
import vozila.Autobus;
import vozila.Kamion;
import vozila.Vozilo;


public class PrvaContoller implements Initializable{
	
	
	public static boolean pauzaKlikom= false;
	
	
	
	public static boolean isPauzaKlikom() {
		return pauzaKlikom;
	}


	public static void setPauzaKlikom(boolean pauzaKlikom) {
		PrvaContoller.pauzaKlikom = pauzaKlikom;
	}

	public  Simulacija s;

    @FXML
    public Label infoPolicijski1;
    
    @FXML
    public  Label infoPolicijski2;

    @FXML
    public  Label infoPolicijskiK;
    
    @FXML
    public Label labelSpecifDog;
	
    @FXML
    private static GridPane gpPrelaz;
   
    @FXML
    private ScrollPane pane2;
    
  
   
    @FXML
    private AnchorPane tbPrelaz2;
    
    @FXML
    private  AnchorPane tbPrelaz;

    @FXML
    private Label vrijeme;
  
    
    @FXML
    private Label trenutnoVrijeme;
  
    private boolean stop=false;
    
    @FXML
    private TextArea taInfoAuta;
    
    @FXML
    private TextField tfAuto;
    

    @FXML
    private TextFlow tfInfo;

    private Timeline timeline;
  
    @FXML
    public Pane p1;
    
    @FXML
    private Pane pane1;

    @FXML
    private Pane panep2;

    @FXML
    private Pane panec1;

    @FXML
    private Pane paneck;

    @FXML
    private Pane panek;

    @FXML
    public Label infoCarinski1;

    @FXML
    public Label infoCarinskiK;
    
    @FXML
    private ScrollPane paneZaJesu;

    @FXML
    private ScrollPane paneZaNisu;
    
    private int seconds = 0;
    
    
    
    @FXML
    void btnDetaljiAuta(ActionEvent event) {
    	
    	  List<String> evidencija = RadSaFajlovima.deserializujFile(RadSaFajlovima.kaznjenaVozila);

    	    StringBuilder sb = new StringBuilder();
    	    for (String line : evidencija) {
    	        sb.append(line).append("\n");
    	    }

    	    Label label = new Label(sb.toString());
    	    paneZaNisu.setContent(label);

    	    
    	    
    	    List<String> evidencija2 = RadSaFajlovima.deserializujFile(RadSaFajlovima.vozilaKojaSuPreslaGranicu);

    	    StringBuilder sb1 = new StringBuilder();
    	    for (String line : evidencija2) {
    	        sb1.append(line).append("\n");
    	    }

    	    Label label1 = new Label(sb1.toString());
    	  
    	    paneZaJesu.setContent(label1);

    }
    
 
    @FXML
    void btnOstalaVozila(ActionEvent event) throws IOException {
   
    	 Stage primaryStage = new Stage();
         Parent root = FXMLLoader.load(getClass().getResource("MainScane.fxml"));
         primaryStage.setTitle("Kolona");
       primaryStage.setScene(new Scene(root));
         primaryStage.show();
        
    }
   
    public final static Label pomP1 = new Label();
    public final static Label pomP2 = new Label();
    public final static Label pomPK = new Label();
    public final static Label pomC1 = new Label();
    public final static Label pomCK = new Label();
    public final static Label specifDogadjaj = new Label();
    
   public static void crtajGui()
    {
    	int kolona = 1;
        int red = 4;
        int brojac = 0;

        Iterator<Vozilo> iterator = Simulacija.redVozila.iterator();
        while (iterator.hasNext() && brojac < 5) {
            Vozilo vozilo = iterator.next();
            
            dodajGuiZaPrvih5(red, kolona, vozilo.getColor(), vozilo.getIdVozila());
            red++;
            brojac++;
        }
    }
    

   private static boolean simulacijaPokrenuta = false;
   @FXML 
    void btnPokreni(ActionEvent event) {
	   
	   if (timeline != null && timeline.getStatus() == Status.RUNNING) {
	        return;
	    }
	   
	   if (!simulacijaPokrenuta) {
	        simulacijaPokrenuta = true; 
	   Thread t = new Thread(new Runnable() {
           @Override
           public void run() {
        	  
        	   s.pokreni();
        	  
           }
       });
       t.start();
       
	  // Simulacija s = new Simulacija();
	   
    	if (timeline != null) {
            if (timeline.getStatus() == Status.PAUSED.RUNNING) {
                timeline.stop();
            } else {
                timeline.play();
            }
        }  {
            seconds = 0;
            timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    seconds++;
                    trenutnoVrijeme.setText("Vrijeme: " + seconds + " s");

                   
                    
                		
                    
                    // Provjera da li je redVozila prazan i zaustavljanje vremena
                    if (Simulacija.redVozila.isEmpty() && sviTerminaliSlobodni() ) {
                    	
                    	zaustaviTerminale();
                        timeline.stop();
                        Platform.runLater(() -> {
                            Stage stage = new Stage();
                            VBox layout = new VBox(10);
                            layout.setAlignment(Pos.CENTER);

                            Label timeLabel = new Label("Vrijeme izvođenja simulacije je: " + seconds + " s");
                            layout.getChildren().add(timeLabel);

                            Scene scene = new Scene(layout, 300, 200);
                            stage.setScene(scene);
                            stage.show();
                        });
                    }
                })
            );

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }

       
        dodajInformacije(pomP1, infoPolicijski1);
        dodajInformacije(pomP2, infoPolicijski2);
        dodajInformacije(pomPK, infoPolicijskiK);
        dodajInformacije(pomC1, infoCarinski1);
        dodajInformacije(pomCK, infoCarinskiK);
        
        dodajInformacije(specifDogadjaj, labelSpecifDog);
	   }
    }
   
   
   public void zaustaviTerminale()
   {
	   
	   for (Terminal t : Simulacija.policiskiTerminali) {
	     t.setRadiDokImaSta(false);
	        
	    }
	    
	    for (Terminal ct : Simulacija.carinskiTerminali) {
	    	ct.setRadiDokImaSta(false);
	    }
	    
   }
   
   
   public boolean sviTerminaliSlobodni()
   {
	   
	   for (Terminal t : Simulacija.policiskiTerminali) {
	        if (t.v != null) {
	     
	            return false;
	            
	            // Ako postoji vozilo, nije sve slobodno
	        }
	    }
	    
	  
	    PrvaContoller.obrisiVoziloNaPoziciji(8, 1);
		PrvaContoller.obrisiVoziloNaPoziciji(7, 1);
		PrvaContoller.obrisiVoziloNaPoziciji(6, 1);
		PrvaContoller.obrisiVoziloNaPoziciji(5, 1);
		PrvaContoller.obrisiVoziloNaPoziciji(4, 1);
	    
	    for (Terminal ct : Simulacija.carinskiTerminali) {
	        if (ct.v != null) {
	            return false; // Ako postoji vozilo, nije sve slobodno
	        }
	    }
	    


	    return true;
   }
   
   public static void dodajInfoZaTerminale(String opis, Label terminal) {
       Platform.runLater(() -> {
           terminal.setText(opis);
       });
   }
   
   public static void obrisiInfoZaTerminale(int terminal) {
	   Platform.runLater(() -> {
	        if (terminal == 1) {
	            PrvaContoller.pomP1.setText("");
	        } else if (terminal == 2) {
	            PrvaContoller.pomP2.setText("");
	        } else if (terminal == 3) {
	            PrvaContoller.pomPK.setText("");
	        } else if (terminal == 4) {
	            PrvaContoller.pomC1.setText("");
	        } else if (terminal == 5) {
	            PrvaContoller.pomCK.setText("");
	        }
	    });
	}
   
   private void dodajInformacije(Label labell, Label idLabel) {
	   labell.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
	        idLabel.setText(labell.getText());
	    }); 
	}
    
   

    
    @FXML
    void btnInfoAuta(ActionEvent event) {
    	
    	String idAuto = tfAuto.getText();
        int id = Integer.parseInt(idAuto);

        
      Vozilo vozilo= null;
        for (Vozilo v : Simulacija.listaVozila) {
         	
            if (v.getIdVozila() == id) {
             
                vozilo = v;
                break;
            }
        }

        if (vozilo != null) {
    
            String info;
            if (vozilo instanceof Auto) {
                info = ((Auto) vozilo).toString();
            } else if (vozilo instanceof Autobus) {
                info = ((Autobus) vozilo).toString();
            } else if (vozilo instanceof Kamion) {
                info = ((Kamion) vozilo).toString();
            } else {
                info = "Nepoznat tip vozila";
            }

            taInfoAuta.setText(info); 
        } else {
            taInfoAuta.setText("Vozilo sa ID-om " + id + " nije pronađeno."); 
        }
    }
    
	
    
    
    @FXML
    private TextArea taKazne;

    @FXML
    private TextArea taPrekrsaji;
    
    @FXML
    void btnPrikazi(ActionEvent event) {
    	
    	List<Putnik> putnici = RadSaFajlovima.deserijalizujPutnikeBinarno();
        StringBuilder sb = new StringBuilder();
   
        for (Putnik putnik : putnici) {
            sb.append(putnik.toString()).append("\n");
        }

        taKazne.setText(sb.toString());

        
        List<String> lista = RadSaFajlovima.deserializujFile(RadSaFajlovima.kazneNaCarinskomPrelazuFile);

        StringBuilder sbb = new StringBuilder();
        for (String line : lista) {
            sbb.append(line).append("\n");
        }

        taPrekrsaji.setText(sbb.toString());
        
        


     
    }

   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	

        
    		gpPrelaz= new GridPane();   	
    		gpPrelaz.setPrefWidth(368);
    	    gpPrelaz.setPrefHeight(342);
    	    gpPrelaz.setLayoutX(12);
    	    gpPrelaz.setLayoutY(35);
    	    // Dodavanje kolona
    	    ColumnConstraints col1 = new ColumnConstraints();
    	    col1.setHgrow(Priority.SOMETIMES);
    	    col1.setMinWidth(10);
    	    col1.setPrefWidth(100);

    	    ColumnConstraints col2 = new ColumnConstraints();
    	    col2.setHgrow(Priority.SOMETIMES);
    	    col2.setMinWidth(10);
    	    col2.setPrefWidth(100);

    	    ColumnConstraints col3 = new ColumnConstraints();
    	    col3.setHgrow(Priority.SOMETIMES);
    	    col3.setMinWidth(10);
    	    col3.setPrefWidth(100);

    	    ColumnConstraints col4 = new ColumnConstraints();
    	    col4.setHgrow(Priority.SOMETIMES);
    	    col4.setMinWidth(10);
    	    col4.setPrefWidth(100);

    	    ColumnConstraints col5 = new ColumnConstraints();
    	    col5.setHgrow(Priority.SOMETIMES);
    	    col5.setMinWidth(10);
    	    col5.setPrefWidth(100);

    	    gpPrelaz.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

    	    // Dodavanje redova
    	    RowConstraints row1 = new RowConstraints();
    	    row1.setMinHeight(10);
    	    row1.setPrefHeight(30);
    	    row1.setVgrow(Priority.SOMETIMES);

    	    RowConstraints row2 = new RowConstraints();
    	    row2.setMinHeight(10);
    	    row2.setPrefHeight(30);
    	    row2.setVgrow(Priority.SOMETIMES);

    	    RowConstraints row3 = new RowConstraints();
    	    row3.setMinHeight(10);
    	    row3.setPrefHeight(30);
    	    row3.setVgrow(Priority.SOMETIMES);

    	    RowConstraints row4 = new RowConstraints();
    	    row4.setMinHeight(10);
    	    row4.setPrefHeight(30);
    	    row4.setVgrow(Priority.SOMETIMES);

    	    RowConstraints row5 = new RowConstraints();
    	    row5.setMinHeight(10);
    	    row5.setPrefHeight(30);
    	    row5.setVgrow(Priority.SOMETIMES);

    	    RowConstraints row6 = new RowConstraints();
    	    row6.setMinHeight(10);
    	    row6.setPrefHeight(30);
    	    row6.setVgrow(Priority.SOMETIMES);

    	    RowConstraints row7 = new RowConstraints();
    	    row7.setMinHeight(10);
    	    row7.setPrefHeight(30);
    	    row7.setVgrow(Priority.SOMETIMES);

    	    RowConstraints row8 = new RowConstraints();
    	    row8.setMinHeight(10);
    	    row8.setPrefHeight(30);
    	    row8.setVgrow(Priority.SOMETIMES);

    	    gpPrelaz.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8);

    	 //  generisiTerminaleGui();
    	    
    	    // Dodavanje elemenata u GridPane
    	   // Pane paneC1 = new Pane();
    	    ct1.setPrefSize(200, 200);
    	    ct1.setStyle("-fx-background-color: #36c9b6;");
    	    Label labelC1 = new Label("C1-4");
    	    labelC1.setLayoutX(50);
    	    labelC1.setLayoutY(11);
    	    ct1.getChildren().add(labelC1);
    	    gpPrelaz.add(ct1, 1, 0);

    	   //Pane paneP1 = new Pane();
    	    pt1.setPrefSize(20, 20);
    	    pt1.setStyle("-fx-background-color: #2f32c7;");
    	    Label labelP1 = new Label("P1-1");
    	    labelP1.setLayoutX(50);
    	    labelP1.setLayoutY(11);
    	    pt1.getChildren().add(labelP1);
    	   gpPrelaz.add(pt1, 0,2 );

    	   // Pane paneP2 = new Pane();
    	    pt2.setPrefSize(200, 200);
    	    pt2.setStyle("-fx-background-color: #2f32c7;");
    	    Label labelP2 = new Label("P2-2");
    	    labelP2.setLayoutX(50);
    	    labelP2.setLayoutY(11);
    	    pt2.getChildren().add(labelP2);
    	    gpPrelaz.add(pt2, 2, 2);

    	   // Pane panePK = new Pane();
    	    ptk.setPrefSize(200, 200);
    	    ptk.setStyle("-fx-background-color: #2f32c7;"); 
    	    Label labelPK = new Label("PK-3");
    	    labelPK.setLayoutX(50);
    	    labelPK.setLayoutY(11);
    	    ptk.getChildren().add(labelPK);
    	    gpPrelaz.add(ptk, 4, 2);

    	   // Pane paneCK = new Pane();
    	    ctk.setPrefSize(200, 200);
    	    ctk.setStyle("-fx-background-color: #36c9b6;");
    	    Label labelCK = new Label("CK-5");
    	    labelCK.setLayoutX(50);
    	    labelCK.setLayoutY(11);
    	    ctk.getChildren().add(labelCK);
    	    gpPrelaz.add(ctk, 4, 0);

   
    	    tbPrelaz.getChildren().add(gpPrelaz);
    	
    	   
     s= new Simulacija();

     crtajGui();
 	
     
     
    	 
} 
    
  

   
    
  public static void dodajGuiZaPrvih5(int row, int column, Color color, long l) {
	
	   Pane pane = new Pane();
	    pane.setPrefSize(40, 40);
	    String colorString = String.format("#%02x%02x%02x",
	            (int) (color.getRed() * 255),
	            (int) (color.getGreen() * 255),
	            (int) (color.getBlue() * 255));
	    pane.setStyle("-fx-background-color: " + colorString + ";");

	    Label label = new Label();
	    label.setText(String.valueOf(l));

	    Platform.runLater(() -> {
	        GridPane.setConstraints(pane, column, row);  
	        GridPane.setConstraints(label, column, row); 
	        gpPrelaz.getChildren().addAll(pane, label); 
	    });
	   
	    
	  
	 
}
  
  
    
    final static Pane pt1 = new Pane();
    final static Pane pt2 = new Pane();
    final static Pane ptk = new Pane();

    final static Pane ct1 = new Pane();
    final static Pane ctk = new Pane();

    public static void dodajGui(Color color, long l, int idTerminal) {
        Pane pane = new Pane();
        pane.setPrefSize(40, 40);
        String colorString = String.format("#%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        pane.setStyle("-fx-background-color: " + colorString + ";");

        Label label = new Label();
        label.setText(String.valueOf(l));

        Platform.runLater(() -> {
            switch (idTerminal) {
                case 1:
                    pt1.getChildren().addAll(pane, label);
                    break;
                case 2:
            
                    pt2.getChildren().addAll(pane, label);
                    break;
                case 3:
               
                    ptk.getChildren().addAll(pane, label);
                    break;
                case 4:
                   
                    ct1.getChildren().addAll(pane, label);
                    break;
                case 5:
                    
                    ctk.getChildren().addAll(pane, label);
                    break;
                    
                default:
                    break;
            }
        });
    }
    

  
    public synchronized static void ukloniGui(int idTerminal) {
        Platform.runLater(() -> {
            Pane targetPane = null;
            switch (idTerminal) {
                case 1:
                    targetPane = pt1;
                    break;
                case 2:
                    targetPane = pt2;
                    break;
                case 3:
                    targetPane = ptk;
                    break;
                case 4:
                    targetPane = ct1;
                    break;
                case 5:
                    targetPane = ctk;
                    break;
                default:
                    // Implementirajte obradu za nepodržane idTerminal vrijednosti
                    break;
            }
            
            if (targetPane != null) {
                Node labelNode = findLabel(targetPane);
                List<Node> nodesToRemove = new ArrayList<>();
                
                for (Node node : targetPane.getChildren()) {
                    if (node != labelNode) {
                        nodesToRemove.add(node);
                    }
                }

                targetPane.getChildren().removeAll(nodesToRemove);
            }
        });
    }
 
    private static Node findLabel(Pane parent) {
        for (Node node : parent.getChildren()) {
            if (node instanceof Label) {
                return node;
            }
        }
        return null;
    }


    
    public static void obrisiVoziloNaPoziciji(int red,int kolona) {
    	  Platform.runLater(() -> {
    	        List<Node> nodesToRemove = new ArrayList<>();

    	        for (Node node : gpPrelaz.getChildren()) {
    	            Integer nodeRow = GridPane.getRowIndex(node);
    	            Integer nodeColumn = GridPane.getColumnIndex(node);

    	            if (nodeRow != null && nodeColumn != null && nodeRow == red && nodeColumn == kolona) {
    	                nodesToRemove.add(node);
    	            }
    	        }

    	        gpPrelaz.getChildren().removeAll(nodesToRemove);
    	    });
    	
    }
    
    public static void azurirajPrvih5Vozila(Queue<Vozilo> redVozila,int brojac)
    {
    	 int kolona = 1;
    	    int red = 4;
    	   
    	    
    	    int brojVozila = Simulacija.redVozila.size();
    	    Iterator<Vozilo> iterator = redVozila.iterator();

    	    while (iterator.hasNext() && brojac>0 && brojVozila>0) {
    	    	
    	    Vozilo vozilo = iterator.next();
    	        dodajGuiZaPrvih5(red, kolona, vozilo.getColor(), vozilo.getIdVozila());
    	    		
    	    	
    	 
    	        red++;
    	        brojac--;
    	        brojVozila--;
    	    
    	    
    	    		}
    	   
    }
   
 
   

   private Object zakljucavanjePauze= new Object(); 

   @FXML
   void btnPauza(ActionEvent event) {
	   pauzaKlikom = !pauzaKlikom; // Promeni status pauze

	    synchronized (zakljucavanjePauze) {
	        if (pauzaKlikom) {
	            // Pauziraj simulaciju
	            timeline.pause();
	            
	            for(Terminal  t: Simulacija.carinskiTerminali)
	            {
	            	t.setPauza(true);
	            }
	            
	            for(Terminal  t: Simulacija.policiskiTerminali)
	            {
	            	t.setPauza(true);
	            }
	            
	        } else {
	            // Nastavi simulaciju
	            timeline.play();
	            
	            for(Terminal  t: Simulacija.carinskiTerminali)
	            {
	            	t.setPauza(false);
	            }
	            
	            for(Terminal  t: Simulacija.policiskiTerminali)
	            {
	            	t.setPauza(false);
	            }
	        }
	    }
   }
   
   
   public static void ispisiSpecifDog(String opis) {
       Platform.runLater(() -> {
           specifDogadjaj.setText(opis);
       });
   }
   
   

}
