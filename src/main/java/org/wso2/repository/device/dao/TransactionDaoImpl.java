package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.Device;
import org.wso2.repository.device.model.Transaction;
import org.wso2.repository.device.util.DB;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class TransactionDaoImpl implements TransactionDao{


    public String deleteTransaction(String id) {

        String strResponse="";

        try{
            Connection connection = DB.getConnection();
            Statement statement = connection.createStatement();
            String query = "delete from transaction where t_id =" +id;

            statement.execute(query);
            strResponse="Successfully Deleted";



        }catch (SQLException e) {
            e.printStackTrace();
            strResponse="Query Not Executed";
            return strResponse;

        }finally {

            return strResponse;

        }

    }

    public Transaction getTransaction(String id)  {

        int intId = Integer.parseInt(id);
        Transaction transaction = new Transaction();
        Connection connection = null;
        try {
            connection = DB.getConnection();

        Statement statement = connection.createStatement();
        String query = "select * from transaction where t_id =" +id;
        ResultSet resultSet = statement.executeQuery(query);



        while (resultSet.next()) {
            transaction.setTransactionId(resultSet.getString("t_id"));
            transaction.setDeviceId(resultSet.getString("d_id"));
            transaction.setUserId(resultSet.getString("u_id"));
            transaction.setTransactionStatusId(resultSet.getString("ts_id"));
            transaction.setTransactionDate(resultSet.getDate("t_date"));
            transaction.setReturnDate(resultSet.getDate("t_return_date"));
            transaction.setDueDate(resultSet.getDate("t_due_date"));
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transaction;
    }

    public LinkedList<Transaction> getTransactions(UriInfo parameters){


        LinkedList<Transaction> transactionList = new LinkedList<Transaction>();

        String userId = parameters.getQueryParameters().getFirst("userId");
        String deviceId = parameters.getQueryParameters().getFirst("deviceId");
        String statusId = parameters.getQueryParameters().getFirst("statusId");
        String options = null;
        String query = "select * from transaction ";
        boolean firstPara = false;

        if (userId !=null)
        {
            options = " u_id = '" + userId +"' ";
            firstPara =true;
        }

        if (deviceId !=null)
        {
            if (firstPara==false) {
                options = " d_id = '" + deviceId + "' ";
                firstPara = true;
            }else
            {
                options = options +  " AND d_id = '" + deviceId + "' ";
            }

        }

        if (statusId !=null)
        {
            if (firstPara==false) {
                options = " ts_id = '" + statusId + "' ";
                firstPara = true;
            }else
            {
                options = options +  " AND ts_id = '" + statusId + "' ";
            }

        }
        if(firstPara)
        {
            query = query + " Where " + options;
        }

        Connection connection = null;
        try {
            connection = DB.getConnection();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Transaction transaction = new Transaction();
            transaction.setTransactionId(resultSet.getString("t_id"));
            transaction.setDeviceId(resultSet.getString("d_id"));
            transaction.setUserId(resultSet.getString("u_id"));
            transaction.setTransactionStatusId(resultSet.getString("ts_id"));
            transaction.setTransactionDate(resultSet.getDate("t_date"));
            transaction.setReturnDate(resultSet.getDate("t_return_date"));
            transaction.setDueDate(resultSet.getDate("t_due_date"));
            transactionList.add(transaction);
        }
        }
        catch (Exception e) {

        }
        return transactionList;
    }

    public String addTransaction(Transaction transaction){

        String strResponse="";

        try{

        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();

        if (transaction.getTransactionDate() ==null)
        {
            transaction.setTransactionDate(new Date());
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String query=null;

        if(transaction.getDueDate() !=null &&  transaction.getReturnDate() !=null) {
            query = "insert into  transaction(d_id,u_id,ts_id,t_date,t_return_date,t_due_date)" +
                    " values ('" + transaction.getDeviceId() + "' ,'" + transaction.getUserId() + "','" + transaction.getTransactionStatusId() + "','" +
                    dateFormat.format(transaction.getTransactionDate()) + "','" + dateFormat.format(transaction.getReturnDate())
                    + "','" + dateFormat.format(transaction.getDueDate()) + "')";
        }
        if(transaction.getDueDate() !=null &&  transaction.getReturnDate() ==null) {
            query = "insert into  transaction(d_id,u_id,ts_id,t_date,t_due_date)" +
                    " values ('" + transaction.getDeviceId() + "' ,'" + transaction.getUserId() + "','" + transaction.getTransactionStatusId() + "','" +
                    dateFormat.format(transaction.getTransactionDate()) + "','" + dateFormat.format(transaction.getDueDate()) + "')";
        }
        if(transaction.getDueDate() ==null &&  transaction.getReturnDate() !=null) {
            query = "insert into  transaction(d_id,u_id,ts_id,t_date,t_return_date)" +
                    " values ('" + transaction.getDeviceId() + "' ,'" + transaction.getUserId() + "','" + transaction.getTransactionStatusId() + "','" +
                    dateFormat.format(transaction.getTransactionDate()) + "','" + dateFormat.format(transaction.getReturnDate())
                    + "')";
        }
        if(transaction.getDueDate() ==null &&  transaction.getReturnDate() ==null) {
            query = "insert into  transaction(d_id,u_id,ts_id,t_date)" +
                    " values ('" + transaction.getDeviceId() + "' ,'" + transaction.getUserId() + "','" + transaction.getTransactionStatusId() + "','" +
                    dateFormat.format(transaction.getTransactionDate()) +  "')";
        }
        statement.execute(query);
        strResponse="Successfully Added";



        }catch (SQLException e) {
            e.printStackTrace();
            strResponse="Query Not Executed";
            return strResponse;

        }finally {

            return strResponse;

        }
    }

    public String updateTransaction(Transaction transaction, String id) {

        String strResponse="";

        try
        {
        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();

        String query =null;
        LinkedList<String> listColumns= new LinkedList<String>();
        LinkedList<String> listValues= new LinkedList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if( transaction.getDeviceId()!=null) {
            listColumns.add("d_id");
            listValues.add(transaction.getDeviceId());
        }
        if( transaction.getUserId()!=null) {
            listColumns.add("u_id");
            listValues.add(transaction.getUserId());
        }
        if( transaction.getTransactionStatusId()!=null) {
            listColumns.add("ts_id");
            listValues.add(transaction.getTransactionStatusId());
        }

        if( transaction.getTransactionDate()!=null) {
            listColumns.add("t_date");
            listValues.add(dateFormat.format(transaction.getTransactionDate()));
        }
        if( transaction.getReturnDate()!=null) {
            listColumns.add("t_return_date");
            listValues.add(dateFormat.format(transaction.getReturnDate()));
        }
        if( transaction.getDueDate()!=null) {
            listColumns.add("t_due_date");
            listValues.add(dateFormat.format(transaction.getDueDate()));
        }


        for (int x= 0;x<listColumns.size();x++) {

            if(x==0)
            {
                query = "update transaction set ";
            }

            if(x!=(listColumns.size()-1))
            {
                query = query + listColumns.get(x) + " = '";
                query = query + listValues.get(x) + "' , ";
            }
            else{
                query = query + listColumns.get(x) + " = '";
                query = query + listValues.get(x)+ "' WHERE t_id = " + id;
            }

        }

        if (listColumns.size()!=0) {
            statement.execute(query);
        }

        strResponse="Successfully Added";



    }catch (SQLException e) {
        e.printStackTrace();
        strResponse="Query Not Executed";
        return strResponse;

    }finally {

        return strResponse;

    }



    }



    public LinkedList<Transaction> getTransactionsDetail(UriInfo parameters){


        LinkedList<Transaction> transactionList = new LinkedList<Transaction>();

        String userId = parameters.getQueryParameters().getFirst("userId");
        String deviceId = parameters.getQueryParameters().getFirst("deviceId");
        String statusId = parameters.getQueryParameters().getFirst("statusId");
        String options = null;
        String query = "Select t.t_id , d.d_name as d_id , concat(concat(u.first_name , \" \"), u.last_name) as u_id , " +
                " t_date, ts_name as ts_id , t_return_date , t_due_date" +
                " from transaction t" +
                " left join device d on d.d_id = t.d_id" +
                " left join user u on u.u_id = t.u_id" +
                " left join transaction_status ts on ts.ts_id = t.ts_id";
        boolean firstPara = false;

        if (deviceId !=null)
        {
            options = " d.d_name = '" + deviceId + "' ";
            firstPara =true;
        }

       
        if (statusId !=null)
        {
            if (firstPara==false) {
                options = " ts.ts_name = '" + statusId + "' ";
                firstPara = true;
            }else
            {
                options = options +  " AND ts.ts_name = '" + statusId + "' ";
            }

        }
        if(firstPara)
        {
            query = query + " Where " + options;
        }

        Connection connection = null;
        try {
            connection = DB.getConnection();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getString("t_id"));
                transaction.setDeviceId(resultSet.getString("d_id"));
                transaction.setUserId(resultSet.getString("u_id"));
                transaction.setTransactionStatusId(resultSet.getString("ts_id"));
                transaction.setTransactionDate(resultSet.getDate("t_date"));
                transaction.setReturnDate(resultSet.getDate("t_return_date"));
                transaction.setDueDate(resultSet.getDate("t_due_date"));
                transactionList.add(transaction);
            }
        }
        catch (Exception e) {

        }
        return transactionList;
    }
}
