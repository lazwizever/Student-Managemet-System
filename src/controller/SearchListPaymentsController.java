package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Payment;
import model.Student;
import view.tm.ManageStudentMarksTM;
import view.tm.SearchListPaymentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class SearchListPaymentsController {
    public AnchorPane paymentContext;
    public JFXComboBox cmbSearchType;
    public TextField txtSearchBar;
    public JFXButton btnSearch;
    public TableView<SearchListPaymentTM> tblStudentPayments;
    public TableColumn colRegistrationNo;
    public TableColumn colStudentName;
    public TableColumn colPaymentId;
    public TableColumn colPaymentType;

    ObservableList<SearchListPaymentTM> setPaymentDetails = FXCollections.observableArrayList();

    public void initialize(){
        try {
            setTblDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colRegistrationNo.setCellValueFactory(new PropertyValueFactory<>("stRegistrationNo"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("stName"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

        tblStudentPayments.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                FXMLLoader lorder = new FXMLLoader(getClass().getResource("../view/PaymentReportsForm.fxml"));
                try {
                    Parent parent = lorder.load();
                    PaymentReportsFormController controller = lorder.getController();
                    controller.setTxtDetails(newValue);
                    controller.btnDelete.setDisable(false);

                    Stage window = (Stage) paymentContext.getScene().getWindow();
                    window.setScene(new Scene(parent));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        ObservableList<String> searchType = FXCollections.observableArrayList("Student Register No","Student Name");
        cmbSearchType.setItems(searchType);
    }

    private void setTblDetails() throws SQLException, ClassNotFoundException {
        ObservableList<Payment> allPaymentDetails = new PaymentController().getAllPaymentDetails();
        for (Payment tempPayment: allPaymentDetails
             ) {
            Student student = new StudentController().getStudent(tempPayment.getStRegistrationNo());
            String paymentType = new PaymentMasterController().getPaymentType(tempPayment.getPaymentTypeId());

            SearchListPaymentTM searchListPaymentTM = new SearchListPaymentTM(
                    tempPayment.getStRegistrationNo(),
                    student.getStName(),
                    tempPayment.getPaymentId(),
                    paymentType
            );
            setPaymentDetails.add(searchListPaymentTM);
        }
        tblStudentPayments.setItems(setPaymentDetails);
    }

    public void searchStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ObservableList<SearchListPaymentTM> searchstudents = FXCollections.observableArrayList();
        if (cmbSearchType.getValue().equals("Student Name")){
            ObservableList<Student> students = new StudentController().searchStudentNameForPayment(txtSearchBar.getText());
            L1:for (Student temp: students
            ) {
                for (SearchListPaymentTM searchStudent: searchstudents
                     ) {
                    if (temp.getStRegisterNo().equals(searchStudent.getStRegistrationNo())){
                        continue L1;
                    }
                }
                    ObservableList<Payment> paymentDetails = new PaymentController().getPaymentDetails(temp.getStRegisterNo());
                    for (Payment tempPayment: paymentDetails
                         ) {
                        String paymentType = new PaymentMasterController().getPaymentType(tempPayment.getPaymentTypeId());
                        SearchListPaymentTM searchListPaymentTM = new SearchListPaymentTM(
                                temp.getStRegisterNo(),
                                temp.getStName(),
                                tempPayment.getPaymentId(),
                                paymentType
                        );
                        searchstudents.add(searchListPaymentTM);
                    }

            }
        }else {

        }
        tblStudentPayments.setItems(searchstudents);
    }

    public void visibleSearchBarOnAction(ActionEvent actionEvent) {
    }

    public void backToPaymentReportsFrom(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PaymentReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) paymentContext.getScene().getWindow();
        window.setX(500);
        window.setY(250);
        window.setScene(new Scene(load));
    }
}
