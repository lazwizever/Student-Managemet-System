package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
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
import model.ParentDetails;
import model.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class TeacherInChargeStudentRegistrationFormController {
    public AnchorPane registerStudentContext;
    public Label lblTime;
    public Label lblDate;
    public JFXTextField txtParentName;
    public ComboBox txtParentTitle;
    public RadioButton rdBtnMale;
    public RadioButton rdBtnFemale;
    public JFXTextField txtJob;
    public JFXTextField txtNIC;
    public JFXTextField txtParentAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtParentContactNo;
    public JFXButton btnRegisterStudent;
    public Label parentIdLbl;
    public Label lblParentId;
    public Label lblRegistrationNo;
    public Label registrationNoLbl;
    public RadioButton rdBtnAddNewStudent;
    public RadioButton rdBtnExistingStudent;
    public JFXTextField txtContactNo;
    public JFXTextField txtAddress;
    public ComboBox<String> cmbGrade;
    public RadioButton btnStMale;
    public RadioButton btnStFemale;
    public ComboBox cmbDate;
    public ComboBox cmbMonth;
    public ComboBox cmbYear;
    public JFXTextField txtStudentName;
    public JFXTextField txtRegistrationNo;
    public JFXButton btnSearch;
    public ComboBox<String> cmbParentTitle;
    public Label lblWarningMessageStudentName;
    public Label lblWarningMessageStudentDOB;
    public Label lblWarningMessageStudentGender;
    public Label lblWarningMessageStudentGrade;
    public Label lblWarningMessageStudentContactNo;
    public Label lblWarningMessageStudentAddress;
    public Label lblWarningMessageGuardianName;
    public Label lblWarningMessageGuardianTitle;
    public Label lblWarningMessageGuardianGender;
    public Label lblWarningMessageGuardianNIC;
    public Label lblWarningMessageGuardianContactNo;
    public Label lblWarningMessageGuardianJob;
    public Label lblWarningMessageEmail;
    public JFXButton btnStudentRegistration;

    LinkedHashMap<JFXTextField, Pattern> studentMap = new LinkedHashMap<>();
    LinkedHashMap<JFXTextField, Pattern> guardianMap = new LinkedHashMap<>();

    Pattern name = Pattern.compile("^([A-Z][a-z]{2,})");
    Pattern contactNo = Pattern.compile("^([0-9]{10})");
    Pattern address = Pattern.compile("^([A-z,0-9]{4,})");
    Pattern nic = Pattern.compile("^([0-9]{9}[v|V]|[0-9]{12})$");
    Pattern job = Pattern.compile("^([A-Z][a-z]{4,})");
    Pattern email = Pattern.compile("^([a-z0-9]{4,}\\@gmail.com)");

    public void initialize(){

        storeValidations();
        {
            Thread clock=new Thread(){
                public void run(){
                    try {
                        while (true) {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
        ObservableList<Integer> days = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
        cmbDate.setItems(days);

        ObservableList<Integer> months = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
        cmbMonth.setItems(months);

        ObservableList<Integer> years = FXCollections.observableArrayList(2014,2015,2016,2017);
        cmbYear.setItems(years);

        ObservableList<String> grade = FXCollections.observableArrayList("Lower Kindergarten","Upper Kindergarten");
        cmbGrade.setItems(grade);

        ObservableList<String> parentTitle = FXCollections.observableArrayList("Mr","Mrs");
        cmbParentTitle.setItems(parentTitle);

        rdBtnAddNewStudent.setDisable(true);
        rdBtnExistingStudent.setDisable(true);
        btnSearch.setDisable(true);
        txtRegistrationNo.setDisable(true);

        try {
            lblRegistrationNo.setText(new StudentController().setRegistrationNo());
            lblParentId.setText(new ParentController().setParentId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (JFXTextField jfxTextField : studentMap.keySet()) {
            jfxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (validateStudentFinal() && validateGuardianFinal()){
                    btnStudentRegistration.setDisable(false);
                }else {
                    btnStudentRegistration.setDisable(true);
                }
            });
        }

        for (JFXTextField jfxTextField : guardianMap.keySet()) {
            jfxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (validateStudentFinal() && validateGuardianFinal()){
                    btnStudentRegistration.setDisable(false);
                }else {
                    btnStudentRegistration.setDisable(true);
                }
            });
        }

        cmbDate.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnStudentRegistration.setDisable(false);
            }else {
                btnStudentRegistration.setDisable(true);
            }
        });

        cmbMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnStudentRegistration.setDisable(false);
            }else {
                btnStudentRegistration.setDisable(true);
            }
        });

        cmbYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnStudentRegistration.setDisable(false);
            }else {
                btnStudentRegistration.setDisable(true);
            }
        });

        cmbGrade.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnStudentRegistration.setDisable(false);
            }else {
                btnStudentRegistration.setDisable(true);
            }
        });

        rdBtnFemale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnStudentRegistration.setDisable(false);
            }else {
                btnStudentRegistration.setDisable(true);
            }
        });

        rdBtnMale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnStudentRegistration.setDisable(false);
            }else {
                btnStudentRegistration.setDisable(true);
            }
        });

        cmbParentTitle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnStudentRegistration.setDisable(false);
            }else {
                btnStudentRegistration.setDisable(true);
            }
        });

        btnStMale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnStudentRegistration.setDisable(false);
            }else {
                btnStudentRegistration.setDisable(true);
            }
        });

        btnStFemale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnStudentRegistration.setDisable(false);
            }else {
                btnStudentRegistration.setDisable(true);
            }
        });


    }

    private void storeValidations() {
        studentMap.put(txtStudentName,name);
        studentMap.put(txtAddress,address);
        studentMap.put(txtContactNo,contactNo);

        guardianMap.put(txtParentName,name);
        guardianMap.put(txtParentAddress,address);
        guardianMap.put(txtNIC,nic);
        guardianMap.put(txtJob,job);
        guardianMap.put(txtParentContactNo,contactNo);
        guardianMap.put(txtEmail,email);

    }

    public boolean validateStudentDetails(){
        for (JFXTextField textField : studentMap.keySet()) {
            Pattern pattern = studentMap.get(textField);
            if (!pattern.matcher(textField.getText()).matches()){
                textField.setStyle("-fx-border-color: red");
                return false;
            }else {
                textField.setStyle("-fx-border-color: green");
            }
        }
        return true;
    }

    public boolean validateStudentFinal(){
        boolean validatestudent = validateStudentDetails();
        boolean gradeSelected = cmbGrade.getValue()!=null;
        boolean birthDaySelected = cmbDate.getValue()!=null && cmbMonth.getValue()!=null && cmbYear.getValue()!=null ;
        boolean studentGender = btnStMale.isSelected() || btnStFemale.isSelected();

        if (validatestudent && gradeSelected && birthDaySelected && studentGender){
            return true;
        }
        return false;
    }

    public boolean validateGuardian(){
        for (JFXTextField jfxTextField : guardianMap.keySet()) {
            Pattern pattern = guardianMap.get(jfxTextField);
            if (!pattern.matcher(jfxTextField.getText()).matches()){
                jfxTextField.setStyle("-fx-border-color: red");
                return false;
            }else {
                jfxTextField.setStyle("-fx-border-color: green");
            }
        }
        return true;
    }

    public boolean validateGuardianFinal(){
        boolean validateGuardianDetails = validateGuardian();
        boolean titleSelected = cmbParentTitle.getValue()!=null;
        boolean parentGender = (rdBtnMale.isSelected() || rdBtnFemale.isSelected()) ? true : false;

        if (validateGuardianDetails && titleSelected && parentGender){
            return true;
        }
        return false;
    }

    public void backToTeacherInChargeForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/TeacherInChargeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) registerStudentContext.getScene().getWindow();
        window.setX(550);
        window.setY(250);
        window.setScene(new Scene(load));
    }

    public void registerStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String parentGender = (rdBtnMale.isSelected()) ? "Male" : (rdBtnFemale.isSelected()) ? "Female" : null;
            ParentDetails parent = new ParentDetails(
                    lblParentId.getText(),
                    lblRegistrationNo.getText(),
                    txtParentName.getText(),
                    cmbParentTitle.getValue(),
                    parentGender,
                    txtJob.getText(),
                    txtNIC.getText(),
                    txtAddress.getText(),
                    txtEmail.getText(),
                    txtContactNo.getText()
            );

            String dob = cmbDate.getValue() + "/" + cmbMonth.getValue() + "/" + cmbYear.getValue();
            String gender = (btnStMale.isSelected()) ? "Male" : (btnStFemale.isSelected()) ? "Female" : null;

            Student student = new Student(
                    lblRegistrationNo.getText(),
                    txtStudentName.getText(),
                    dob,
                    lblDate.getText(),
                    gender,
                    cmbGrade.getValue(),
                    txtAddress.getText(),
                    txtContactNo.getText(),
                    parent
            );

            if (new StudentController().registerNewStudent(student)) {
                new Alert(Alert.AlertType.INFORMATION, "Student has been Successfully registered.").show();
                lblRegistrationNo.setText(new StudentController().setRegistrationNo());
                lblParentId.setText(new ParentController().setParentId());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
            lblRegistrationNo.setText(new StudentController().setRegistrationNo());
            lblParentId.setText(new ParentController().setParentId());

    }

    public void clearTxtFeildOnAction(ActionEvent actionEvent) {
        btnStudentRegistration.setDisable(true);
        txtStudentName.clear();
        txtRegistrationNo.clear();
        txtContactNo.clear();
        txtAddress.clear();
        txtParentName.clear();
        txtParentAddress.clear();
        txtParentContactNo.clear();
        txtNIC.clear();
        txtEmail.clear();
        txtJob.clear();
        cmbDate.getSelectionModel().clearSelection();
        cmbMonth.getSelectionModel().clearSelection();
        cmbYear.getSelectionModel().clearSelection();
        cmbGrade.getSelectionModel().clearSelection();
        cmbParentTitle.getSelectionModel().clearSelection();
        rdBtnMale.setSelected(false);
        rdBtnFemale.setSelected(false);
        btnStMale.setSelected(false);
        btnStFemale.setSelected(false);
    }

    public void rdBtnStMaleOnAction(ActionEvent actionEvent) {
        if (btnStMale.isSelected()){
            btnStFemale.setSelected(false);
        }
    }

    public void rdBtnStFemaleOnAction(ActionEvent actionEvent) {
        if (btnStFemale.isSelected()){
            btnStMale.setSelected(false);
        }
    }

    public void rdBtnMaleOnAction(ActionEvent actionEvent) {
        if (rdBtnMale.isSelected()){
            rdBtnFemale.setSelected(false);
        }
    }

    public void rdBtnFemaleOnAction(ActionEvent actionEvent) {
        if (rdBtnFemale.isSelected()){
            rdBtnMale.setSelected(false);
        }
    }
}
