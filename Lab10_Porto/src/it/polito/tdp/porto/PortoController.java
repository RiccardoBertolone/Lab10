package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;
    
    private Model model;
    private boolean flag = false ;

    @FXML
    void handleCoautori(ActionEvent event) {
    	txtResult.clear() ;
    	Author a = boxPrimo.getValue() ;
    	if (a == null) {
    		txtResult.setText("Selezionare un autore");
    		return;
    	}
    	List<Author> coautori = model.getCoautori(a) ;
    	txtResult.setText("Coautori di "+a.getFirstname()+" "+a.getLastname()+":\n");
    	for (Author a2 : coautori) {
    		txtResult.appendText(a2.getFirstname()+" "+a2.getLastname()+"\n");
    	}
    	if (coautori.size()==0)
    		txtResult.appendText("Nessun coautore di "+a.getFirstname()+" "+a.getLastname()+"\n");
    	
    	List<Author> autoriDue = model.getAuthors() ;
    	for (Author a2 : coautori) {
    		autoriDue.remove(a2) ;
    	}
    	autoriDue.remove(a);
    	boxSecondo.getItems().addAll(autoriDue);
    	flag=true;
    	
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.clear();
    	if(!flag) {
    		txtResult.setText("Fare prima la parte uno!");
    		return;
    	}
    	Author a1 = boxPrimo.getValue() ;
    	Author a2 = boxSecondo.getValue() ;
    	if (a1 == null || a2 == null) {
    		txtResult.setText("Selezionare gli autori");
    		return;
    	}
    	txtResult.setText("Sequenza di papers che collega "+a1+" e "+a2+" :\n");
    	List<Paper> articoli = model.getPapers1(a1, a2) ;
    	for (Paper p : articoli) {
    		txtResult.appendText(p.toString());
    	}

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
		boxPrimo.getItems().addAll(model.getAuthors());
	}
}
