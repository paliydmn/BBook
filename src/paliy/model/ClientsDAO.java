package paliy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paliy.util.DBUtilSQLite;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientsDAO {

    static ObservableList<Clients> obsClientsList;


    //*******************************
    //SELECT  Clients
    //*******************************
    public static ObservableList<Clients> searchClientsResult(String searchParam) throws SQLException, ClassNotFoundException{

        String selectStmt;
        //Declare a SELECT statement
        if(searchParam.startsWith("#") || searchParam.startsWith("№")){
            selectStmt = "SELECT * FROM clients WHERE client_id='"+searchParam.substring(1,searchParam.length())+"'";
        }else {
            selectStmt = "SELECT * FROM clients WHERE LOWER (fio) Like LOWER ('%" + searchParam + "%')";
        }

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsClients = DBUtilSQLite.dbExecuteQuery(selectStmt);
            //Send ResultSet to the getBookFromResultSet method and get employee object
            return getClientsFromResultSet(rsClients);
        } catch (SQLException e) {
            System.out.println("While searching a Client with " + searchParam + " name, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }
    //Use ResultSet from DB as parameter and set Book Object's attributes and return book object.
    private static ObservableList<Clients> getClientsFromResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Clients> foundItems = FXCollections.observableArrayList();
        while (rs.next()) {
            foundItems.add(getClientsData(rs));
        }
        return foundItems;
    }

   //*******************************
    //SELECT  Clients
    //*******************************
    public static void getAllItemsFromTable() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM clients";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            //ResultSet rsClients = DBUtil.dbExecuteQuery(selectStmt);
            ResultSet rsClients = DBUtilSQLite.dbExecuteQuery(selectStmt);
            //Send ResultSet to the getBookList method and get book object
            getClientsList(rsClients);
            //Return book object
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }
    public static ObservableList<Clients> getObsClientsList() {
        return obsClientsList;
    }

    private static ObservableList<Clients> getClientsList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        obsClientsList = FXCollections.observableArrayList();
        while (rs.next()) {
            obsClientsList.add(getClientsData(rs));
        }
        return obsClientsList;
    }

    //Select fio from clients operation
    public static List<String> getClientsFioList() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT fio FROM clients";
        //Execute SELECT statement
        ResultSet rsFio;
        try {
            //Get ResultSet from dbExecuteQuery method
            //rsFio = DBUtil.dbExecuteQuery(selectStmt);
            rsFio = DBUtilSQLite.dbExecuteQuery(selectStmt);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
        //Declare a observable List which comprises of Employee objects
        List<String> fios = new ArrayList<>();
        while (rsFio.next()) {
            fios.add(getClientsFio(rsFio));
        }
        return fios;
    }

    private static String getClientsFio(ResultSet rs) throws SQLException {
        return rs.getString("FIO");
    }

    private static Clients  getClientsData(ResultSet rs)throws SQLException, ClassNotFoundException {
        Clients client = new Clients();

        client.setClientId(rs.getInt("ID"));
        client.setClientFio(rs.getString("FIO"));
        client.setClientEmail(rs.getString("EMAIL"));
        client.setClientTel(rs.getString("TEL"));
        client.setClientAddress(rs.getString("ADDRESS"));
        client.setClientFrom(rs.getString("COME_FROM"));
        client.setClientOrders(rs.getString("ORDERS"));
        client.setAddedDate(Date.valueOf(rs.getString("ADDED_DATE")));
        client.setClientNotes(rs.getString("NOTES"));

        return client;
    }


    //INSERT new Client
    //*************************************
    public static boolean insertClient(String fio, String email, String tel, String address, String from, String notes) throws SQLException, ClassNotFoundException {
        String updateStmt =
                        "INSERT INTO clients\n" +
                        "(ID, FIO, EMAIL, TEL, ADDRESS, COME_FROM, ADDED_DATE, NOTES)\n" +
                        "VALUES\n" +
                        "(null, '" + fio +
                        "','" + email +
                        "','" + tel +
                        "','" + address +
                        "','" + from +
                        "'," + " date('now'),"+
                        "'" + notes +
                        "');";

        try {
            //DBUtil.dbExecuteUpdate(updateStmt);
            DBUtilSQLite.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while Inserting Operation: " + e);
            return false;
        }
        return true;
    }

    //Search Client by Name
    //*************************************

    public static Clients getClientByFio(String fio){
        String stmt = String.format("SELECT * FROM CLIENTS WHERE fio='%s'", fio);
        try {
            //Get ResultSet from dbExecuteQuery method
            //ResultSet rs = DBUtil.dbExecuteQuery(stmt);
            ResultSet rs = DBUtilSQLite.dbExecuteQuery(stmt);
            //Send ResultSet to the getBookList method and get book object
            //in order to set the right cursor  position
            rs.next();
         return getClientsData(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteClientByName(List<String> cNameList) {
        StringBuilder names = new StringBuilder(cNameList.size());
        for(String s : cNameList){
            names.append("'").append(s).append("',");
        }
        String updateStmt =
                "BEGIN\n" +
                        "   DELETE FROM clients\n" +
                        "         WHERE fio in ("+ names.deleteCharAt(names.length() -1)+");\n" +
                        "   COMMIT;\n" +
                        "END;";
        //Execute UPDATE operation
        try {
            //DBUtil.dbExecuteUpdate(updateStmt);
            DBUtilSQLite.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Clients Operation: " + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
