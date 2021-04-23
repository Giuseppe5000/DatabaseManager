import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.sql.*;

public class Reload
{
        public static void ReloadDB(DefaultTableModel model, File file)
        {

                model.getDataVector().removeAllElements();

                String url = "jdbc:sqlite:" + file;
                Connection con;
                String query = 	"SELECT * FROM Studenti;";
                Statement stmt;

                try
                {

                        con = DriverManager.getConnection(url,"", "");
                        stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        while (rs.next())
                        {
                                int codice     = rs.getInt(1);
                                String nome    = rs.getString(2);
                                String cognome = rs.getString(3);
                                String classe  = rs.getString(4);

                                //Aggiungo la riga
                                model.addRow(new Object[]{codice,nome,cognome,classe});
                        }
                        stmt.close();
                        con.close();


                }
                catch(SQLException ex)
                {
                        System.err.print("SQLException: ");
                        System.err.println(ex.getMessage());
                }
        }
}
