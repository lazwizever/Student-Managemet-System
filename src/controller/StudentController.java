package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ParentDetails;
import model.Student;
import view.tm.StudentPaymentsTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentController {

    public String setRegistrationNo() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT StRegister_No FROM Student Order BY StRegister_No DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int tempId = Integer.
                    parseInt(resultSet.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "S-00" + tempId;
            } else if (tempId < 100) {
                return "S-0" + tempId;
            } else {
                return "S-" + tempId;
            }
        } else {
            return "S-001";
        }
    }

    public boolean registerNewStudent(Student student){
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Student VALUES(?,?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,student.getStRegisterNo());
            preparedStatement.setObject(2,student.getStName());
            preparedStatement.setObject(3,student.getStDob());
            preparedStatement.setObject(4,student.getStRegistrationDate());
            preparedStatement.setObject(5,student.getStGender());
            preparedStatement.setObject(6,student.getStGrade());
            preparedStatement.setObject(7,student.getStAddress());
            preparedStatement.setObject(8,student.getStContactNo());

            if (preparedStatement.executeUpdate()>0){
                if (new ParentController().registerNewParent(student.getParentDetails())){
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    return false;
                }
            }else {
                connection.rollback();
                return false;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public ObservableList<Student> getStudentDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student");
        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<Student> studentDetails = FXCollections.observableArrayList();
        while (resultSet.next()){
                    Student student = new Student(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            new ParentController().getParentDetails(resultSet.getString(1))
                    );
                    studentDetails.addAll(student);
        }
        return studentDetails;
    }

    public ObservableList<Student> searchStudentName(String searchText) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student INNER JOIN `Final Exam Mark` ON Student.StRegister_No = `Final Exam Mark`.StRegister_No WHERE StName LIKE ?");
        preparedStatement.setObject(1,searchText+"%");

        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Student> studentName = FXCollections.observableArrayList();
        while (resultSet.next()){
            Student student = new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    new ParentController().getParentDetails(resultSet.getString(1))
            );
            studentName.add(student);
        }
        return studentName;
    }

    public ObservableList<Student> getStudentId(String registrationNo) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student WHERE StRegister_No LIKE ?");
        preparedStatement.setObject(1,registrationNo+"%");

        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Student> studentId = FXCollections.observableArrayList();
        while (resultSet.next()){
            Student student = new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    new ParentController().getParentDetails(resultSet.getString(1))
            );
            studentId.add(student);
        }
        return studentId;
    }

    public boolean updateStudent(Student updateStudent) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Student SET StName = ?,StDOB = ?, StRegistrationDate = ?,StGender = ?,StGrade = ?,StAddress = ?,StContactNo = ? WHERE StRegister_No = ?");
        preparedStatement.setObject(1,updateStudent.getStName());
        preparedStatement.setObject(2,updateStudent.getStDob());
        preparedStatement.setObject(3,updateStudent.getStGender());
        preparedStatement.setObject(4,updateStudent.getStRegistrationDate());
        preparedStatement.setObject(5,updateStudent.getStGrade());
        preparedStatement.setObject(6,updateStudent.getStAddress());
        preparedStatement.setObject(7,updateStudent.getStContactNo());
        preparedStatement.setObject(8,updateStudent.getStRegisterNo());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean deleteStudent(String studentId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Student WHERE StRegister_No = ?");
        preparedStatement.setObject(1,studentId);

        return preparedStatement.executeUpdate()>0;
    }

    public String getPaymentTableStudentName(String stRegistrationNo) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT StName FROM Student WHERE StRegister_No = ?");
        preparedStatement.setObject(1,stRegistrationNo);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
           return (resultSet.getString(1));
        }else {
            return null;
        }
    }

    public ObservableList<Student> getAllStudentForGradeType(String grade) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student WHERE StGrade = ?");
        preparedStatement.setObject(1,grade);

        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Student> getAllStudent = FXCollections.observableArrayList();

        while (resultSet.next()){
            Student student = new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    new ParentController().getParentDetails(resultSet.getString(1))
            );
            getAllStudent.add(student);
        }
        return getAllStudent;
    }

    public Student getStudent(String sId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student WHERE StRegister_No = ?");
        preparedStatement.setObject(1,sId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            Student student = new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    new ParentController().getParentDetails(resultSet.getString(1))
            );
            return student;
        }
        return null;
    }


    //===============================Get Student Id For Student Reports Form====================================//
    public String getStudentGradeFromStId(String stId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT StGrade FROM Student WHERE StRegister_No = ?");
        preparedStatement.setObject(1,stId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
           return resultSet.getString(1);
        }
        return null;
    }

    public ObservableList<Student> getStudentName(String searchText) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student WHERE StName LIKE ?");
        preparedStatement.setObject(1,searchText+"%");

        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Student> studentName = FXCollections.observableArrayList();
        while (resultSet.next()){
            Student student = new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    new ParentController().getParentDetails(resultSet.getString(1))
            );
            studentName.add(student);
        }
        return studentName;
    }

    //==========================================================================================================//

    public boolean upgradeStudent(ArrayList<StudentPaymentsTM> selectedStudents) throws SQLException, ClassNotFoundException {
        for (StudentPaymentsTM temp: selectedStudents
             ) {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Student SET StGrade = ? WHERE StRegister_No = ? ");
            preparedStatement.setObject(1,"Upper Kindergarten");
            preparedStatement.setObject(2,temp.getStRegistrationNo());

            if (preparedStatement.executeUpdate()>0){
                continue;
            }else {
                return false;
            }
        }
        return true;
    }

    public ObservableList<Student> searchStudentNameForPayment(String searchText) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student INNER JOIN Payment ON Student.StRegister_No = payment.StRegister_No WHERE StName LIKE ?");
        preparedStatement.setObject(1,searchText+"%");

        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Student> studentName = FXCollections.observableArrayList();
        while (resultSet.next()){
            Student student = new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    new ParentController().getParentDetails(resultSet.getString(1))
            );
            studentName.add(student);
        }
        return studentName;
    }

    public int getTotalStudent(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(StRegister_No) FROM Student WHERE StRegistrationDate BETWEEN ? AND ?");
        preparedStatement.setObject(1,year+"-01-01");
        preparedStatement.setObject(2,year+"-12-31");

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

}
