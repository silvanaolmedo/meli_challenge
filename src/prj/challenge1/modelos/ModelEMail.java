
package prj.challenge1.modelos;

import java.sql.*;
import java.util.ArrayList;
import prj.challenge1.entidades.Email;

public class ModelEMail 
{
    private String user, password, dbName;
    private Connection connection;
    
    public ModelEMail(String dbName, String user, String password)
    {
        this.user = user;
        this.password = password;
        this.dbName = dbName;
        try {
            connection = getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private  Connection getConnection() throws Exception
    {
        String dbDriver = "com.mysql.jdbc.Driver";        
        String dbConnection = "jdbc:mysql://localhost/"+dbName;        
        Class.forName(dbDriver).newInstance();
        
        return DriverManager.getConnection(dbConnection,user,password);
    }
    
    public ArrayList<Email>  getAllEmails() 
    {
        ArrayList<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM email";
        Statement stm;
        try 
        {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            int i = 0;
            while(rs.next())
            {            
                Email e = new Email();
                e.setFecha(rs.getDate("fecha_email").toString());
                e.setFrom(rs.getString("from_email"));
                e.setSubject(rs.getString("subject_email"));   
                emails.add(e);
                System.out.println(i);
                i++;
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return emails;
    }
    
    public void insertEmail(Email email)
    {
        PreparedStatement pstm ;
        
        String sql = "INSERT INTO email VALUES (NULL, ?, ?, ?)";
        
        try 
        {
            pstm = connection.prepareStatement(sql);
            pstm.setDate(1, Date.valueOf(email.getFecha()));
            pstm.setString(2, email.getFrom());
            pstm.setString(3, email.getSubject());
            
            pstm.executeUpdate();
            
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public boolean existe(Email e) 
    {
        Statement st;
        boolean existe = false;
        String sql = "SELECT COUNT(*) as cantidad FROM email WHERE "
                    + "fecha_email='" + Date.valueOf(e.getFecha()) 
                    +"' AND from_email='" + e.getFrom() 
                    + "' AND subject_email='" + e.getSubject() +"'"
                ;
        try {
            st = connection.createStatement();   
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) 
            {                
                if (rs.getInt("cantidad") > 0) 
                    existe = true;                
            }
            rs.close();
            st.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return existe;
    }
}
