package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.FinalExamMark;
import model.Student;
import view.tm.ManageStudentMarksTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewStudentMarksFormController {
    public AnchorPane viewStudentMarksContext;
    public Label lblDate;
    public Label lblTime;
    public TableView<ManageStudentMarksTM> tblVewMarks;
    public TableColumn colRegistrationNo;
    public TableColumn colDictation;
    public TableColumn colStNme;
    public TableColumn colGradeDictation;
    public TableColumn colGeneralEnglish;
    public TableColumn colGradeEnglish;
    public RadioButton rdBtnLKG;
    public RadioButton rdBtnUKG;
    public TableColumn colGrade;
    public TableColumn colSubject;
    public TableColumn colMark;

    ObservableList<ManageStudentMarksTM> studentMarks = FXCollections.observableArrayList();
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

        colRegistrationNo.setCellValueFactory(new PropertyValueFactory<>("stRegistrationNo"));
        colStNme.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("stGrade"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colMark.setCellValueFactory(new PropertyValueFactory<>("mark"));

    }

    public void backToClassTeacherForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/TeacherInChargeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) viewStudentMarksContext.getScene().getWindow();
        window.setX(550);
        window.setY(250);
        window.setScene(new Scene(load));
    }

    public void LKGOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (rdBtnUKG.isSelected()){
            rdBtnUKG.setSelected(false);
        }
        studentMarks.clear();
        ObservableList<FinalExamMark> allDetails = new FinalExamMarkController().getAllDetails();
        L1:for (FinalExamMark tempGrade: allDetails
        ) {
            String subject = new SubjectController().getSubject(tempGrade.getSubjectId());
            Student student = new StudentController().getStudent(tempGrade.getStRegistrationNo());

            if (tempGrade.getStGrade().equals("Lower Kindergarten")){
                ManageStudentMarksTM manageStudentMarksTM = new ManageStudentMarksTM(
                        student.getStRegisterNo(),
                        student.getStName(),
                        student.getStGrade(),
                        subject,
                        tempGrade.getMark()
                );
                studentMarks.add(manageStudentMarksTM);
            }
        }
        tblVewMarks.setItems(studentMarks);

//        ObservableList<FinalExamMark> allDetails = new FinalExamMarkController().getAllDetails();
//        L1:for (FinalExamMark temp: allDetails
//        ) {
//            for (ViewStudentMarksTM temp1 : studentMarks
//            ) {
//                if (temp1.getStRegistrationNo().equals(temp.getStRegistrationNo())) {
//                    continue L1;
//                }
//            }
//
//            String grade = new StudentController().getStudentGradeFromStId(temp.getStRegistrationNo());
//            String selectGrade;
//
//            if (rdBtnLKG.isSelected()) {
//                selectGrade = rdBtnLKG.getText();
//            } else {
//                selectGrade = rdBtnUKG.getText();
//            }
//
//            String subject = "Dictation";
//            int dictationMarks = 0;
//
//            if (subject=="Dictation"){
//                String subjectId = new SubjectController().getSubjectId(subject);
//                dictationMarks= new FinalExamMarkController().getStudentMarksForTbl(temp.getStRegistrationNo(), subjectId);
//
//            }
//            String subjectEnglish = "General English";
//            int englishMarks = 0;
//
//            if (subjectEnglish=="General English"){
//                String subjectId = new SubjectController().getSubjectId(subjectEnglish);
//                englishMarks = new FinalExamMarkController().getStudentMarksForTbl(temp.getStRegistrationNo(), subjectId);
//
//            }
//
//            if (selectGrade.equals(grade)) {
//                ViewStudentMarksTM viewStudentMarksTM = new ViewStudentMarksTM(
//                        temp.getStRegistrationNo(),
//                        new StudentController().getPaymentTableStudentName(temp.getStRegistrationNo()),
//                        dictationMarks,
//                        temp.getPassOrFail(),
//                        englishMarks,
//                        temp.getPassOrFail()
//                );
//                studentMarks.add(viewStudentMarksTM);
//
//            }
//        }
//        tblVewMarks.setItems(studentMarks);
    }

    public void UKgOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (rdBtnLKG.isSelected()){
            rdBtnLKG.setSelected(false);
        }
        studentMarks.clear();

        ObservableList<FinalExamMark> allDetails = new FinalExamMarkController().getAllDetails();
        L1:for (FinalExamMark tempGrade: allDetails
        ) {
            String subject = new SubjectController().getSubject(tempGrade.getSubjectId());
            Student student = new StudentController().getStudent(tempGrade.getStRegistrationNo());

            if (tempGrade.getStGrade().equals("Upper Kindergarten")){
                ManageStudentMarksTM manageStudentMarksTM = new ManageStudentMarksTM(
                        student.getStRegisterNo(),
                        student.getStName(),
                        student.getStGrade(),
                        subject,
                        tempGrade.getMark()
                );
                studentMarks.add(manageStudentMarksTM);
            }
        }
        tblVewMarks.setItems(studentMarks);

//        ObservableList<FinalExamMark> allDetails = new FinalExamMarkController().getAllDetails();
//        L1:for (FinalExamMark temp: allDetails
//             ) {
//            for (ViewStudentMarksTM temp1 : studentMarks
//            ) {
//                if (temp1.getStRegistrationNo().equals(temp.getStRegistrationNo())) {
//                    continue L1;
//                }
//            }
//
//            String grade = new StudentController().getStudentGradeFromStId(temp.getStRegistrationNo());
//            String selectGrade;
//
//            if (rdBtnLKG.isSelected()) {
//                selectGrade = rdBtnLKG.getText();
//            } else {
//                selectGrade = rdBtnUKG.getText();
//            }
//
//            String subject = "Dictation";
//            int dictationMarks = 0;
//
//            if (subject=="Dictation"){
//                String subjectId = new SubjectController().getSubjectId(subject);
//                dictationMarks= new FinalExamMarkController().getStudentMarksForTbl(temp.getStRegistrationNo(), subjectId);
//
//            }
//            String subjectEnglish = "General English";
//            int englishMarks = 0;
//
//            if (subjectEnglish=="General English"){
//                String subjectId = new SubjectController().getSubjectId(subjectEnglish);
//                englishMarks = new FinalExamMarkController().getStudentMarksForTbl(temp.getStRegistrationNo(), subjectId);
//
//            }
//
//            if (selectGrade.equals(grade)) {
//                ViewStudentMarksTM viewStudentMarksTM = new ViewStudentMarksTM(
//                        temp.getStRegistrationNo(),
//                        new StudentController().getPaymentTableStudentName(temp.getStRegistrationNo()),
//                        dictationMarks,
//                        temp.getPassOrFail(),
//                        englishMarks,
//                        temp.getPassOrFail()
//                );
//           studentMarks.add(viewStudentMarksTM);
//
//            }
//        }
//        tblVewMarks.setItems(studentMarks);
    }
}
