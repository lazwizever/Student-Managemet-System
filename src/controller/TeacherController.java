package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherController {

    public ObservableList<Teacher> getTeacherDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Teacher");
        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<Teacher>obTeachers = FXCollections.observableArrayList();
        while (resultSet.next()){
            Teacher teacher = new Teacher(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            obTeachers.add(teacher);
        }
        return obTeachers;
    }


    public boolean addNewTeacher(Teacher teacher) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = new DbConnection().getConnection().prepareStatement("INSERT INTO Teacher VALUES(?,?,?,?,?)");
        preparedStatement.setObject(1,teacher.getTeacherId());
        preparedStatement.setObject(2,teacher.getTeacherName());
        preparedStatement.setObject(3,teacher.getUserLevelId());
        preparedStatement.setObject(4,teacher.getUserName());
        preparedStatement.setObject(5,teacher.getPassWord());

        return preparedStatement.executeUpdate()>0;
    }

    public String getTeacherId() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT TchrId FROM Teacher Order BY TchrId DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            int id = Integer.parseInt(resultSet.getString(1));
            return String.valueOf(id+1);
        }else {
            return "1";
        }

    }

}
