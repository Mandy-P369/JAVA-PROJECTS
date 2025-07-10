/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wms;

import java.sql.*;
public class Connect {
    Connection con;
    Statement s1;
    ResultSet rs;
    void getConnect()
    {
        try
        {
         DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
         con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","aayush");
         s1=con.createStatement();  
        }
        catch(SQLException e)
        {
            System.out.println("Error is "+e);
        }     
    }
}
