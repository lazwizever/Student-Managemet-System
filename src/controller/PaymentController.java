package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Payment;
import model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentController {

    public String generatePaymentId() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT PymntId FROM Payment Order BY PymntId DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int tempId = Integer.
                    parseInt(resultSet.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "P-00" + tempId;
            } else if (tempId < 100) {
                return "P-0" + tempId;
            } else {
                return "P-" + tempId;
            }
        } else {
            return "P-001";
        }
    }

    public boolean setStudentPaymentDetails(Payment payment) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Payment VALUES(?,?,?,?)");
        preparedStatement.setObject(1,payment.getPaymentId());
        preparedStatement.setObject(2,payment.getStRegistrationNo());
        preparedStatement.setObject(3,payment.getPaymentTypeId());
        preparedStatement.setObject(4,payment.getPaymentDate());

        return preparedStatement.executeUpdate()>0;
    }

    public Payment getPayment(String paymentTypeId,String stId ) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment WHERE StRegister_No = ? && Pymnt_TypeId = ?");
        preparedStatement.setObject(1,stId);
        preparedStatement.setObject(2,paymentTypeId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Payment payment = new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            return payment;
        }
        return null;
    }

    public ObservableList<String> getPaymentDate(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT StRegister_No FROM Payment WHERE PymntDate BETWEEN ? AND ?");
        preparedStatement.setObject(1,"01/01/"+year);
        preparedStatement.setObject(2,"31/12/"+year);
        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<String> payments = FXCollections.observableArrayList();
        while (resultSet.next()){
            payments.add(resultSet.getString(1));
        }
        return payments;
    }

    public ObservableList<Payment> getPaymentDetails(String stId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment WHERE StRegister_No = ?");
        preparedStatement.setObject(1,stId);

        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Payment> paymentIds = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Payment payment = new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
               paymentIds.add(payment);
        }
        return paymentIds;
    }

    public ObservableList<String> getStudentPayments(String sId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT Pymnt_TypeId FROM Payment WHERE StRegister_No = ?");
        preparedStatement.setObject(1,sId);
        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<String> stPaymentIds = FXCollections.observableArrayList();
        while (resultSet.next()){
            stPaymentIds.add(resultSet.getString(1));
        }
            return stPaymentIds;
        }

    public Boolean deleteStudentPayments(String paymentId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Payment WHERE PymntId = ?");
        preparedStatement.setObject(1,paymentId);

        return preparedStatement.executeUpdate()>0;
    }

    public ObservableList<Payment> getAllPaymentDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment");

        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Payment> paymentIds = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Payment payment = new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            paymentIds.add(payment);
        }
        return paymentIds;
    }



}

