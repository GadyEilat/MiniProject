package client.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ServiceRepresentativeController extends AbstractScenes {

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField telField;

	@FXML
	private TextField idNumberField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField credit4Digit1;

	@FXML
	private ComboBox<?> choiceNumberOfMembers;

	@FXML
	private TextField credit4Digit3;

	@FXML
	private TextField credit4Digit2;

	@FXML
	private TextField credit4Digit4;

	@FXML
	private Button btnSave;

	@FXML
	private RadioButton btnRadioGroup;

	@FXML
	private ToggleGroup RadioButton;

	@FXML
	private RadioButton btnRadioFamily;

	@FXML
	private Button btnLogout;

	@FXML
	void logout(ActionEvent event) {
		// exit Logout
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

	@FXML
	void saveSubAndGetSubNumber(ActionEvent event) {
		// Saving To the correct DB and generate SUB number

		try {
			showNumOfSubPopOut();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showNumOfSubPopOut() throws IOException {
		Stage helpWindow = new Stage();
		FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/client/boundaries/subNumPopup.fxml"));
		Parent current = fxmlLoad.load();
		helpWindow.initModality(Modality.APPLICATION_MODAL);
		helpWindow.setTitle("Subscription Number");
		Scene scene = new Scene(current);
		helpWindow.setMinHeight(400);
		helpWindow.setMinWidth(600);
		helpWindow.setMaxHeight(400);
		helpWindow.setMaxWidth(600);
		helpWindow.setScene(scene);
		helpWindow.showAndWait();
	}

}
