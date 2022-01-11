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

public class SearchStudentListClassTeacherFormController {
    public JFXComboBox cmbSearchType;
    public TextField txtSearchBar;
    public TableView<Student> tblStudent;
    public TableColumn colRegistrationNo;
    public TableColumn colStName;
    public JFXButton btnSearch;
    public AnchorPane stuDentDetailsContext;

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
        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SearchStudentClassTeacherForm.fxml"));
                try {
                    Parent parent = loader.load();
                    SearchStudentClassTeacherFormController controller = loader.getController();
                    controller.setTxtDetails(newValue);
                    Stage window = (Stage) stuDentDetailsContext.getScene().getWindow();
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
        tblStudent.setItems(new StudentController().getStudentDetails());
    }

    public void searchStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cmbSearchType.getValue().equals("Student Name")){
            ObservableList<Student> studentName = new StudentController().getStudentName(txtSearchBar.getText());
            tblStudent.setItems(studentName);
        }else {
            ObservableList<Student> studentId = new StudentController().getStudentId(txtSearchBar.getText());
            tblStudent.setItems(studentId);
        }
    }

    public void visibleSearchBar(ActionEvent actionEvent) {
        btnSearch.setDisable(false);
        txtSearchBar.setDisable(false);
    }

    public void backToSearchStudentOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SearchStudentClassTeacherForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) stuDentDetailsContext.getScene().getWindow();
        window.setX(400);
        window.setY(200);
        window.setScene(new Scene(load));
    }
}
