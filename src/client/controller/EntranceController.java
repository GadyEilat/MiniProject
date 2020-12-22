package client.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EntranceController extends AbstractScenes{

    @FXML
    private Button travelerEnter;

    @FXML
    private Button workerEnter;

    @FXML
    private Button helpBtn;

    @FXML
    void btnWorker(ActionEvent event) {
    	switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
    }
    
    @FXML
    void travelerEnter(ActionEvent event) {
    	//TODO: switchScenes
    }
    
    @FXML
	void helpWindowPopOut(ActionEvent event) throws IOException {
    	Stage helpWindow = new Stage();
    	FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/client/boundaries/help.fxml"));
    	Parent current = fxmlLoad.load();
    	helpWindow.initModality(Modality.APPLICATION_MODAL);
    	helpWindow.setTitle("Help");
    	Scene scene = new Scene(current);
    	helpWindow.setMinHeight(400);
    	helpWindow.setMinWidth(600);
    	helpWindow.setMaxHeight(400);
    	helpWindow.setMaxWidth(600);
    	helpWindow.setScene(scene);
    	helpWindow.showAndWait();
    	
    } 
    

}
