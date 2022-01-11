package controller;

import db.DbConnection;
import javafx.scene.Parent;
import model.ParentDetails;
import model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParentController {

    public boolean registerNewParent(ParentDetails parent) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Parent VALUES(?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setObject(1, parent.getPrtId());
        preparedStatement.setObject(2, parent.getStRegistrationNo());
        preparedStatement.setObject(3, parent.getPrtName());
        preparedStatement.setObject(4, parent.getPrtTitle());
        preparedStatement.setObject(5, parent.getPrtGender());
        preparedStatement.setObject(6, parent.getPrtJob());
        preparedStatement.setObject(7, parent.getPrtNIC());
        preparedStatement.setObject(8, parent.getPrtAddress());
        preparedStatement.setObject(9, parent.getPrtEmail());
        preparedStatement.setObject(10, parent.getPrtContactNo());

        return preparedStatement.executeUpdate() > 0;
    }

    public String setParentId() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT PrtId FROM Parent Order BY PrtId DESC LIMIT 1");
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

    public ParentDetails getParentDetails(String stId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Parent WHERE StRegister_No= ?");
        preparedStatement.setObject(1,stId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            ParentDetails parent = new ParentDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10)
            );
            return parent;
        }
        return null;
    }

    public boolean updateParentDetail(ParentDetails updateParent) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Parent SET PrtName = ?, PrtTitle = ?,PrtGender = ?,PrtJob = ?, PrtNIC = ?,PrtAddress = ?,PrtEmail = ?,PrtContactNo = ? WHERE PrtId = ?");
        preparedStatement.setObject(1,updateParent.getPrtName());
        preparedStatement.setObject(2,updateParent.getPrtTitle());
        preparedStatement.setObject(3,updateParent.getPrtGender());
        preparedStatement.setObject(4,updateParent.getPrtJob());
        preparedStatement.setObject(5,updateParent.getPrtNIC());
        preparedStatement.setObject(6,updateParent.getPrtAddress());
        preparedStatement.setObject(7,updateParent.getPrtEmail());
        preparedStatement.setObject(8,updateParent.getPrtContactNo());
        preparedStatement.setObject(9,updateParent.getPrtId());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean deleteParentDetails(String parentId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Parent WHERE PrtId = ?");
        preparedStatement.setObject(1,parentId);

        return preparedStatement.executeUpdate()>0;
    }
}
