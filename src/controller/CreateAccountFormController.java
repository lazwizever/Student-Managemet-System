package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Teacher;
import model.UserLevelMaster;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CreateAccountFormController {
    public AnchorPane createAccountContext;
    public PasswordField txtPassword;
    public TextField txtUserName;
    public TextField txtTeacherName;
    public ComboBox<String> cmbUserLevels;
    public Label lblWaringMessageName;
    public Label lblWaringMessageUserLevel;
    public Label lblWaringMessageUserName;
    public Label lblWaringMessagePassword;
    public Label lblWaringMessageConfirmPassword;
    public TextField txtConfirmPassword;
    public JFXButton btnCreateAccount;

    LinkedHashMap<TextField, Pattern> createMap = new LinkedHashMap<>();

    Pattern enterName = Pattern.compile("^([A-Z][a-z]{2,})");
    Pattern password = Pattern.compile("^([a-z0-9\\@\\&\\*]{4,})");


    public void initialize(){
        storeValidation();
        ObservableList<String> userLevels = FXCollections.observableArrayList("Admin","Teacher In Charge","Class Teacher");
        cmbUserLevels.setItems(userLevels);

        for (TextField jfxTextField : createMap.keySet()) {
            jfxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (validateAccountFinal()){
                    btnCreateAccount.setDisable(false);
                }else {
                    btnCreateAccount.setDisable(true);
                }
            });
        }

        cmbUserLevels.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateAccountFinal()){
                btnCreateAccount.setDisable(false);
            }else {
                btnCreateAccount.setDisable(true);
            }
        });
    }

    public void storeValidation(){
        createMap.put(txtTeacherName,enterName);
        createMap.put(txtUserName,enterName);
        createMap.put(txtPassword,password);
        createMap.put(txtConfirmPassword,password);
    }

    public boolean validateAccountDetails(){
        for (TextField textField : createMap.keySet()) {
            Pattern pattern = createMap.get(textField);
            if (!pattern.matcher(textField.getText()).matches()){
                textField.setStyle("-fx-border-color: red");
                return false;
            }else {
                textField.setStyle("-fx-border-color: green");
            }
        }
        return true;
    }

    public boolean validateAccountFinal(){
        boolean validateAccount = validateAccountDetails();
        boolean userLevelSelected = cmbUserLevels.getValue()!=null;
        if (validateAccount && userLevelSelected){
            return true;
        }
        return false;
    }

    public void backToAdminFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) createAccountContext.getScene().getWindow();
//        window.setX(450);
//        window.setY(350);
        window.setScene(new Scene(load));
    }

    public void createAccountOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            Teacher teacher = new Teacher(
                    new TeacherController().getTeacherId(),
                    txtTeacherName.getText(),
                    new UserLevelMasterController().getUserLevelType(cmbUserLevels.getValue()),
                    txtUserName.getText(),
                    txtPassword.getText()
            );
            if (new TeacherController().addNewTeacher(teacher)) {
                new Alert(Alert.AlertType.INFORMATION, "Create account has been successfull.").show();
                txtUserName.clear();
                txtConfirmPassword.clear();
                txtTeacherName.clear();
                txtPassword.clear();
                cmbUserLevels.getSelectionModel().clearSelection();
            } else {
                new Alert(Alert.AlertType.WARNING, "Not successfully created").show();
            }
    }
}
