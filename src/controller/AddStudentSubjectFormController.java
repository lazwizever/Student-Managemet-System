package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Subject;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class AddStudentSubjectFormController {

    public Label lblDate;
    public Label lblTime;
    public JFXComboBox cmbSubjects;
    public Label lblSubjectId;
    public Label lblNoOfSubjects;
    public AnchorPane studentSubjectContext;
    public TableView<Subject> tblSubjectDetails;
    public TableColumn colSubid;
    public TableColumn colSubject;
    public JFXTextField txtSubjectName;
    public JFXButton btnAddSubject;
    public JFXTextField txtSubjectId;
    public Label lblTopicSubject;
    public JFXRadioButton rdBtnAddNewSubject;

    Pattern subject = Pattern.compile("^([A-Z][a-z]{2,})$");
    public void initialize(){
        {
            Thread clock=new Thread(){
                public void run(){
                    try {
                        while (true) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy ");
                            SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm a");
                            Date date = new Date();
                            String myString = formatter.format(date);
                            String myString1 = formatter1.format(date);
                            Platform.runLater(() -> {
                                lblDate.setText(myString);
                                lblTime.setText(myString1);
                            });
                            sleep(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            clock.start();

        }

        try {
            lblSubjectId.setText(new SubjectController().generateSubjectId());
            setSubjectDetailsToTbl();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colSubid.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        tblSubjectDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setDetailsToTxtFields(newValue);
                btnAddSubject.setText("Update Subject");
                txtSubjectId.setDisable(false);
                lblTopicSubject.setDisable(true);
                lblSubjectId.setDisable(true);
                rdBtnAddNewSubject.setSelected(false);
            }

        });

        txtSubjectName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                if (subject.matcher(newValue).matches()){
                    txtSubjectName.setStyle("-fx-border-color: green");
                    btnAddSubject.setDisable(false);
                }else {
                    txtSubjectName.setStyle("-fx-border-color: red");
                    btnAddSubject.setDisable(true);
                }
            }
        });
        rdBtnAddNewSubject.setSelected(true);
        rdBtnAddNewSubject.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                txtSubjectId.setDisable(true);
            }else {
                txtSubjectId.setDisable(false);
                lblTopicSubject.setDisable(true);
                lblSubjectId.setDisable(true);
            }
        });

    }

    private void setDetailsToTxtFields(Subject subject) {
        txtSubjectName.setText(subject.getSubject());
        txtSubjectId.setText(subject.getSubjectId());
    }

    private void setSubjectDetailsToTbl() throws SQLException, ClassNotFoundException {
        ObservableList<Subject> subjectDetails = new SubjectController().getSubjectDetails();
        tblSubjectDetails.setItems(subjectDetails);
    }

    public void AddSubjectOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnAddSubject.getText().equals("Update Subject")){

            Subject subject = new Subject(
                    txtSubjectId.getText(),
                    txtSubjectName.getText()
            );

           if (new SubjectController().updateSubject(subject)){
               new Alert(Alert.AlertType.INFORMATION,"Subject Has Been Added Successfully").show();
               lblTopicSubject.setDisable(false);
               lblSubjectId.setDisable(false);
               txtSubjectId.clear();
               txtSubjectName.clear();
               setSubjectDetailsToTbl();
               btnAddSubject.setText("Add Subject");
               btnAddSubject.setDisable(false);
               rdBtnAddNewSubject.setSelected(true);
           }else {
               new Alert(Alert.AlertType.WARNING,"Try Again").show();
           }

        }else {
            Subject subject = new Subject(
                    lblSubjectId.getText(),
                    txtSubjectName.getText()
            );

            if (new SubjectController().setNewSubject(subject)){
                new Alert(Alert.AlertType.INFORMATION,"Subject Has Been Added Successfully").show();
                lblTopicSubject.setDisable(false);
                lblSubjectId.setDisable(false);
                txtSubjectId.clear();
                txtSubjectName.clear();
                setSubjectDetailsToTbl();
                btnAddSubject.setDisable(false);
                rdBtnAddNewSubject.setSelected(true);
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
            }
        }

    }

    public void backToTeacherInChargeFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/TeacherInChargeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) studentSubjectContext.getScene().getWindow();
        window.setX(500);
        window.setY(300);
        window.setScene(new Scene(load));
    }
}
