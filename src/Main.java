/*
* @author Giuseppe Tutino
* @version 1.0
* @java-version openjdk15
* * */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class Main
{
        public static JFrame f  = new JFrame("Database");

        public static JScrollPane scroll;
        public static JTable table;

        public static JButton btnAggiungi = new JButton("Aggiungi");
        public static JButton btnModifica = new JButton("Modifica");
        public static JButton btnCerca = new JButton("Cerca");
        public static JButton btnElimina = new JButton("Elimina");

        public static JPanel p = new JPanel();

        public static void main(String[] args)
        {

                JFileChooser filec = new JFileChooser();
                filec.setCurrentDirectory(new File("."));
                File elenco = null;
                int returnVal = filec.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION)
                        elenco = filec.getSelectedFile();
                assert elenco != null;
                File file = elenco.getAbsoluteFile();


                // Init Tabella
                DefaultTableModel model = new DefaultTableModel();
                table = new JTable(model);
                scroll = new JScrollPane(table);

                //Button style
                btnAggiungi.setBackground(Color.GRAY);
                btnModifica.setBackground(Color.GRAY);
                btnCerca.setBackground(Color.GRAY);
                btnElimina.setBackground(Color.GRAY);

                btnAggiungi.setFont(new Font("Arial", Font.PLAIN, 16));
                btnModifica.setFont(new Font("Arial", Font.PLAIN, 16));
                btnCerca.setFont(new Font("Arial", Font.PLAIN, 16));
                btnElimina.setFont(new Font("Arial", Font.PLAIN, 16));

                //Panel style
                p.setBackground(Color.LIGHT_GRAY);

                //Table style
                table.setBackground(Color.LIGHT_GRAY);



                // Aggiungo le colonne
                model.addColumn("ID");
                model.addColumn("Nome");
                model.addColumn("Cognome");
                model.addColumn("Classe");


                // Pannello laterale
                GridLayout gridlay = new GridLayout(4,1);
                gridlay.setHgap(6);
                gridlay.setVgap(6);
                p.setLayout(gridlay);
                p.add(btnAggiungi);
                p.add(btnModifica);
                p.add(btnCerca);
                p.add(btnElimina);


                // JFrame
                BorderLayout bordlay = new BorderLayout();
                bordlay.setHgap(6);
                bordlay.setVgap(6);
                f.setLayout(bordlay);
                f.add(scroll,BorderLayout.CENTER);
                f.add(p,BorderLayout.WEST);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.setSize(500,500);
                f.setVisible(true);


                // Init DB
                Reload.ReloadDB(model,file);

                // CRUD Buttons
                btnAggiungi.addActionListener(e -> Aggiungi.AggiungiRow(model,file));
                btnModifica.addActionListener(e -> Modifica.ModificaRow(model,table,file));
                btnCerca.addActionListener(e -> Cerca.CercaRow(model,file));
                btnElimina.addActionListener(e -> Elimina.EliminaRow(model,table,file));

        }


}
