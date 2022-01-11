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
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.ParentDetails;
import model.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class StudentRegistrationFormController {
    public AnchorPane studentRegistrationFormContext;
    public JFXButton btnHome;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnDelete;
    public JFXButton btnSearch;
    public RadioButton rdBtnAddNewStudent;
    public RadioButton rdBtnExistingStudent;
    public JFXTextField txtRegistrationNo;
    public JFXButton btnPayment;
    public Label lblRegistrationNo;
    public JFXTextField txtContactNo;
    public JFXTextField txtAddress;
    public ComboBox<String> cmbGrade;
    public RadioButton rdBtnMale;
    public RadioButton rdBtnFemale;
    public ComboBox<String> cmbDay;
    public ComboBox<String> cmbMonth;
    public ComboBox<String> cmbYear;
    public JFXTextField txtStudentName;
    public Label lblParentId;
    public JFXTextField txtParentName;
    public ComboBox<String> cmbParentTitle;
    public RadioButton rdMale;
    public RadioButton rdFemale;
    public JFXTextField txtParentJob;
    public JFXTextField txtNIC;
    public JFXTextField txtParentAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtParentContactNo;
    public JFXButton btnRegisterStudent;
    public JFXTextField txtParentId;
    public Label lblStudentRegistration;
    public Label lblParentNum;
    public Label lblWarningMessage;
    public Label lblWarningMessageCmb;
    public Label lblWarngMessageContactNo;
    public Label warningMessageParentName;
    public Label warningMessageNIC;
    public Label warningMessageParentContactNo;
    public Label warningMessageParentJob;
    public Label warningMessageParentGender;
    public Label lblWarningMessageParentTitle;
    public Label lblWarningMessageGrade;
    public Label lblWarningMessageStudentGender;
    public Label lblWarningMessageEmail;
    public JFXButton clearTxtFields;


    LinkedHashMap<JFXTextField,Pattern> guardianMap = new LinkedHashMap<>();
    LinkedHashMap<JFXTextField,Pattern> studentMap = new LinkedHashMap<>();

    Pattern name = Pattern.compile("^([A-z.\\s]{3,})$");
    Pattern contactNo = Pattern.compile("^([0-9]{10})$");
    Pattern address = Pattern.compile("^([A-z0-9,/\\s]{3,})$");
    Pattern nic = Pattern.compile("^([0-9]{9}[v|V]|[0-9]{12})$");
    Pattern job = Pattern.compile("^([A-Z][a-z]{4,})$");
    Pattern email = Pattern.compile("^([a-z0-9]{4,}\\@gmail.com)$");

    public void initialize(){
        storeValidation();
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

        btnDelete.setDisable(true);
        btnSearch.setDisable(true);
        txtRegistrationNo.setDisable(true);
        rdBtnAddNewStudent.setSelected(true);
        txtParentId.setDisable(true);

        ObservableList<String> days = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30");
        cmbDay.setItems(days);

        ObservableList<String> months = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12");
        cmbMonth.setItems(months);

        ObservableList<String> years = FXCollections.observableArrayList("2014","2015","2016","2017");
        cmbYear.setItems(years);

        ObservableList<String> grade = FXCollections.observableArrayList("Lower Kindergarten","Upper Kindergarten");
        cmbGrade.setItems(grade);

        ObservableList<String> parentTitle = FXCollections.observableArrayList("Mr","Mrs");
        cmbParentTitle.setItems(parentTitle);

        try {
            lblRegistrationNo.setText(new StudentController().setRegistrationNo());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            lblParentId.setText(new ParentController().setParentId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        for (JFXTextField jfxTextField : studentMap.keySet()) {
            jfxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
               if (validateStudentFinal() && validateGuardianFinal()){
                           btnRegisterStudent.setDisable(false);
               }else {
                   btnRegisterStudent.setDisable(true);
               }
            });
        }

        for (JFXTextField jfxTextField : guardianMap.keySet()) {
            jfxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (validateStudentFinal() && validateGuardianFinal()){
                    btnRegisterStudent.setDisable(false);
                }else {
                    btnRegisterStudent.setDisable(true);
                }
            });
        }

        cmbDay.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });

        cmbMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });

        cmbYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });

        cmbGrade.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });

        rdBtnFemale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });

        rdBtnMale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });

        cmbParentTitle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });

        rdMale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });

        rdBtnFemale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });

        rdFemale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (validateStudentFinal() && validateGuardianFinal()){
                btnRegisterStudent.setDisable(false);
            }else {
                btnRegisterStudent.setDisable(true);
            }
        });
    }

    private void storeValidation() {
        studentMap.put(txtStudentName,name);
        studentMap.put(txtAddress,address);
        studentMap.put(txtContactNo,contactNo);

        guardianMap.put(txtParentName,name);
        guardianMap.put(txtParentAddress,address);
        guardianMap.put(txtNIC,nic);
        guardianMap.put(txtParentJob,job);
        guardianMap.put(txtParentContactNo,contactNo);
        guardianMap.put(txtEmail,email);
    }

    public boolean validateStudentDetails(){
        for (JFXTextField jfxTextField : studentMap.keySet()) {
            Pattern pattern = studentMap.get(jfxTextField);
            if (!pattern.matcher(jfxTextField.getText()).matches()){
                jfxTextField.setStyle("-fx-border-color: red");
                return false;
            }else {
                jfxTextField.setStyle("-fx-border-color: green");
            }
        }
        return true;
    }

    public boolean validateStudentFinal(){
        boolean validatestudent = validateStudentDetails();
        boolean gradeSelected = cmbGrade.getValue()!=null;
        boolean birthDaySelected = cmbDay.getValue()!=null && cmbMonth.getValue()!=null && cmbYear.getValue()!=null ;
        boolean studentGender = rdBtnMale.isSelected() || rdBtnFemale.isSelected();

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
        boolean parentGender = (rdMale.isSelected() || rdFemale.isSelected()) ? true : false;

        if (validateGuardianDetails && titleSelected && parentGender){
            return true;
        }
        return false;
    }

    public void setTxtDetails(Student newValue){
        txtRegistrationNo.setText(newValue.getStRegisterNo());
        txtStudentName.setText(newValue.getStName());
        cmbGrade.setValue(newValue.getStGrade());
        txtContactNo.setText(newValue.getStContactNo());
        txtAddress.setText(newValue.getStAddress());

        if (newValue.getStGender().equals("Male")){
            rdBtnMale.setSelected(true);
        }else {
            rdBtnFemale.setSelected(true);
        }
        String day = newValue.getStDob().split("/")[0];
        String month = newValue.getStDob().split("/")[1];
        String year = newValue.getStDob().split("/")[2];

        cmbDay.setValue(day);
        cmbMonth.setValue(month);
        cmbYear.setValue(year);

        txtParentId.setText(newValue.getParentDetails().getPrtId());
        txtParentName.setText(newValue.getParentDetails().getPrtName());
        txtNIC.setText(newValue.getParentDetails().getPrtNIC());
        txtParentAddress.setText(newValue.getParentDetails().getPrtAddress());
        txtParentContactNo.setText(newValue.getParentDetails().getPrtContactNo());
        txtParentJob.setText(newValue.getParentDetails().getPrtJob());
        txtEmail.setText(newValue.getParentDetails().getPrtEmail());

        cmbParentTitle.setValue(newValue.getParentDetails().getPrtTitle());
        if (newValue.getParentDetails().getPrtGender().equals("Male")){
            rdMale.setSelected(true);
        }else {
            rdFemale.setSelected(true);
        }
    }


    public void backToAdminForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) studentRegistrationFormContext.getScene().getWindow();
        window.setX(550);
        window.setY(250);
        window.setScene(new Scene(load));
    }

    public void moveToSearchForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SearchListForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) studentRegistrationFormContext.getScene().getWindow();
        window.setX(500);
        window.setY(150);
        window.setScene(new Scene(load));

    }

    //----------------Register Student And Parent-------------------------------//
    public void registerStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        //----------------------------------Start Update--------------------------------------------------//
        if (btnRegisterStudent.getText().equals("Update")){

            String parentGender1 = (rdMale.isSelected()) ? "Male" : (rdFemale.isSelected()) ? "Female" : null;
            ParentDetails updateParent = new ParentDetails(
                    txtParentId.getText(),
                    lblRegistrationNo.getText(),
                    txtParentName.getText(),
                    cmbParentTitle.getValue(),
                    parentGender1,
                    txtParentJob.getText(),
                    txtNIC.getText(),
                    txtParentAddress.getText(),
                    txtEmail.getText(),
                    txtParentContactNo.getText()
            );

            String dob = cmbDay.getValue() + "/" + cmbMonth.getValue() + "/" + cmbYear.getValue();
            String gender = (rdBtnMale.isSelected()) ? "Male" : (rdBtnFemale.isSelected()) ? "Female" : null;
            Student student = new StudentController().getStudent(txtRegistrationNo.getText());
            Student updateStudent = new Student(
                    txtRegistrationNo.getText(),
                    txtStudentName.getText(),
                    dob,
                    student.getStRegistrationDate(),
                    gender,
                    cmbGrade.getValue(),
                    txtAddress.getText(),
                    txtContactNo.getText(),
                    updateParent
            );

            if (new StudentController().updateStudent(updateStudent) && new ParentController().updateParentDetail(updateParent)) {
                new Alert(Alert.AlertType.INFORMATION, "Student Has Been Successfully Updated.").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again.").show();
            }
            //----------------------------------End Update--------------------------------------------------//

        }else {
                String parentGender = (rdMale.isSelected()) ? "Male" : (rdFemale.isSelected()) ? "Female" : null;
                ParentDetails parent = new ParentDetails(
                        lblParentId.getText(),
                        lblRegistrationNo.getText(),
                        txtParentName.getText(),
                        cmbParentTitle.getValue(),
                        parentGender,
                        txtParentJob.getText(),
                        txtNIC.getText(),
                        txtParentAddress.getText(),
                        txtEmail.getText(),
                        txtParentContactNo.getText()
                );

                String dob = cmbDay.getValue() + "/" + cmbMonth.getValue() + "/" + cmbYear.getValue();
                String gender = (rdBtnMale.isSelected()) ? "Male" : (rdBtnFemale.isSelected()) ? "Female" : null;

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
                btnRegisterStudent.setDisable(false);
        }
    }

    //--------------------------------------------------------------------------//

    public void isSelectExistingStudentOnAction(ActionEvent actionEvent) {
        rdBtnAddNewStudent.setSelected(false);
        btnSearch.setDisable(false);
        btnRegisterStudent.setDisable(true);
        txtRegistrationNo.setDisable(false);
        txtParentId.setDisable(false);
        lblStudentRegistration.setVisible(false);
        lblRegistrationNo.setVisible(false);
        lblParentNum.setVisible(false);
        lblParentId.setVisible(false);
        btnDelete.setDisable(false);

    }

    public void rdBtnAddNewStudentOnAction(ActionEvent actionEvent) {
        rdBtnExistingStudent.setSelected(false);
        btnSearch.setDisable(true);
        btnRegisterStudent.setDisable(false);
        txtParentId.setDisable(true);
        txtRegistrationNo.setDisable(true);
        lblStudentRegistration.setVisible(true);
        lblRegistrationNo.setVisible(true);
        lblParentNum.setVisible(true);
        lblParentId.setVisible(true);
    }

    public void deleteStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ButtonType yes =new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no =new ButtonType("No", ButtonBar.ButtonData.OK_DONE);

        Alert alert =new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove this Student and Parent ?",yes,no);
        alert.setTitle("Confirmation Alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no)==yes){
            if (new StudentController().deleteStudent(txtRegistrationNo.getText()) && new ParentController().deleteParentDetails(txtParentId.getText())){

            }
        }

    }

    public void clearTxtFieldsOnAction(ActionEvent actionEvent) {
        if (rdBtnExistingStudent.isSelected()){
            txtRegistrationNo.clear();
        }
        btnRegisterStudent.setDisable(true);
        cmbGrade.getSelectionModel().clearSelection();
        txtStudentName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        cmbDay.getSelectionModel().clearSelection();
        cmbMonth.getSelectionModel().clearSelection();
        cmbYear.getSelectionModel().clearSelection();
        cmbParentTitle.getSelectionModel().clearSelection();
        txtContactNo.clear();
        txtParentName.clear();
        txtParentContactNo.clear();
        txtParentAddress.clear();
        txtParentId.clear();
        txtEmail.clear();
        txtParentJob.clear();
        txtNIC.clear();
        rdFemale.setSelected(false);
        rdMale.setSelected(false);
        rdBtnMale.setSelected(false);
        rdBtnFemale.setSelected(false);
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

    public void rdBtnOnAction(ActionEvent actionEvent) {
        if (rdMale.isSelected()){
            rdFemale.setSelected(false);
        }
    }

    public void rdFemaleOnAction(ActionEvent actionEvent) {
        if (rdFemale.isSelected()){
            rdMale.setSelected(false);
        }
    }
}
