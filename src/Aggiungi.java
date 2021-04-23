import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.sql.*;

public class Aggiungi
{
        public static void AggiungiRow(DefaultTableModel model, File file)
        {

                JTextField nome = new JTextField();
                JTextField cognome = new JTextField();
                JTextField classe = new JTextField();


                Object[] obj = new Object[]{
                        "Nome", nome,
                        "Cognome",cognome,
                        "Classe", classe
                };

                int i = JOptionPane.showConfirmDialog(null,obj,"Aggiungi",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);

                if(i == JOptionPane.OK_OPTION && !(nome.getText().isEmpty() && cognome.getText().isEmpty() && classe.getText().isEmpty())) {
                        String url = "jdbc:sqlite:" + file;
                        Connection con;

                        try {

                                con = DriverManager.getConnection(url,"", "");
                                PreparedStatement stmt = con.prepareStatement("INSERT INTO Studenti (Nome, Cognome, Classe) VALUES (?, ?, ?);");

                                stmt.setString(1, nome.getText());
                                stmt.setString(2, cognome.getText());
                                stmt.setString(3, classe.getText());

                                stmt.executeUpdate();

                                stmt.close();
                                con.close();
                        }
                        catch(SQLException ex) {
                                System.err.print("SQLException: ");
                                System.err.println(ex.getMessage());
                        }

                }

                Reload.ReloadDB(model,file);


        }
}
