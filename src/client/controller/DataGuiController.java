package client.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DataGuiController extends AbstractScenes{
	Visitor visitor;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTel;

    @FXML
    private Button BtnBack;

    @FXML
    private Button BtnSave;

    @FXML
    private Button dataTableExit;


    @FXML
    void DataExit(ActionEvent event) {
		System.exit(0);
    }

	public void loadVisitor(Visitor visitorInt) {
		this.visitor = visitorInt;
		this.txtID.setText(visitor.getId());
		this.txtName.setText(visitor.getFname());
		this.txtLastName.setText(visitor.getLname());
		this.txtEmail.setText(visitor.getEmail());
		this.txtTel.setText(visitor.getTeln());
<<<<<<< HEAD

=======
>>>>>>> branch 'main' of https://github.com/liranami/MiniProject
	}

	@FXML
	void ButtonBack(ActionEvent event) throws Exception {
		ChatClient.visitor = new Visitor(null, null, null, null, null);
		switchScenes("/client/boundaries/ClientGUI.fxml", "GoNature Enter");
	}
	@FXML
	void ButtonSaveEmail(ActionEvent event) throws Exception {
		String updatedEmail = (txtEmail.getText());
		visitor.setEmail(updatedEmail);
		ClientUI.chat.accept(visitor);
		System.out.println("Email Updated Successfully");
	}
<<<<<<< HEAD


=======
>>>>>>> branch 'main' of https://github.com/liranami/MiniProject
}
