import javax.swing.*;
import java.io.File;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Elimina
{
        public static void EliminaRow(DefaultTableModel model, JTable table, File file)
        {
    			if(file == null) {
    				return;
    			}
    			
                if (table.getSelectedRow() != -1) {

                        int id = (int) model.getValueAt(table.getSelectedRow(),0);

                        int i = JOptionPane.showConfirmDialog(null,"Stai per eliminare un elemento, sei sicuro?","Elimina",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);

                        if (i == JOptionPane.OK_OPTION){
                                String url = "jdbc:sqlite:" + file;
                                Connection con;

                                try {

                                        con = DriverManager.getConnection(url,"", "");
                                        PreparedStatement stmt = con.prepareStatement("DELETE FROM Studenti WHERE ID=?;");

                                        stmt.setInt(1,id);

                                        stmt.executeUpdate();

                                        stmt.close();
                                        con.close();
                                }
                                catch(SQLException ex) {
                                        System.err.print("SQLException: ");
                                        System.err.println(ex.getMessage());
                                }

                                Reload.ReloadDB(model,file);
                        }


                }


        }
}
