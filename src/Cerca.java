import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.sql.*;
import java.util.Vector;

public class Cerca
{
        public static void CercaRow(DefaultTableModel model, File file)
        {
                JTextField nome = new JTextField();
                JTextField cognome = new JTextField();
                JTextField classe = new JTextField();


                Object[] obj = new Object[]{
                        "Nome", nome,
                        "Cognome",cognome,
                        "Classe", classe
                };

                int i = JOptionPane.showConfirmDialog(null,obj,"Cerca",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);

                if(i == JOptionPane.OK_OPTION) {

                        Vector<String> s = new Vector<>();

                        // Controllo quali campi sono vuoti
                        if (!(nome.getText().isEmpty())) {
                                s.add(" Nome = '" + nome.getText() + "' AND ");
                        }
                        if(!(cognome.getText().isEmpty())) {
                               s.add(" Cognome = '" + cognome.getText() + "' AND ");
                        }
                        if(!(classe.getText().isEmpty())) {
                               s.add(" Classe = '" + classe.getText() + "'");
                        }


                        // Aggiungo i campi pieni alla query
                        StringBuilder q = new StringBuilder("SELECT * FROM Studenti WHERE ");
                        for (int j = 0; j < s.size(); j++ ) {
                                q.append(s.elementAt(j));
                        }


                        String query = String.valueOf(q);
                        char[] c = query.toCharArray();

                        // Se è presente un AND alla fine lo tolgo
                        if (c[query.length() -4] == 'A' && c[query.length() -3] == 'N' && c[query.length() -2] == 'D') {
                                c[query.length() -4] = ' ';
                                c[query.length() -3] = ' ';
                                c[query.length() -2] = ' ';
                        }

                        query = String.valueOf(c) + ";";
                        System.out.println(query);

                        String url = "jdbc:sqlite:" + file;
                        Connection con;

                        try {

                                con = DriverManager.getConnection(url,"", "");
                                Statement stmt = con.createStatement();

                                ResultSet rs = stmt.executeQuery(query);

                                DefaultTableModel modelPanel = new DefaultTableModel();
                                JTable table = new JTable(modelPanel);
                                JScrollPane scroll = new JScrollPane(table);

                                modelPanel.addColumn("ID");
                                modelPanel.addColumn("Nome");
                                modelPanel.addColumn("Cognome");
                                modelPanel.addColumn("Classe");

                                while (rs.next())
                                {
                                        int codice = rs.getInt(1);
                                        String rsnome = rs.getString(2);
                                        String rscognome = rs.getString(3);
                                        String rsclasse = rs.getString(4);

                                        //Aggiungo la riga
                                        modelPanel.addRow(new Object[]{codice,rsnome,rscognome,rsclasse});
                                }

                                JOptionPane.showOptionDialog(null,scroll,"Risultati",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);

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
