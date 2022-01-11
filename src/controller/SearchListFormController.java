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
import java.net.URL;
import java.sql.SQLException;

public class SearchListFormController {
    public JFXComboBox cmbSearchType;
    public TextField txtSearchBar;
    public TableView<Student> tblStudentDetails;
    public TableColumn colRegistrationNo;
    public TableColumn colStudentName;
    public JFXButton btnSearch;
    public AnchorPane searchListContext;

    public void initialize(){
        try {
            setStudentDetailsToTbl();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colRegistrationNo.setCellValueFactory(new PropertyValueFactory("stRegisterNo"));
        colStudentName.setCellValueFactory(new PropertyValueFactory("stName"));

        ObservableList<String> searchType = FXCollections.observableArrayList("Student Register No","Student Name");
        cmbSearchType.setItems(searchType);

        //------------------Select Student In Table--------------------------------//
        tblStudentDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                FXMLLoader lorder = new FXMLLoader(getClass().getResource("../view/StudentRegistrationForm.fxml"));
                try {
                    Parent parent = lorder.load();
                    StudentRegistrationFormController controller = lorder.getController();

                    controller.setTxtDetails(newValue);
                    controller.rdBtnExistingStudent.setSelected(true);
                    controller.rdBtnAddNewStudent.setSelected(false);
                    controller.txtRegistrationNo.setDisable(false);
                    controller.btnSearch.setDisable(false);
                    controller.btnRegisterStudent.setText("Update");
                    controller.btnDelete.setDisable(false);

                    Stage window = (Stage) searchListContext.getScene().getWindow();
                    window.setScene(new Scene(parent));
                } catch (IOException e) {
                   e.printStackTrace();
               }
            }
            //Stage = searchListContext.
        });
        txtSearchBar.setDisable(true);
        btnSearch.setDisable(true);
    }

    public void setStudentDetailsToTbl() throws SQLException, ClassNotFoundException {
        tblStudentDetails.setItems(new StudentController().getStudentDetails());
    }

    public void searchStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cmbSearchType.getValue().equals("Student Name")){
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

    public void backToStudentRegistrationForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/StudentRegistrationForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) searchListContext.getScene().getWindow();
        window.setX(400);
        window.setY(150);
        window.setScene(new Scene(load));
    }
}
