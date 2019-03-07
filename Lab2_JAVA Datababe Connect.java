/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labjdbc;

import java.io.IOException;
import static java.lang.Character.UnicodeBlock.of;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jdk.nashorn.internal.ir.Statement;
import org.apache.derby.iapi.store.raw.Page;

/**
 *
 * @author lab
 */
public class DB {

    /**
     * @param args the command line arguments
     */
    public static Connection getConnection()
            throws SQLException, IOException {
        System.setProperty("jdbc.drivers",
                "org.apache.derby.jdbc.ClientDriver");
        String url = "jdbc:derby://localhost/sun-appserv-samples;create=true";
        String username = "APP";
        String password = "APP";
        return DriverManager.getConnection(url, username, password);
    }

    public void createCustTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = (Statement) cnnct.createStatement();
            stmnt.execute("CREATE TABLE CUSTOMER ( "
                    + " CustId CHAR(5) CONSTRAINT PK_CUSTOMER PRIMARY KEY, "
                    + " Name CHAR(25), Tel CHAR(10), Age INTEGER)");
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();

                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }


    
public void addRecord(String CustId, String Name, String Tel, int Age) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement
                    = "INSERT INTO CUSTOMER VALUES (?, ?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, CustId);
            pStmnt.setString(2, Name);
            pStmnt.setString(3, Tel);
            pStmnt.setInt(4, Age);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount == 0) {
                throw new SQLException("Cannot insert records!");
            }
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
    }

    void addRecord() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
