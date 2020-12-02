package client.controller;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import client.logic.Visitor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DataGuiController implements Serializable, Initializable{
	String firstPerson[]= {"avi", "noam ", "123", "bla@gmail.com", "054"};
	String secondPerson[]= {"Elad", "Kobi ", "123", "eladkobi@gmail.com", "054"};

	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
	@FXML
	private Button BtnSave;

	@FXML
	public Button dataTableExit;
	
	@FXML
	public Button BtnBack;
	
	

	
    
	@FXML
    private TableView<Visitor> TblData;
    
    @FXML
    private TableColumn<row, String> ColID;

    @FXML
    private TableColumn<row, String> ColFname;

    @FXML
    private TableColumn<row, String> ColLname;

    @FXML
    private TableColumn<row, String> ColEmail;

    @FXML
    private TableColumn<row, String> ColTel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		ColFname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
		ColLname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
		ColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		ColTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
		//addItems();
	}
	
	/*private void addItems() {
		TblData.getItems().add(new row ("avi", "noam ", "123", "bla@gmail.com", "054"));
		TblData.getItems().add(new row ("Elad", "Kobi", "123", "eladkobi@gmail.com", "054"));
		TblData.getItems().add(new row ("Elad", "Kobi", "123", "eladkobi@gmail.com", "054"));
		
	}*/
	
	

	public class row{
		public String getFirstName() {
			return FirstName;
		}
		public String getLastName() {
			return LastName;
		}
		public String getID() {
			return ID;
		}
		public String getEmail() {
			return email;
		}
		public String getTel() {
			return tel;
		}
		private String FirstName;
		private String LastName;
		private String ID;
		private String email;
		private String tel;
		public row(String fName, String lName, String iD, String email, String phone) {
			super();
			this.FirstName = fName;
			this.LastName = lName;
			this.ID = iD;
			this.email = email;
			this.tel =phone;
		}
		
		
	}
	
	@FXML
	void ButtonBack(ActionEvent event) throws Exception {
		TblData.getItems().clear();
		((Node) event.getSource()).getScene().getWindow().hide();
		System.out.println("Go back to Client GUI");
		Stage stage = new Stage();
		ClientUI clientUI = new ClientUI();
		clientUI.start(stage);
	}

	public void SetRows(ArrayList<Visitor> list) {
		ObservableList<Visitor> dataOfTable = FXCollections.observableArrayList(list);
		TblData.setItems(dataOfTable);
		
	}
	@FXML
	public void DataTableExit(ActionEvent event) throws Exception 
    {
		System.exit(0);
	}

	
}
