package client.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class OrderManagementController extends AbstractScenes{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField orderNumberTxt;

    @FXML
    private TextField amountOfVisitorsTxt;
    
    @FXML
    private Text helloTxt;
    
    @FXML
    private Button changeBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private TextField timeTxt;

    @FXML
    private TextField dateTxt;

    @FXML
    private TextField parkTxt;

    @FXML
    private ImageView existingOrderBackBtn;

    @FXML
    private Button orderManagementBtn;

    @FXML
    private Button changeOrderDetailsBtn;

    @FXML
    private Button printDetailsbtn;

    @FXML
    private Button cancelOrderBtn;

    @FXML
    void CancelOrder(ActionEvent event) {
    	switchScenes("/client/boundaries/Cancel Confirmation.fxml", "Cancel Confirmation");
    }

    @FXML
    void ChangeOrderDetails(ActionEvent event) {
    	switchScenes("/client/boundaries/Change Order Details.fxml", "Change Order Details");
    }

    @FXML
    void PrintDetails(ActionEvent event) {
    	//fix
    }

    @FXML
    void Exit(ActionEvent event) {
    	switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    }
    
    @FXML
    void initialize() {
    }
}
