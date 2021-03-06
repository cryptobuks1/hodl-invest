// AUTHOR: ALEX WU 4/15/2018
package com.kenny.hodlinvest.database;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.sql.Timestamp;

// We have two tables, "users_info" and "transaction_history"
// We store all user portfolio and everything user in user table, and everything transaction related is stored in transaction table
// Call method: 1) import this class, and use method. Ex: ConnectBase.deleteUser(username);

public class ConnectBase{


    public static void initDatabase() throws URISyntaxException, SQLException {
        URI dbUri = new URI("postgres://kvfviercdrwcct:c592a2faaf25a11c1641d252e7cd564c06e442480eca56cf083039b36e96d32f@ec2-54-243-239-66.compute-1.amazonaws.com:5432/d90u6fpjbk3aer");

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";


        Connection con = DriverManager.getConnection(dbUrl, username, password);
        java.util.Map map = con.getTypeMap();
        // put UDT into JDBC type map
        con.setTypeMap(map);
        con.close();
    }

    private static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI("postgres://kvfviercdrwcct:c592a2faaf25a11c1641d252e7cd564c06e442480eca56cf083039b36e96d32f@ec2-54-243-239-66.compute-1.amazonaws.com:5432/d90u6fpjbk3aer");

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        return DriverManager.getConnection(dbUrl, username, password);
    }



    private static ResultSet queryQuery(String query, boolean who) throws URISyntaxException, SQLException{
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        if(who) {
            ResultSet rs = stmt.executeQuery(query);
            con.close();
            return rs;
        }else{
            stmt.executeUpdate(query);
            con.close();
            return null;
        }
    }

    public static ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            rs = queryQuery(query, true);
        }catch(SQLException e){
            System.out.println("SQL database wrong.");
        }catch(URISyntaxException e){
            e.printStackTrace();
        }finally{
            return rs;
        }
    }


    public static void runQuery(String query){
        try{
            queryQuery(query, false);
        }catch(SQLException e){
            System.out.println("SQL database wrong.");
        }catch(Exception e){
            System.out.println("URI is syntax wrong.");
        }finally{

        }
    }

    // add new user to user table
    public static void addNewUser(
            String username,
            String password,
            String name,
            String email,
            int refer,
            double playMoney,
            double btc,
            double eth,
            double xrp,
            double bch,
            double ltc){

        String newUser = "INSERT INTO users_info VALUES(" +
                "'" + username + "'" + ", " +
                "'" + password + "'" + ", " +
                "'" + name + "'" + ", " +
                "'" + email + "'" + ", " +
                refer + ", " +
                playMoney + ", " +
                btc + ", " +
                eth + ", " +
                xrp + ", " +
                bch + ", " +
                ltc + ")";

        System.out.println(newUser);
        runQuery(newUser);
    }

    // add new transaction to transaction history
    public static void addNewTransaction(
            int transaction_id,
            String username,
            double playmoney,
            double btc,
            double eth,
            double xrp,
            double bch,
            double ltc){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String newTransaction = "INSERT INTO transaction_history VALUES(" +
                "'" + transaction_id + "'" + ", " +
                "'" + username + "'" + ", " +
                "'" + timestamp + "'" + ", " +
                playmoney + ", " +
                btc + ", " +
                eth + ", " +
                xrp + ", " +
                bch + ", " +
                ltc + ")";

        System.out.println(newTransaction);
        runQuery(newTransaction);
    }

    // Update money from referral
    public static void updateMoney(String username, String refer, String rewardPlayMoneyAmount){

        String updatedUser = "UPDATE users_info SET refer = " +
                "'" + refer + "'" +
                "playMoney = " +
                "'" + rewardPlayMoneyAmount + "'" +
                "WHERE username = " +
                "'" + username + "'";

        runQuery(updatedUser);
        System.out.println(updatedUser);
    }

    // delete all the transaction by user
    public static void deleteAllTransactionByUser(String username){
        String deleteAllTransactionByUser = "DELETE FROM transaction_history WHERE username = '" + username + "' RETURNING *;";
        runQuery(deleteAllTransactionByUser);
        System.out.println(deleteAllTransactionByUser);
    }

    // delete user from user table and also all transactions associated with user in the transaction table
    public static void deleteUser(String username){
        ConnectBase.deleteAllTransactionByUser(username);
        String deleteUser = "DELETE FROM + users_info WHERE username = ' " + username + "' RETURNING *;";
        System.out.println(deleteUser);
        runQuery(deleteUser);
    }

}
