package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FamilySubscription extends AbstractScenes{

	/////////////////// History Of family scene

	@FXML
	private TableView<?> tableOfHistory;

	//////////////////// Edit info scene

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField telEditField;

	@FXML
	private TextField idNumberField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField credit4Digit1;

	@FXML
	private ComboBox<?> familyNumberField;

	@FXML
	private TextField credit4Digit3;

	@FXML
	private TextField credit4Digit2;

	@FXML
	private TextField credit4Digit4;

	@FXML
	private Button btnSave;

	/////////////////// New order scene

	//////////////// For All scenes

	@FXML
	private Text familyName;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnEditInfo;

	@FXML
	private Button btnHistoryOfVisit;

	@FXML
	private Button btnNewOrder;

	@FXML
	void logout(ActionEvent event) {
		switchScenes("/client/boundaries/FamilySubEnter.fxml", "Family Subscription");
	}

	@FXML
	void showEditInfo(ActionEvent event) {
		switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");

	}

	@FXML
	void showHistroryOfVisit(ActionEvent event) {
		switchScenes("/client/boundaries/HistoryOfFamilyVisits.fxml", "Family Subscription");

	}

	@FXML
	void showNewOrder(ActionEvent event) {
		//switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");

	}

	/////////////////// History Of family scene Functions

	@FXML
	void showTableOfHistory(ActionEvent event) {

	}

	//////////////////// Edit info scene

	@FXML
	void updateFamilyInfo(ActionEvent event) {

	}

	/////////////////// New order scene

}
