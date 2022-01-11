package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.UserLevelMaster;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLevelMasterController {

    public String getUserLevelType(String selectLevel) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT UserLevelId FROM `User Level Master`WHERE UserLevel = ?");
        preparedStatement.setObject(1,selectLevel);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return resultSet.getString(1);

        }else {
            return null;
        }
    }

}
