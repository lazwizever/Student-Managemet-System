package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FinalExamMark;
import model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinalExamMarkController {
    public Boolean setStudentMarks(FinalExamMark finalExamMark) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Final Exam Mark` VALUES(?,?,?,?)");
        preparedStatement.setObject(1,finalExamMark.getStRegistrationNo());
        preparedStatement.setObject(2,finalExamMark.getStGrade());
        preparedStatement.setObject(3,finalExamMark.getSubjectId());
        preparedStatement.setObject(4,finalExamMark.getMark());

        return preparedStatement.executeUpdate()>0;
    }

//    public ObservableList<FinalExamMark> searchStudentFromId(String text) throws SQLException, ClassNotFoundException {
//        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `FInal Exam Mark` WHERE StRegister_No LIKE ?");
//        preparedStatement.setObject(1,text);
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//        ObservableList<FinalExamMark> studentMarks = FXCollections.observableArrayList();
//
//        while (resultSet.next()){
//            FinalExamMark finalExamMark = new FinalExamMark(
//                    resultSet.getString(1),
//                    resultSet.getString(2),
//                    Integer.parseInt(resultSet.getString(3)),
//                    resultSet.getString(4)
//
//            );
//            studentMarks.add(finalExamMark);
//        }
//        return studentMarks;
//    }

    public int getStudentMarksForTbl(String sid, String subId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT StMarks FROM `Final Exam Mark` WHERE StRegister_No = ? && SubId = ?");
        preparedStatement.setObject(1,sid);
        preparedStatement.setObject(2,subId);

        ResultSet resultSet = preparedStatement.executeQuery();
      //  ObservableList<Integer> studentMarks = FXCollections.observableArrayList();

        if (resultSet.next()){
            return Integer.valueOf(resultSet.getString(1));
        }else {
            return 0;
        }
    }

    public ObservableList<FinalExamMark> getAllDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECt * FROM `Final Exam Mark`");
        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<FinalExamMark> allDetails = FXCollections.observableArrayList();
        while (resultSet.next()){
            FinalExamMark finalExamMark = new FinalExamMark(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Integer.parseInt(resultSet.getString(4))
            );
            allDetails.add(finalExamMark);
        }
        return allDetails;
    }

    public boolean updateStudentMarks(FinalExamMark finalExamMark) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Final Exam Mark` SET StMarks = ? WHERE StRegister_No = ? && SubId = ?");
        preparedStatement.setObject(1,finalExamMark.getMark());
        preparedStatement.setObject(2,finalExamMark.getStRegistrationNo());
        preparedStatement.setObject(3,finalExamMark.getSubjectId());

        return preparedStatement.executeUpdate()>0;
    }

    public ObservableList<FinalExamMark> getAllDetailsForSearchType(String stName) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECt * FROM `Final Exam Mark` WHERE StRegister_No LIKE = ?");
        preparedStatement.setObject(1,stName+"%");
        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<FinalExamMark> allDetails = FXCollections.observableArrayList();
        while (resultSet.next()){
            FinalExamMark finalExamMark = new FinalExamMark(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Integer.parseInt(resultSet.getString(4))
            );
            allDetails.add(finalExamMark);
        }
        return allDetails;
    }
}
