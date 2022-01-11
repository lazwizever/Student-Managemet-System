package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TeacherInChargeFormController {
    public AnchorPane teacherInChargeFormContext;
    public Label lblDate;
    public Label lblTime;
    public JFXButton closeBtn;

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

    public void moveToViewStudentMarkFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewStudentMarksForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) teacherInChargeFormContext.getScene().getWindow();
        window.setX(500);
        window.setY(150);
        window.setScene(new Scene(load));
    }

    public void moveToStudentRegistrationFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/TeacherInChargeStudentRegistrationForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) teacherInChargeFormContext.getScene().getWindow();
        window.setX(350);
        window.setY(100);
        window.setScene(new Scene(load));
    }

    public void backToLoginOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) teacherInChargeFormContext.getScene().getWindow();
        window.setX(550);
        window.setY(250);
        window.setScene(new Scene(load));
    }

    public void searchStudentOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SearchStudentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) teacherInChargeFormContext.getScene().getWindow();
        window.setX(350);
        window.setY(200);
        window.setScene(new Scene(load));
    }

    public void closeBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void moveToAddSubjectFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddStudentSubjectForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) teacherInChargeFormContext.getScene().getWindow();
        window.setX(350);
        window.setY(200);
        window.setScene(new Scene(load));
    }
}
