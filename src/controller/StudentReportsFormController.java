package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.FinalExamMark;
import model.Student;
import view.tm.ManageStudentMarksTM;
import view.tm.StudentPaymentsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentReportsFormController {
    public AnchorPane studentReportContext;
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox<String> cmbYear;
    public RadioButton rdBtnLKG;
    public RadioButton rdBtnUKG;
    public TableView tblPaidStudent;
    public TableColumn colRegistrationNo;
    public TableColumn colStName;
    public TableColumn colSelectStudent;
    public JFXCheckBox checkBoxSelectAll;
    public JFXButton btnUpgrade;
    public JFXButton btnDelete;
    public JFXButton btnViewAllStudents;
    public TableView<ManageStudentMarksTM> tblViewMarks;
    public TableColumn colViewMarksRegistrationNo;
    public TableColumn colStudentName;
    public TableColumn colDictationMark;
    public TableColumn colDictationResult;
    public TableColumn colEnglishMark;
    public TableColumn colEnglishResult;
    public RadioButton rdBtnLKGViewMarks;
    public RadioButton rdBtnUKGViewMarks;
    public BarChart<String,Number> studentBarChart;
    public TableColumn colStGrade;
    public TableColumn colStSubject;
    public TableColumn colMark;



    ObservableList<StudentPaymentsTM> loadStudentPayments = FXCollections.observableArrayList();
    ObservableList<ManageStudentMarksTM> studentMarks = FXCollections.observableArrayList();

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

            colViewMarksRegistrationNo.setCellValueFactory(new PropertyValueFactory<>("stRegistrationNo"));
            colStudentName.setCellValueFactory(new PropertyValueFactory<>("stName"));
            colStGrade.setCellValueFactory(new PropertyValueFactory<>("stGrade"));
            colStSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
            colMark.setCellValueFactory(new PropertyValueFactory<>("mark"));


        }

        colRegistrationNo.setCellValueFactory(new PropertyValueFactory<>("stRegistrationNo"));
        colStName.setCellValueFactory(new PropertyValueFactory<>("stName"));
        colSelectStudent.setCellValueFactory(new PropertyValueFactory<>("jfxCheckBox"));

        ObservableList<String> years = FXCollections.observableArrayList("2020", "2021");
        cmbYear.setItems(years);

        rdBtnLKG.setDisable(true);
        rdBtnUKG.setDisable(true);
        btnUpgrade.setDisable(true);
        btnDelete.setDisable(true);
        checkBoxSelectAll.setDisable(true);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String formatYear = formatter.format(date);

        ObservableList<String> year = FXCollections.observableArrayList();
        int currentYear = Integer.valueOf(formatYear);

        for (int i = 2021; i <=currentYear ; i++) {
            year.add(String.valueOf(i));
        }

        xAxis.setCategories(year);


        XYChart.Series series1 = new XYChart.Series();

        try {

            for (String temp : year) {
                int totalStudent = new StudentController().getTotalStudent(temp);
                series1.getData().add(new XYChart.Data<>(temp,totalStudent));
            }
            studentBarChart.getData().addAll(series1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //yAxis.setCategories();
       // studentBarChart.setData();

//        XYChart.Series series2 = new XYChart.Series<>();
//        //series2.setName("2021");
//

      //  studentBarChart.getData().addAll(series2);


    }

    public void backToAdminFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) studentReportContext.getScene().getWindow();
        window.setX(550);
        window.setY(250);
        window.setScene(new Scene(load));

    }

    //==============================Upgrade Student=================================================//
    public void upgradeStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<StudentPaymentsTM> selectedStudents = new ArrayList<>();

        String studentGrade = rdBtnUKG.getText();
        for (StudentPaymentsTM temp: loadStudentPayments
             ) {
            if (temp.getJfxCheckBox().isSelected()){
                String paymentTypeId = new PaymentMasterController().getPaymentTypeId("Registration Fees U.K.G");
                if (new PaymentController().getPayment(paymentTypeId,temp.getStRegistrationNo())!=null){
                    selectedStudents.add(temp);
                }else {
                    new Alert(Alert.AlertType.WARNING,temp.getStRegistrationNo()+" - "+temp.getStName()+" hadn't paid registration fess.").show();
                    return;
                }

            }
        }
        if (new StudentController().upgradeStudent(selectedStudents)){
            new Alert(Alert.AlertType.INFORMATION,"Student has been successfully updated").show();
            setStudentsToTbl();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }

    }

    public void deleteStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       if (rdBtnUKG.isSelected()){
           for (StudentPaymentsTM temp: loadStudentPayments
                ) {
               if (temp.getJfxCheckBox().isSelected()){
                   if (new StudentController().deleteStudent(temp.getStRegistrationNo())){
                        continue;
                   }else {
                       new Alert(Alert.AlertType.WARNING,"Something Going Wrong").show();
                       return;
                   }
               }
           }
           new Alert(Alert.AlertType.INFORMATION,"Successful deleted").show();
           setStudentsToTbl();
       }

    }

        public void selectAllStudentOnAction (ActionEvent actionEvent){
            if (checkBoxSelectAll.isSelected()){
                for (int i = 0; i < loadStudentPayments.size(); i++) {
                    loadStudentPayments.get(i).getJfxCheckBox().setSelected(true);
                }
            }else {
                for (int i = 0; i < loadStudentPayments.size(); i++) {
                    loadStudentPayments.get(i).getJfxCheckBox().setSelected(false);
                }
            }

        }

        public void selectYearOnAction (ActionEvent actionEvent){
            rdBtnLKG.setDisable(false);
            rdBtnUKG.setDisable(false);
        }

        public void rdBtnLKGOnAction (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            if (rdBtnUKG.isSelected()) {
                rdBtnUKG.setSelected(false);
            }
            setStudentsToTbl();
            btnUpgrade.setDisable(false);
            btnDelete.setDisable(true);
        }

        public void btnUKGOnAction (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            if (rdBtnLKG.isSelected()) {
                rdBtnLKG.setSelected(false);
            }
            setStudentsToTbl();
            btnDelete.setDisable(false);
            btnUpgrade.setDisable(true);

        }
    //=============================================================================================//



    //==============================View Marks=================================================//
    public void LKGOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (rdBtnUKGViewMarks.isSelected()){
            rdBtnUKGViewMarks.setSelected(false);
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
        tblViewMarks.setItems(studentMarks);
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
//            if (rdBtnLKGViewMarks.isSelected()) {
//                selectGrade = rdBtnLKGViewMarks.getText();
//            } else {
//                selectGrade = rdBtnUKGViewMarks.getText();
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
//                        temp.getStGrade(),
//                        temp.getSubjectId(),
//                        temp.getMark()
//                );
//                studentMarks.add(viewStudentMarksTM);
//
//            }
//        }
//        tblViewMarks.setItems(studentMarks);
    }

    public void UKGOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (rdBtnLKGViewMarks.isSelected()){
            rdBtnLKGViewMarks.setSelected(false);
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
        tblViewMarks.setItems(studentMarks);
    }
    //========================================================================================//

    public void setStudentsToTbl() throws SQLException, ClassNotFoundException {
        loadStudentPayments.clear();
        ObservableList<String> stIds = new PaymentController().getPaymentDate(cmbYear.getValue());
        L1:
        for (String temp : stIds
        ) {
            for (StudentPaymentsTM temp1 : loadStudentPayments
            ) {
                if (temp1.getStRegistrationNo().equals(temp)) {
                    continue L1;
                }
            }
            String grade = new StudentController().getStudentGradeFromStId(temp);
            String selectGrade;

            if (rdBtnLKG.isSelected()) {
                selectGrade = rdBtnLKG.getText();
            } else {
                selectGrade = rdBtnUKG.getText();
            }

            if (selectGrade.equals(grade)) {
                JFXCheckBox jfxCheckBox = new JFXCheckBox();
                if (new PaymentController().getPaymentDetails(temp).size() >= 4) {
                    StudentPaymentsTM studentPaymentsTM = new StudentPaymentsTM(
                            temp,
                            new StudentController().getPaymentTableStudentName(temp),
                            jfxCheckBox
                    );
                    loadStudentPayments.add(studentPaymentsTM);
                }
            }
        }
        tblPaidStudent.setItems(loadStudentPayments);
        checkBoxSelectAll.setDisable(false);
    }
}
