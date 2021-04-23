import javax.swing.*;
import java.io.File;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Modifica
{
        public static void ModificaRow(DefaultTableModel model, JTable table, File file)
        {
                if (table.getSelectedRow() != -1) {

                        // Recupero i dati della row selezionata
                        int id = (int) model.getValueAt(table.getSelectedRow(),0);
                        String nome = (String) model.getValueAt(table.getSelectedRow(),1);
                        String cognome = (String) model.getValueAt(table.getSelectedRow(),2);
                        String classe = (String) model.getValueAt(table.getSelectedRow(),3);

                        JTextField txtNome = new JTextField();
                        JTextField txtCognome = new JTextField();
                        JTextField txtClasse = new JTextField();

                        txtNome.setText(nome);
                        txtCognome.setText(cognome);
                        txtClasse.setText(classe);

                        Object[] obj = new Object[]{
                                "Nome", txtNome,
                                "Cognome",txtCognome,
                                "Classe", txtClasse
                        };

                        int i = JOptionPane.showConfirmDialog(null,obj,"Modifica",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);

                        if(i == JOptionPane.OK_OPTION && !(txtCognome.getText().isEmpty() && txtCognome.getText().isEmpty() && txtClasse.getText().isEmpty())){
                                String url = "jdbc:sqlite:" + file;
                                Connection con;

                                try {

                                        con = DriverManager.getConnection(url,"", "");
                                        PreparedStatement stmt = con.prepareStatement("UPDATE Studenti SET Nome = ?, Cognome = ?, Classe = ? WHERE ID = ?;");

                                        stmt.setString(1,txtNome.getText());
                                        stmt.setString(2,txtCognome.getText());
                                        stmt.setString(3,txtClasse.getText());
                                        stmt.setInt(4,id);

                                        stmt.executeUpdate();

                                        stmt.close();
                                        con.close();
                                }
                                catch(SQLException ex) {
                                        System.err.print("SQLException: ");
                                        System.err.println(ex.getMessage());
                                }

                                Reload.ReloadDB(model, file);
                        }


                }
        }
}
