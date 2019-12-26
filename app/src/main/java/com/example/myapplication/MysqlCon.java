package com.example.myapplication;

import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlCon {

    // 資料庫定義
    String mysql_ip = "34.80.184.16";
    int mysql_port = 3306; // Port 預設為 3306
    String db_name = "bank";
    String url = "jdbc:mysql://34.80.184.16:3306/bank";
    String db_user = "weija";
    String db_password = "0000";

    public void run() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.v("DB","加載驅動成功");
        }catch( ClassNotFoundException e) {
            Log.e("DB","加載驅動失敗");
            return;
        }

        // 連接資料庫
        try {
            Connection con = DriverManager.getConnection(url,db_user,db_password);
            Log.v("DB","遠端連接成功");
        }catch(SQLException e) {
            Log.e("DB","遠端連接失敗");
            Log.e("DB", e.toString());
        }
    }

    public String getData(String coor) {
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT * FROM account WHERE coor_account="+coor;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String account = rs.getString("account");
                String deposit = rs.getString("deposit");
                String pssn = rs.getString("Pssn");
                data += account +  "  "+ deposit +"\n";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public boolean login(String coor,String pass){
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT * FROM bankcoordinator ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String password = rs.getString("password");
                String name = rs.getString("coor_account");
                if(name.equals(coor) && password.equals(pass)){
                    return true;
                }
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public String showForeignExchangeCountry(){
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT country FROM foreignexchange";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String country = rs.getString("country");
//                String forex = rs.getString("forex");
                data += country  +"\n";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public String showForeignExchangeForex(){
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT forex FROM foreignexchange";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
//                String country = rs.getString("country");
                String forex = rs.getString("forex");
                data += forex  +"\n";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public void setForeignExchange(String coor, String country, float forex){
        int mode=0;
            try {  /* 查詢是否有國家資料*/
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT country FROM foreignexchange ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String countrySQL = rs.getString("country");
                if(country.equals(countrySQL)){
                    mode =1;
                }
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(mode == 1){     /* 1 原本就有國家資料 更改匯率*/
            try {
                Connection con = DriverManager.getConnection(url, db_user, db_password);
                String sql = "UPDATE foreignexchange set `forex` = '"+ forex +"' WHERE country= '"+country+"'";
                String sql2 = "UPDATE updateforeignexchange set `coor_account` = '"+ coor +"' WHERE country= '"+country+"'";
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                st.executeUpdate(sql2);
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            try {
//                Connection con = DriverManager.getConnection(url, db_user, db_password);
//                String sql = "UPDATE updateforeignexchange set `coor_account` = '"+ coor +"' WHERE country= '"+country+"'";
//                Statement st = con.createStatement();
//                st.executeUpdate(sql);
//                st.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }else{   /*0 無國家資料 新增匯率*/
            try {
                Connection con = DriverManager.getConnection(url, db_user, db_password);
//                String noKey = "ALTER TABLE updateforeignexchange  DROP FOREIGN KEY `updateforeignexchange_ibfk_1`";
                String sql = "INSERT into foreignexchange(`country`,`forex`) values('"+ country + "','"+ forex +"')";
                String sql2 = "INSERT into updateforeignexchange(`coor_account`,`country`) values('"+ coor + "','"+ country +"')";
//                String addFK = "ALTER TABLE `updateforeignexchange` ADD CONSTRAINT updateforeignexchange_ibfk_1 FOREIGN KEY (country) REFERENCES foreignexchange(country)";
                Statement st = con.createStatement();
//                st.executeUpdate(noKey);
                st.executeUpdate(sql);
                st.executeUpdate(sql2);
//                st.executeUpdate(addFK);
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void deleteForeignExchange(String coor, String countryDel){
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
//            String noKey = "ALTER TABLE updateforeignexchange  DROP FOREIGN KEY updateforeignexchange_ibfk_1";
            String sql = "DELETE FROM foreignexchange WHERE country= '"+ countryDel +"'";
//            String sql2 = "DELETE FROM updateforeignexchange WHERE country= '"+ countryDel +"'";
//            String addFK = "ALTER TABLE `updateforeignexchange` ADD CONSTRAINT updateforeignexchange_ibfk_1 FOREIGN KEY (country) REFERENCES foreignexchange(country)";
            Statement st = con.createStatement();
//            st.executeUpdate(noKey);
            st.executeUpdate(sql);
//            st.executeUpdate(sql2);
//            st.executeUpdate(addFK);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public String showFixedDepositRate(){
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "select F.fixde_id, year, interestRate from fixeddepositrate F,rate FR,fixeddepositteriod FT where F.fixde_id = FR.fixde_id AND F.fixde_id = FT.fixde_id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String id = rs.getString("fixde_id");
                String year = rs.getString("year");
                String rate = rs.getString("interestRate");
                data += "No."+ id + "   " + year +  " year  "+ rate +"%\n";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public void setFixedDeposit(String coor, int no, int year, float rate){
        int mode=0;
        try {  /* 查詢是否有利率編號*/
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT * FROM fixeddepositrate ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                int fixde_id = rs.getInt("fixde_id");
                if(no == fixde_id){
                    mode =1;
                }
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(mode == 1){     /* 1 原本就有編號 更改年份和利率*/
            try {
                Connection con = DriverManager.getConnection(url, db_user, db_password);
                String sql = "UPDATE fixeddepositteriod set `year` = '"+ year +"' WHERE fixde_id= '"+ no +"'";
                String sql2 = "UPDATE rate set `interestRate` = '"+ rate +"' WHERE fixde_id= '"+ no +"'";
                String sql3 = "UPDATE updatefixeddeposit set `coor_account` = '"+ coor +"' WHERE fixde_id= '"+ no +"'";
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                st.executeUpdate(sql2);
                st.executeUpdate(sql3);
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{   /*0 無國家資料 新增匯率*/
            try {
                Connection con = DriverManager.getConnection(url, db_user, db_password);
                String sql = "INSERT into fixeddepositrate(`fixde_id`) values('"+ no +"')";
                String sql2 = "INSERT into updatefixeddeposit(`fixde_id`,`coor_account`) values('"+ no + "','"+ coor +"')";
                String sql3 = "INSERT into fixeddepositteriod(`fixde_id`,`year`) values('"+ no + "','"+ year +"')";
                String sql4 = "INSERT into rate(`fixde_id`,`interestRate`) values('"+ no + "','"+ rate +"')";
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                st.executeUpdate(sql2);
                st.executeUpdate(sql3);
                st.executeUpdate(sql4);
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void deleteFixedDeposit(String coor, int no){
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
//            String noKey = "ALTER TABLE updateforeignexchange  DROP FOREIGN KEY updateforeignexchange_ibfk_1";
            String sql = "DELETE FROM fixeddepositrate WHERE fixde_id= '"+ no +"'";
//            String sql2 = "DELETE FROM updateforeignexchange WHERE country= '"+ countryDel +"'";
//            String addFK = "ALTER TABLE `updateforeignexchange` ADD CONSTRAINT updateforeignexchange_ibfk_1 FOREIGN KEY (country) REFERENCES foreignexchange(country)";
            Statement st = con.createStatement();
//            st.executeUpdate(noKey);
            st.executeUpdate(sql);
//            st.executeUpdate(sql2);
//            st.executeUpdate(addFK);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public String showPssnCheck(){
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "select Pssn from client where coor_account is null";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String pssn = rs.getString("Pssn");

                data += pssn+"\n";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public String showNameCheck(){
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "select name from client where coor_account is null";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String name = rs.getString("name");

                data += name +"\n";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public void deleteCheck(String pssn) {
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "DELETE FROM client WHERE Pssn= '" + pssn + "'";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addCheck(String coor, String pssn) {
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "UPDATE client set `coor_account` = '"+ coor +"' WHERE Pssn= '"+ pssn +"'";
            String sqlQQ ="select name from client where Pssn = '"+ pssn + "'";
//            String sql2 = "INSERT INTO `bank`.`account` (`account`, `password`, `deposit`, `Pssn`, `coor_account`) VALUES ('" + pssn + "', '0000', '0', '"+pssn+"', '"+coor+"')";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            ResultSet rs = st.executeQuery(sqlQQ);
            while(rs.next()){
                String name = rs.getString("name");
                data += name;
            }

            String sql2 = "INSERT INTO `bank`.`account` (`account`, `password`, `deposit`, `Pssn`, `coor_account`) VALUES ('" + data + "', '"+pssn+"', '0', '"+pssn+"', '"+coor+"')";
            st.executeUpdate(sql2);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}