package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Student;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchStudentFormController {
    public AnchorPane searchStudentContext;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtStName;
    public JFXTextField txtParentName;
    public JFXTextField txtContactNo;
    public JFXTextField txtAddress;
    public RadioButton rdMale;
    public RadioButton rdFemale;
    public JFXTextField txtParentJob;
    public JFXTextField txtNIC;
    public JFXTextField txtParentAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtParentContactNo;
    public RadioButton rdBtnMale;
    public RadioButton rdBtnFemale;
    public JFXTextField txtStudentName;
    public JFXTextField txtRegistrationNo;
    public JFXTextField txtDateOfBirth;
    public JFXTextField txtGrade;

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
    }

    public void backToTeacherInChargeFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/TeacherInChargeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) searchStudentContext.getScene().getWindow();
        window.setX(550);
        window.setY(250);
        window.setScene(new Scene(load));
    }

    public void moveToSearchFormList(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SearchListFormTeacherInCharge.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)searchStudentContext.getScene().getWindow();
        window.setX(500);
        window.setY(150);
        window.setScene(new Scene(load));
    }

    public void setTxtDetails(Student newValue) {
        txtRegistrationNo.setText(newValue.getStRegisterNo());
        txtStudentName.setText(newValue.getStName());
        txtContactNo.setText(newValue.getStContactNo());
        txtAddress.setText(newValue.getStAddress());
        txtGrade.setText(newValue.getStGrade());
        if (newValue.getStGender().equals("Male")){
            rdBtnMale.setSelected(true);
        }else {
            rdBtnFemale.setSelected(true);
        }
        txtDateOfBirth.setText(newValue.getStDob());

        txtParentName.setText(newValue.getParentDetails().getPrtName());
        txtNIC.setText(newValue.getParentDetails().getPrtNIC());
        txtParentAddress.setText(newValue.getParentDetails().getPrtAddress());
        txtParentContactNo.setText(newValue.getParentDetails().getPrtContactNo());
        txtEmail.setText(newValue.getParentDetails().getPrtEmail());

        if (newValue.getParentDetails().getPrtGender().equals("Male")){
            rdMale.setSelected(true);
        }else {
            rdFemale.setSelected(true);
        }
        txtParentJob.setText(newValue.getParentDetails().getPrtJob());
    }

}
