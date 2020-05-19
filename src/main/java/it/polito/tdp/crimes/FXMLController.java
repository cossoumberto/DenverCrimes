/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<DefaultWeightedEdge> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	if(boxArco.getItems().size()==0)
    		txtResult.setText("ANALIZZA PRIMA I QUARTIERI");
    	else if(boxArco.getValue()==null)
    		txtResult.setText("SELEZIONA UN ARCO");
    	else {
    		String source = model.getGrafo().getEdgeSource(boxArco.getValue());
    		String end = model.getGrafo().getEdgeTarget(boxArco.getValue());
    		txtResult.setText("CAMMINO MASSIMO TRA I 2 VERTICI:\n");
    		for(String s : model.cercaCammino(source, end))
    			txtResult.appendText(s + "\n");
    		boxArco.getItems().clear();
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	boxArco.getItems().clear();
    	String cat = boxCategoria.getValue();
    	Integer mese = boxMese.getValue();
    	if(mese!=null && cat!=null) {
    		model.creaGrafo(cat, mese);
    		Graph <String, DefaultWeightedEdge> grafo = model.getGrafo();
    		for(DefaultWeightedEdge e : model.archiPesoMedio()) {
    			txtResult.appendText(grafo.getEdgeSource(e) + " : " + grafo.getEdgeTarget(e) + " Weight: " + grafo.getEdgeWeight(e) + "\n");
    			boxArco.getItems().add(e);
    		}
    	}
    	else 
    		txtResult.setText("SELEZIONE CATEGORIA E MESE");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxCategoria.getItems().addAll(model.getCategorie());
    	for(Integer i=1; i<=12; i++)
    		boxMese.getItems().add(i);
    }
}
