package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Teacher;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginFormController {
    public AnchorPane loginContext;
    public PasswordField txtPassword;
    public TextField txtUserName;
    public Label lblDate;
    public Label lblTime;
    public Label lblInvalidPassword;
    public TextField warningMessageUserName;
    public JFXButton closeBtn;
    public CheckBox checkBoxShowPassword;
    public Label lblShowPassword;
    public JFXTextField txtShowPasswordField;
    public TextField txtShowPassword;


    public void initialize() {

        {
            Thread clock = new Thread() {
                public void run() {
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

        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                txtShowPassword.setText(txtPassword.getText());
            }
        });

        txtShowPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                txtPassword.setText(txtShowPassword.getText());
            }
        });

        checkBoxShowPassword.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue==true){
                txtShowPassword.setVisible(true);
                txtPassword.setVisible(false);
            }else {
                txtPassword.setVisible(true);
                txtShowPassword.setVisible(false);
            }
        });
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        int count = 0;
        for (Teacher temp : new TeacherController().getTeacherDetails()
        ) {
            if (txtUserName.getText().equals(temp.getUserName())) {
                if (txtPassword.getText().equals(temp.getPassWord())) {
                    if (temp.getUserLevelId().equals("1")) {
                        URL resource = getClass().getResource("../view/AdminForm.fxml");
                        Parent load = FXMLLoader.load(resource);
                        Stage window = (Stage) loginContext.getScene().getWindow();
                        window.setX(550);
                        window.setY(250);
                        window.setScene(new Scene(load));

                    } else if (temp.getUserLevelId().equals("2")) {
                        URL resource = getClass().getResource("../view/TeacherInChargeForm.fxml");
                        Parent load = FXMLLoader.load(resource);
                        Stage window = (Stage) loginContext.getScene().getWindow();
                        window.setX(550);
                        window.setY(250);
                        window.setScene(new Scene(load));
                    } else if (temp.getUserLevelId().equals("3")) {
                        URL resource = getClass().getResource("../view/ClassTeacherForm.fxml");
                        Parent load = FXMLLoader.load(resource);
                        Stage window = (Stage) loginContext.getScene().getWindow();
                        window.setX(550);
                        window.setY(250);
                        window.setScene(new Scene(load));
                    }
                }else {
                    lblInvalidPassword.setVisible(true);
                }
                break;
            }
            count++;
        }
        if (count==new TeacherController().getTeacherDetails().size()){
            lblInvalidPassword.setVisible(true);
            lblInvalidPassword.setText("Invalid UserName");
        }



    }

    public void closeBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();

    }

}

