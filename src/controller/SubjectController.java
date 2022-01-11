package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectController {

    public String generateSubjectId() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT SubId FROM Subject Order BY SubId DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int tempId = Integer.
                    parseInt(resultSet.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "Sub-0" + tempId;
            } else {
                return "Out Of Limit";
            }
        } else {
            return "Sub-01";
        }
    }

    public ObservableList<Subject> getSubjectDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Subject");

        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Subject> obSubjectList = FXCollections.observableArrayList();
        while (resultSet.next()){
            Subject subjects = new Subject(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
            obSubjectList.add(subjects);
        }
        return obSubjectList;
    }

    public String getSubjectId(String subject) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT SubId FROM Subject WHERE Subject = ?");
        preparedStatement.setObject(1,subject);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return resultSet.getString(1);

        }else {
            return null;
        }
    }

    public String getSubject(String subId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT Subject FROM Subject WHERE SubId = ?");
        preparedStatement.setObject(1, subId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);

        } else {
            return null;
        }
    }

    public boolean setNewSubject(Subject subject) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Subject VALUES(?,?)");
        preparedStatement.setObject(1,subject.getSubjectId());
        preparedStatement.setObject(2,subject.getSubject());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean updateSubject(Subject subject) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("Update Subject SET Subject = ? WHERE SubId = ?");
        preparedStatement.setObject(1,subject.getSubject());
        preparedStatement.setObject(2,subject.getSubjectId());

        return preparedStatement.executeUpdate()>0;
    }
}
