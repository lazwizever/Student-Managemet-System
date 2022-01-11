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

public class SearchListFormTeacherInChargeController {

    public TextField txtSearchBar;
    public TableView<Student> tblSearchStudent;
    public TableColumn colRegistrationNo;
    public TableColumn colStName;
    public JFXButton btnSearch;
    public JFXComboBox<String> cmbSearchType;
    public AnchorPane searchListContext;

    public void initialize(){
        try {
            setStudentDetailsToTbl();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colRegistrationNo.setCellValueFactory(new PropertyValueFactory<>("stRegisterNo"));
        colStName.setCellValueFactory(new PropertyValueFactory<>("stName"));

        ObservableList<String> searchType = FXCollections.observableArrayList("Student Register No","Student Name");
        cmbSearchType.setItems(searchType);

        //------------------Select Student In Table--------------------------------//
        tblSearchStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SearchStudentForm.fxml"));
                try {
                    Parent parent = loader.load();
                    SearchStudentFormController controller = loader.getController();
                    controller.setTxtDetails(newValue);
                    Stage window = (Stage) searchListContext.getScene().getWindow();
                    window.setScene(new Scene(parent));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        btnSearch.setDisable(true);
        txtSearchBar.setDisable(true);
    }

    private void setStudentDetailsToTbl() throws SQLException, ClassNotFoundException {
        tblSearchStudent.setItems(new StudentController().getStudentDetails());
    }


    public void searchStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cmbSearchType.getValue().equals("Student Name")){
            ObservableList<Student> studentName = new StudentController().getStudentName(txtSearchBar.getText());
            tblSearchStudent.setItems(studentName);
        }else {
            ObservableList<Student> studentId = new StudentController().getStudentId(txtSearchBar.getText());
            tblSearchStudent.setItems(studentId);
        }
    }

    public void visibleSearchBtn(ActionEvent actionEvent) {
        btnSearch.setDisable(false);
        txtSearchBar.setDisable(false);
    }

    public void backToSearchStudent(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SearchStudentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) searchListContext.getScene().getWindow();
        window.setX(400);
        window.setY(250);
        window.setScene(new Scene(load));
    }
}
