package paliy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paliy.util.DBUtilSQLite;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {
    static ObservableList<Order> obsOrdersList;

    //*******************************
    //SELECT  Clients
    //*******************************
    public static ObservableList<Order> searchOrdersResult(String searchParam) throws SQLException, ClassNotFoundException{

        String selectStmt;
        //Declare a SELECT statement
        if(searchParam.startsWith("#") || searchParam.startsWith("№")){
            selectStmt = "SELECT * FROM orders WHERE id='"+searchParam.substring(1,searchParam.length())+"'";
        }else {
            selectStmt = "SELECT * FROM orders WHERE LOWER (client) Like LOWER ('%" + searchParam + "%')";
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
    private static ObservableList<Order> getClientsFromResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Order> foundItems = FXCollections.observableArrayList();
        while (rs.next()) {
            foundItems.add(getOrdersData(rs));
        }
        return foundItems;
    }

    //*******************************
    //SELECT  Clients
    //*******************************
    public static void getAllItemsFromTable() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM orders";

        //Execute SELECT statement
        try {

            ResultSet rsClients = DBUtilSQLite.dbExecuteQuery(selectStmt);
            getOrdersList(rsClients);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }
    public static ObservableList<Order> getObsOrdersList() {
        return obsOrdersList;
    }

    private static ObservableList<Order> getOrdersList(ResultSet rs) throws SQLException, ClassNotFoundException {
        obsOrdersList = FXCollections.observableArrayList();
        while (rs.next()) {
            obsOrdersList.add(getOrdersData(rs));
        }
        return obsOrdersList;
    }

    public static Order getLastOrder(){
        try {
            getAllItemsFromTable();
        } catch (SQLException e) {
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (obsOrdersList!= null && !obsOrdersList.isEmpty())
            return obsOrdersList.get(obsOrdersList.size()-1);
        return null;
    }

    private static Order getOrdersData(ResultSet rs)throws SQLException, ClassNotFoundException {
        Order order = new Order();

        order.setId(rs.getInt("ID"));
        order.setClient_name(rs.getString("CLIENT"));
        order.setBooks(rs.getString("BOOKS"));
        order.setOrdered_via(rs.getString("ORDER_FROM"));
        order.setSend_via(rs.getString("SEND_VIA"));
        order.setPrice(rs.getInt("PRICE"));
        order.setDate(Date.valueOf(rs.getString("DATE")));
        order.setStatus(rs.getString("STATUS"));

        return order;
    }


    //INSERT new Order
    //*************************************
    public static boolean insertOrder(Order order) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "INSERT INTO orders\n" +
                        "(ID, CLIENT, BOOKS, ORDER_FROM, SEND_VIA, PRICE, DATE, STATUS )\n" +
                        "VALUES\n" +
                        "('" + order.getId() +
                        "','" + order.getClient_name() +
                        "','"  + order.getBooks() +
                        "','" + order.getOrdered_via() +
                        "','" + order.getSend_via() +
                        "','" + order.getPrice() +
                        "'," +  " date('now')" +
                        "," + null + ")";

        try {
            DBUtilSQLite.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            return false;
        }
        return true;
    }
}
