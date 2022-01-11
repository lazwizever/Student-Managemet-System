package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Student;

import java.io.IOException;
import java.sql.SQLException;

public class PaymentFormSearchListFormController {
    public JFXComboBox cmbSearchTypes;
    public TextField txtSearchBar;
    public TableView<Student> tblStudentDetails;
    public TableColumn colRegistrationNo;
    public TableColumn colStudentName;
    public JFXButton btnSearch;
    public AnchorPane searchListFormContext;

    public  void initialize(){
        try {
            setStudentDetailToTbl();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        colRegistrationNo.setCellValueFactory(new PropertyValueFactory("stRegisterNo"));
        colStudentName.setCellValueFactory(new PropertyValueFactory("stName"));

        ObservableList<String> searchType = FXCollections.observableArrayList("Student Register No","Student Name");
        cmbSearchTypes.setItems(searchType);


        tblStudentDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PaymentReportsForm.fxml"));
                try {
                    Parent parent = loader.load();
                    PaymentReportsFormController controller = loader.getController();
                    controller.setDetails(newValue);
                    Stage window = (Stage) searchListFormContext.getScene().getWindow();
                    window.setScene(new Scene(parent));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        txtSearchBar.setDisable(true);
        btnSearch.setDisable(true);
    }

    public void setStudentDetailToTbl() throws SQLException, ClassNotFoundException {
        tblStudentDetails.setItems(new StudentController().getStudentDetails());
    }

    public void searchStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cmbSearchTypes.getValue().equals("Student Name")){
            ObservableList<Student> studentName = new StudentController().getStudentName(txtSearchBar.getText());
            tblStudentDetails.setItems(studentName);
        }else {
            ObservableList<Student> studentId = new StudentController().getStudentId(txtSearchBar.getText());
            tblStudentDetails.setItems(studentId);
        }
    }

    public void visibleSearchBarOnAction(ActionEvent actionEvent) {
        txtSearchBar.setDisable(false);
        btnSearch.setDisable(false);
    }
}
