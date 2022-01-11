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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.FinalExam;
import model.FinalExamMark;
import model.Student;
import model.Subject;
import view.tm.ManageStudentMarksTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class AddStudentMarksFormController {
    public AnchorPane manageMarksOnAction;
    public Label lblDate;
    public Label lblTime;
    public TableColumn colRegistrationNo;
    public TableColumn colGrade;
    public TableColumn colDictation;
    public TableColumn colGeneralEnglish;
    public JFXTextField txtStRegistrationNo;
    public JFXTextField txtStudentName;
    public TextField txtSearchBar;
    public JFXComboBox<String> cmbSearchType;
    public JFXComboBox<String> cmbSubjectType;
    public JFXTextField txtUpdateEnterMarks;
    public JFXButton btnSaveMarks;
    public TextField txtSearchForSearchStudent;
    public JFXButton btnSeachForSearchMark;
    public JFXButton btnMoveToSearchList;
    public JFXButton btnAddMark;
    public TableView<ManageStudentMarksTM> tblStudentMark;
    public TableColumn colStudentName;
    public TableColumn colResult;
    public JFXRadioButton rdBtnSearchMark;
    public JFXRadioButton rbBtnUpdateMark;
    public JFXRadioButton rdBtnAddMark;
    public JFXRadioButton rdBtnUKG;
    public JFXRadioButton rdBtnLKG;
    public Label lblWarningMessageEnterMarks;

    ObservableList<ManageStudentMarksTM> studentMarkDetails = FXCollections.observableArrayList();

    Pattern mark = Pattern.compile("^([0-9]{1,3})$");
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
        ObservableList<String> subjects = FXCollections.observableArrayList();

        try {
            setAllStudents();
            ObservableList<Subject> subjectDetails = new SubjectController().getSubjectDetails();
            for (int i = 0; i < subjectDetails.size(); i++) {
                subjects.add(subjectDetails.get(i).getSubject());
            }
            cmbSubjectType.setItems(subjects);
        } catch (SQLException throwables) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<String> searchType = FXCollections.observableArrayList("Student Registration No","Student Name");

        colRegistrationNo.setCellValueFactory(new PropertyValueFactory<>("stRegistrationNo"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("stName"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("stGrade"));
        colDictation.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colGeneralEnglish.setCellValueFactory(new PropertyValueFactory<>("mark"));
       // colResult.setCellValueFactory(new PropertyValueFactory<>("result"));

      //  ObservableList<String> selectSubject = FXCollections.observableArrayList();
        tblStudentMark.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setTxtDetailFromTbl(newValue);
               // selectSubject.add(newValue.getSubject());
            }
        });
      //  cmbSubjectType.setItems(selectSubject);
        txtUpdateEnterMarks.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                if (mark.matcher(newValue).matches()){
                    int mark = Integer.parseInt(txtUpdateEnterMarks.getText());
                    if (mark>=0 && mark <=100){
                        txtUpdateEnterMarks.setStyle("-fx-border-color: green");
                        btnAddMark.setDisable(false);
                    }else {
                        txtUpdateEnterMarks.setStyle("-fx-border-color: red");
                        btnAddMark.setDisable(true);
                    }
                }else {
                    txtUpdateEnterMarks.setStyle("-fx-border-color: red");
                    btnAddMark.setDisable(true);
                }
            }
        });

    }

    private void setTxtDetailFromTbl(ManageStudentMarksTM newValue) {
        txtStRegistrationNo.setText(newValue.getStRegistrationNo());
        txtStudentName.setText(newValue.getStName());

    }

    public void backToClassTeacherFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ClassTeacherForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) manageMarksOnAction.getScene().getWindow();
        window.setX(550);
        window.setY(250);
        window.setScene(new Scene(load));
    }

    public void setTxtDetails(Student newValue) {
        txtStudentName.setText(newValue.getStName());
        txtStRegistrationNo.setText(newValue.getStRegisterNo());
    }

    public void moveToSearhListOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SearchListFormStudentMarks.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)manageMarksOnAction.getScene().getWindow();
        window.setX(650);
        window.setY(250);
        window.setScene(new Scene(load));
    }

    public void SaveMarksOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student student = new StudentController().getStudent(txtStRegistrationNo.getText());
        String subjectId = new SubjectController().getSubjectId(cmbSubjectType.getValue());

        if (btnAddMark.getText().equals("Update Mark")){
            if (validateStudentMark()) {

                FinalExamMark finalExamMark = new FinalExamMark(
                        txtStRegistrationNo.getText(),
                        student.getStGrade(),
                        subjectId,
                        Integer.parseInt(txtUpdateEnterMarks.getText())
                );

                if (new FinalExamMarkController().updateStudentMarks(finalExamMark)) {
                    new Alert(Alert.AlertType.INFORMATION, "Student Marks Has Been Successfully Updated.").show();
                    setAllStudents();
                    txtStudentName.clear();
                   txtStRegistrationNo.clear();
                    txtUpdateEnterMarks.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again.").show();
                }
            }
        }else {
            if (validateStudentMark()) {
                FinalExamMark finalExamMark = new FinalExamMark(
                        txtStRegistrationNo.getText(),
                        student.getStGrade(),
                        subjectId,
                        Integer.parseInt(txtUpdateEnterMarks.getText())
                );

                if (new FinalExamMarkController().setStudentMarks(finalExamMark)) {
                    new Alert(Alert.AlertType.INFORMATION, "Student Marks Has Been Successfully Added.").show();
                    setAllStudents();
                    txtStudentName.clear();
                  txtStRegistrationNo.clear();
                    txtUpdateEnterMarks.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again.").show();
                }
            }
        }
    }

    public void cmbSetSubjectMark(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String subjectId = new SubjectController().getSubjectId(cmbSubjectType.getValue());
        int studentMarksForTbl = new FinalExamMarkController().getStudentMarksForTbl(txtStRegistrationNo.getText(), subjectId);
        if (studentMarksForTbl!=0){
            txtUpdateEnterMarks.setText(String.valueOf(studentMarksForTbl));
            btnAddMark.setText("Update Mark");
        }else {
            btnAddMark.setText("Add Mark");
        }
    }

    public void setAllStudents() throws SQLException, ClassNotFoundException {
        studentMarkDetails.clear();
        ObservableList<FinalExamMark> allDetails = new FinalExamMarkController().getAllDetails();
        L1:for (FinalExamMark tempGrade: allDetails
        ) {
            String subject = new SubjectController().getSubject(tempGrade.getSubjectId());
            Student student = new StudentController().getStudent(tempGrade.getStRegistrationNo());

                ManageStudentMarksTM manageStudentMarksTM = new ManageStudentMarksTM(
                        student.getStRegisterNo(),
                        student.getStName(),
                        student.getStGrade(),
                       subject,
                       tempGrade.getMark()
                );
                studentMarkDetails.add(manageStudentMarksTM);

        }
        tblStudentMark.setItems(studentMarkDetails);
    }

    public boolean validateStudentMark(){
        if (txtUpdateEnterMarks.getText().isEmpty()){
                lblWarningMessageEnterMarks.setText("* Marks Can't Be Empty");
                lblWarningMessageEnterMarks.setVisible(true);
        }else if (!(Integer.parseInt(txtUpdateEnterMarks.getText()) <= 100) || !(Integer.parseInt(txtUpdateEnterMarks.getText()) >= 0)) {
            lblWarningMessageEnterMarks.setText("* Invalid Mark Type");
            lblWarningMessageEnterMarks.setVisible(true);
        }else {
            return true;
        }
            return false;
    }

    public void clickEnterMarks(MouseEvent mouseEvent) {
        lblWarningMessageEnterMarks.setVisible(false);
    }
}
