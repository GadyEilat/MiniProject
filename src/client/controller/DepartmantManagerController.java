package client.controller;

import com.gluonhq.charm.glisten.control.ProgressBar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class DepartmantManagerController extends AbstractScenes{
	
	/////////////////////// Status Scene
    @FXML
    private Text currentNumOfVisitors;

    @FXML
    private Text currentMaxOfVisitors;

    @FXML
    private ProgressBar progressBar;

    ///////////////////////// Approve Scene
    @FXML
    private TextField maxVisitorsField;

    @FXML
    private Button btnCancelOfMaxVisitors;

    @FXML
    private Button btnApproveOfMaxVisitors;

    @FXML
    private TextField discountFiled;

    @FXML
    private Button btnApproveOfDiscount;

    @FXML
    private Button btnCancelOfDiscount;
    
    //////////////////////// Reports Scene
    @FXML
    private Button btnVisitReport;

    @FXML
    private Button btnCancellationReport;
    
    ///////////////////// For All Scenes
    @FXML
    private Button btnLogout;

    @FXML
    private Button btnStatus;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnApproval;

    @FXML
    void logout(ActionEvent event) {
    	//exit Logout
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
    }

    @FXML
    void showApproval(ActionEvent event) {
		switchScenes("/client/boundaries/approveManagerChanges.fxml", "Departmant Manager");
    }

    @FXML
    void showReports(ActionEvent event) {
		switchScenes("/client/boundaries/reportsDM.fxml", "Departmant Manager");
    }

    @FXML
    void showStatus(ActionEvent event) {
		switchScenes("/client/boundaries/mainDepartmantManager.fxml", "Departmant Manager");
    }
    
	/////////////////////// Status Scene Function :

	public void updateNumberOfVisitor() {
		
	}
	
	///////////////////// Approve Scene Function :
	
    @FXML
    void approveChangeOfDiscount(ActionEvent event) {

    }

    @FXML
    void approveChangeOfMaxVisitors(ActionEvent event) {

    }

    @FXML
    void cancelChangesOfDiscount(ActionEvent event) {

    }

    @FXML
    void cancelTheChangesOfMaxVisitors(ActionEvent event) {

    }
    
    //////////////////////// Reports Scene Function

    @FXML
    void showCacnellationReport(ActionEvent event) {

    }
    
    @FXML
    void showVisitReport(ActionEvent event) {

    }

}
