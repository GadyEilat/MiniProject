package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import client.logic.Visitor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TourGuidePaymentController extends AbstractScenes {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button completeTourOrderBtn;

    @FXML
    private SplitMenuButton cardTypeBtn;

    @FXML
    private SplitMenuButton yearBtn;

    @FXML
    private SplitMenuButton mmBtn;

    @FXML
    private Button LogOutBtn;

    @FXML
    private Button updateDetalisGuideBtn;

    @FXML
    private Button NewOrderBtn;

    @FXML
    private Button myOrdersBtn;

    @FXML
    void LogOutButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideLogin.fxml", "GoNature Enter");
    }

    @FXML
    void NewOrderButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideNewOrder.fxml", "GoNature Enter");
    }

    @FXML
    void cardTypeButton(ActionEvent event) {

    }

    @FXML
    void completeTourOrderButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideOrderSuccssed.fxml", "GoNature Enter");
    }

    @FXML
    void mmButton(ActionEvent event) {

    }

    @FXML
    void myOrdersButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMyOrders.fxml", "GoNature Enter");
    }

    @FXML
    void updateDetalisGuideButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideChangeDetails.fxml", "GoNature Enter");
    }

    @FXML
    void yearButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert completeTourOrderBtn != null : "fx:id=\"completeTourOrderBtn\" was not injected: check your FXML file 'TourGuidePayment.fxml'.";
        assert cardTypeBtn != null : "fx:id=\"cardTypeBtn\" was not injected: check your FXML file 'TourGuidePayment.fxml'.";
        assert yearBtn != null : "fx:id=\"yearBtn\" was not injected: check your FXML file 'TourGuidePayment.fxml'.";
        assert mmBtn != null : "fx:id=\"mmBtn\" was not injected: check your FXML file 'TourGuidePayment.fxml'.";
        assert LogOutBtn != null : "fx:id=\"LogOutBtn\" was not injected: check your FXML file 'TourGuidePayment.fxml'.";
        assert updateDetalisGuideBtn != null : "fx:id=\"updateDetalisGuideBtn\" was not injected: check your FXML file 'TourGuidePayment.fxml'.";
        assert NewOrderBtn != null : "fx:id=\"NewOrderBtn\" was not injected: check your FXML file 'TourGuidePayment.fxml'.";
        assert myOrdersBtn != null : "fx:id=\"myOrdersBtn\" was not injected: check your FXML file 'TourGuidePayment.fxml'.";

    }
}
