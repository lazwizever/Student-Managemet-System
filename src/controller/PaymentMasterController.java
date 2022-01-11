package controller;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMasterController {

    public String getPaymentTypeId(String paymentType) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT PymntTypeId FROM `Payment Master` WHERE PymntType = ?");
        preparedStatement.setObject(1,paymentType);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return resultSet.getString(1);

        }else {
            return null;
        }
    }

    public String getPaymentAmount(String paymentTypes) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT Amout FROM `Payment Master` WHERE PymntType = ?");
        preparedStatement.setObject(1,paymentTypes);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(1);
        }else {
            return null;
        }
    }

    public String getPaymentType(String paymentId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT PymntType FROM `Payment Master` WHERE PymntTypeId = ?");
        preparedStatement.setObject(1,paymentId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return resultSet.getString(1);

        }else {
            return null;
        }
    }
}
