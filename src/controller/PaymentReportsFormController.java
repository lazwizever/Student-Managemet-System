package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Payment;
import model.ReportTM;
import model.Student;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.PaymentReportsTM;
import view.tm.SearchListPaymentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

public class PaymentReportsFormController {
    public AnchorPane paymentReportContext;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtRegistrationNo;
    public JFXTextField txtStName;
    public JFXTextField txtAddress;
    public JFXTextField txtGender;
    public JFXTextField txtDOB;
    public JFXTextField txtContactNo;
    public JFXComboBox<String> cmbFeeType;
    public JFXButton btnSearchStudent;
    public Label lblPaymentId;
    public Label lblBalance;
    public TextField txtCash;
    public Label lblAmount;
    public Rectangle rectangle;
    public Line line2;
    public Line line3;
    public Line line4;
    public Label balanceLbl;
    public Label cashLbl;
    public Label amountLbl;
    public Label lblPaymentTopic;
    public Line line1;
    public Label lblPaymentNum;
    public JFXButton btnGenerateBill;
    public TableView tblPayments;
    public JFXComboBox<String> cmbGrade;
    public JFXComboBox<String> cmbFeeTypes;
    public Label lblReportsTopic;
    public Line line;
    public JFXButton btnViewReports;
    public TableColumn colRegistrationNo;
    public TableColumn colStudentName;
    public TableColumn colPaymentDate;
    public TableColumn colPaidOrNot;
    public JFXButton btnPaymentReport;
    public JFXButton btnPayment;
    public JFXButton btnClear;
    public JFXButton btnDelete;
    public JFXButton btnSearchPayment;
    public JFXButton btnPrintBill;
//    String paymentTypeId;

    ObservableList<PaymentReportsTM> paymentDetails = FXCollections.observableArrayList();

    Pattern cash = Pattern.compile("^([0-9]{1,})");
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
            lblPaymentId.setText(new PaymentController().generatePaymentId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObservableList<String> grade = FXCollections.observableArrayList("Lower Kindergarten","Upper Kindergarten");
        cmbGrade.setItems(grade);

        ObservableList<String> feeTypes = FXCollections.observableArrayList("Registration Fees L.K.G","Registration Fees U.K.G","1st Term Fees L.K.G","2st Term Fees L.K.G","3st Term Fees L.K.G","1st Term Fees U.K.G","2nd Term Fees U.K.G","3rd Term Fees U.K.G");
        cmbFeeTypes.setItems(feeTypes);

        colRegistrationNo.setCellValueFactory(new PropertyValueFactory<>("stRegistrationNo"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("stName"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paidDate"));
        colPaidOrNot.setCellValueFactory(new PropertyValueFactory<>("paidOrNot"));

        btnDelete.setDisable(true);

        txtCash.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                if (cash.matcher(newValue).matches()){
                    double amount = Double.parseDouble(lblAmount.getText());
                    double cashAmount = Double.parseDouble(txtCash.getText());
                    if (cashAmount>=amount){
                        txtCash.setStyle("-fx-border-color:green");
                        btnGenerateBill.setDisable(false);
                    }else {
                        txtCash.setStyle("-fx-border-color: red");
                        btnGenerateBill.setDisable(true);
                    }
                }else {
                    txtCash.setStyle("-fx-border-color: red");
                    btnGenerateBill.setDisable(true);
                }
            }
        });

    }

    public void backToAdminFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) paymentReportContext.getScene().getWindow();
        window.setX(550);
        window.setY(250);
        window.setScene(new Scene(load));
    }

    public void moveToSearchListFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PaymentFormSearchList.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) paymentReportContext.getScene().getWindow();
        window.setX(500);
        window.setY(150);
        window.setScene(new Scene(load));



    }

    public void setDetails(Student newValue){
        txtRegistrationNo.setText(newValue.getStRegisterNo());
        txtStName.setText(newValue.getStName());
        txtGender.setText(newValue.getStGender());
        txtDOB.setText(newValue.getStDob());
        txtContactNo.setText(newValue.getStContactNo());
        txtAddress.setText(newValue.getStAddress());
        txtRegistrationNo.setText(newValue.getStRegisterNo());

        ObservableList<String> feeTye = FXCollections.observableArrayList();
        if (newValue.getStGrade().equals("Lower Kindergarten")){
            feeTye.addAll("Registration Fees L.K.G","1st Term Fees L.K.G","2st Term Fees L.K.G","3st Term Fees L.K.G","Registration Fees U.K.G");
        }else {
            feeTye.addAll("Registration Fees U.K.G","1st Term Fees U.K.G","2nd Term Fees U.K.G","3rd Term Fees U.K.G");
        }

        cmbFeeType.setItems(feeTye);

    }

    public void selectFeeTypeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String paymentAmount = new PaymentMasterController().getPaymentAmount(cmbFeeType.getValue());
        lblAmount.setText(paymentAmount);

    }

    public void generateBillOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String paymentTypeId = new PaymentMasterController().getPaymentTypeId(cmbFeeType.getValue());
        Payment payment = new PaymentController().getPayment(paymentTypeId, txtRegistrationNo.getText());

        if (payment==null){
            Payment studentPayment = new Payment(
                        lblPaymentId.getText(),
                        txtRegistrationNo.getText(),
                        new PaymentMasterController().getPaymentTypeId(cmbFeeType.getValue()),
                        lblDate.getText()
                );
                if (new PaymentController().setStudentPaymentDetails(studentPayment)){
                    new Alert(Alert.AlertType.INFORMATION,"Payment has been successfully done.").show();
                    lblPaymentId.setText(new PaymentController().generatePaymentId());
                }else {
                    new Alert(Alert.AlertType.WARNING,"Try again.").show();
                }

        }else {
            new Alert(Alert.AlertType.WARNING,"This payment has been already paid.").show();
        }
    }

    public void balanceOnAction(ActionEvent actionEvent) {
        double balance = Double.valueOf(txtCash.getText()) - Double.parseDouble(lblAmount.getText());
        lblBalance.setText(String.valueOf(balance));
    }

    public void moveToReportsOnAction(ActionEvent actionEvent) {
        txtAddress.setVisible(false);
        txtStName.setVisible(false);
        txtContactNo.setVisible(false);
        txtRegistrationNo.setVisible(false);
        txtGender.setVisible(false);
        txtDOB.setVisible(false);
        cmbFeeType.setVisible(false);
        lblPaymentId.setVisible(false);
        lblPaymentNum.setVisible(false);
        lblPaymentTopic.setVisible(false);
        line1.setVisible(false);
        line2.setVisible(false);
        line3.setVisible(false);
        line4.setVisible(false);
        rectangle.setVisible(false);
        lblAmount.setVisible(false);
        lblBalance.setVisible(false);
        lblAmount.setVisible(false);
        amountLbl.setVisible(false);
        cashLbl.setVisible(false);
        balanceLbl.setVisible(false);
        txtCash.setVisible(false);
        btnSearchStudent.setVisible(false);
        btnGenerateBill.setVisible(false);
        tblPayments.setVisible(true);
        cmbFeeTypes.setVisible(true);
        cmbGrade.setVisible(true);
        line.setVisible(true);
        lblReportsTopic.setVisible(true);
        btnViewReports.setVisible(true);
        btnSearchPayment.setVisible(false);
        btnSearchPayment.setVisible(false);
        btnClear.setVisible(false);
        btnDelete.setVisible(false);
        btnPrintBill.setVisible(false);

    }

    public void moveToPayments(ActionEvent actionEvent) {
        txtAddress.setVisible(true);
        txtStName.setVisible(true);
        txtContactNo.setVisible(true);
        txtRegistrationNo.setVisible(true);
        txtGender.setVisible(true);
        txtDOB.setVisible(true);
        cmbFeeType.setVisible(true);
        lblPaymentId.setVisible(true);
        lblPaymentNum.setVisible(true);
        lblPaymentTopic.setVisible(true);
        line1.setVisible(true);
        line2.setVisible(true);
        line3.setVisible(true);
        line4.setVisible(true);
        rectangle.setVisible(true);
        lblAmount.setVisible(true);
        lblBalance.setVisible(true);
        lblAmount.setVisible(true);
        amountLbl.setVisible(true);
        cashLbl.setVisible(true);
        balanceLbl.setVisible(true);
        txtCash.setVisible(true);
        btnSearchStudent.setVisible(true);
        btnGenerateBill.setVisible(true);
        tblPayments.setVisible(false);
        cmbFeeTypes.setVisible(false);
        cmbGrade.setVisible(false);
        line.setVisible(false);
        lblReportsTopic.setVisible(false);
        btnViewReports.setVisible(false);
        btnSearchPayment.setVisible(true);
        btnClear.setVisible(true);
        btnDelete.setVisible(true);
        btnPrintBill.setVisible(true);
    }

    public void viewReportsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        for (Student temp: new StudentController().getAllStudentForGradeType(cmbGrade.getValue())
             ) {
            String paymentTypeId = new PaymentMasterController().getPaymentTypeId(cmbFeeTypes.getValue());
            Payment payment = new PaymentController().getPayment(paymentTypeId, temp.getStRegisterNo());

            String paymentDate;
            String paidOrNot;

            if (payment!=null){
                paymentDate = payment.getPaymentDate();
                paidOrNot = "Paid";
            }else {
                paymentDate = "-";
                paidOrNot = "Not Paid";
            }

            PaymentReportsTM paymentReportsTM = new PaymentReportsTM(
                        temp.getStRegisterNo(),
                        temp.getStName(),
                        paymentDate,
                        paidOrNot
                );
                paymentDetails.add(paymentReportsTM);
        }
        tblPayments.setItems(paymentDetails);

    }

    public void clearTblOnAction(ActionEvent actionEvent) {
        paymentDetails.clear();
    }

    public void btnChangeColourPaymentReport(MouseEvent mouseEvent) {
//        btnPaymentReport.setStyle("-fx-background-color:"+"#c4c4c4"+";");
////        btnPaymentReport.setStyle("-fx-border-color:"+"#ffffff"+";");
////        btnPaymentReport.setStyle("-fx-border-width:"+"5"+";");
//
//          btnPayment.setStyle("-fx-background-color:"+"#273c75"+";");
//          btnPayment.setStyle("-fx-border-color:"+"#ffffff"+";");
//          //btnPayment.setStyle("-fx-border-width:"+"5"+";");

    }

    public void chageColourBtnPayment(MouseEvent mouseEvent) {
//        btnPayment.setStyle("-fx-background-color:"+"#c4c4c4"+";");
////        btnPayment.setStyle("-fx-border-color:"+"#ffffff"+";");
////        btnPayment.setStyle("-fx-border-width:"+"5"+";");
//
//
//          btnPaymentReport.setStyle("-fx-background-color:"+"#273c75"+";");
//          btnPaymentReport.setStyle("-fx-border-color:"+"#ffffff"+";");
//          //btnPaymentReport.setStyle("-fx-border-width:"+"5"+";");

    }

    public void clearOnAction(ActionEvent actionEvent) {
        btnGenerateBill.setDisable(true);
        txtCash.setStyle("-fx-border-color: silver");
        txtRegistrationNo.clear();
        txtGender.clear();
        txtContactNo.clear();
        txtDOB.clear();
        txtAddress.clear();
        txtStName.clear();
        txtCash.clear();
        cmbFeeType.getSelectionModel().clearSelection();
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
                ButtonType yes =new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no =new ButtonType("No", ButtonBar.ButtonData.OK_DONE);

                Alert alert =new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove this Student Payment ?",yes,no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(no)==yes){
                    if (new PaymentController().deleteStudentPayments(lblPaymentId.getText())){}
                }
    }

    public void moveToPaymentListOnAction(ActionEvent actionEvent) throws IOException {

        URL resource = getClass().getResource("../view/SearchListPayments.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) paymentReportContext.getScene().getWindow();
        window.setX(500);
        window.setY(150);
        window.setScene(new Scene(load));
    }

    public void setTxtDetails(SearchListPaymentTM newValue) throws SQLException, ClassNotFoundException {
        txtRegistrationNo.setText(newValue.getStRegistrationNo());
        txtStName.setText(newValue.getStName());
        Student student = new StudentController().getStudent(newValue.getStRegistrationNo());
        ObservableList<String> paymentType = FXCollections.observableArrayList();
        paymentType.add(newValue.getPaymentType());
        cmbFeeType.setItems(paymentType);

        txtAddress.setText(student.getStAddress());
        txtDOB.setText(student.getStDob());
        txtContactNo.setText(student.getStContactNo());
        txtGender.setText(student.getStGender());
        lblPaymentId.setText(newValue.getPaymentId());
    }

    public void openPaymentReportEvent(MouseEvent mouseEvent) {
        try {

            ArrayList<ReportTM> studentReportDetails = new ArrayList<>();

            ReportTM reportTM = new ReportTM(
                    txtRegistrationNo.getText(),
                    txtStName.getText(),
                    cmbFeeType.getValue(),
                    Double.parseDouble(lblAmount.getText()),
                    Double.parseDouble(txtCash.getText()),
                    Double.parseDouble(lblBalance.getText())
            );
            studentReportDetails.add(reportTM);

            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/Student Payment Report.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanCollectionDataSource(studentReportDetails));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
