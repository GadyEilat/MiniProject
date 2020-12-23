package client.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ChangeOrderDetailsController extends AbstractScenes{

    @FXML
    private ResourceBundle resources;

    @FXML
    private Text msgFromController;
    
    @FXML
    private TextField amountOfVisitorsTxt;
    
    @FXML
    private Text helloTxt;
    
    @FXML
    private URL location;

    @FXML
    private TextField orderNumberTxt;

    @FXML
    private Button applyBtn;

    @FXML
    private Button btnLogout;
    
    @FXML
    private TextField timeTxt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<?> parkComboBox;

    @FXML
    private ImageView backBtn;

    @FXML
    private Button orderManagementBtn;

    @FXML
    private Button changeOrderDetailsBtn;

    @FXML
    private Button printDetailsBtn;

    @FXML
    private Button cancelOrderBtn;

    @FXML
    void Apply(ActionEvent event) {
    	//Save Changes to Order Details in DB --> Fix.
    	String time = timeTxt.getText();
		if (time.trim().isEmpty()) {
			msgFromController.setFill(Color.RED);
			msgFromController.setText("Please enter a time");
		}
		else
		{
			//actually check in DB whatever needed.
			msgFromController.setFill(Color.GREEN);
			msgFromController.setText("Updated Successfully");
		}
    }

    @FXML
    void CancelOrder(ActionEvent event) {
    	switchScenes("/client/boundaries/Cancel Confirmation.fxml", "Cancel Confirmation");
    }

    @FXML
    void OrderManagement(ActionEvent event) { //same as last button, but in different position
    	switchScenes("/client/boundaries/Order Management.fxml", "Order Management");
    }

    @FXML
    void PrintDetails(ActionEvent event) {
    	//fix -- Printing.
    }
    
    @FXML
    void Exit(ActionEvent event) {
    	switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    }

    @FXML
    void initialize() {
    }
}
