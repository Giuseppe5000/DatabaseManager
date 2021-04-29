/*
* @author Giuseppe Tutino
* @version 1.1
* @java-version openjdk15
* @sqlite-verision 3.34.0
* * */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

public class Main
{
        public static JFrame f  = new JFrame("Database Manager");

        public static JScrollPane scroll;
        public static JTable table;

        public static JButton btnAggiungi = new JButton("Aggiungi");
        public static JButton btnModifica = new JButton("Modifica");
        public static JButton btnCerca = new JButton("Cerca");
        public static JButton btnElimina = new JButton("Elimina");

        public static JPanel p = new JPanel();
        
        public static File file;

        public static void main(String[] args)
        {
                SplashScreen.Loading();

                // Init Tabella
                DefaultTableModel model = new DefaultTableModel();
                table = new JTable(model);
                table.setDefaultEditor(Object.class, null);
                table.getTableHeader().setReorderingAllowed(false);
                scroll = new JScrollPane(table);

                // Button style
                btnAggiungi.setBackground(Color.GRAY);
                btnModifica.setBackground(Color.GRAY);
                btnCerca.setBackground(Color.GRAY);
                btnElimina.setBackground(Color.GRAY);

                btnAggiungi.setFont(new Font("Arial", Font.PLAIN, 16));
                btnModifica.setFont(new Font("Arial", Font.PLAIN, 16));
                btnCerca.setFont(new Font("Arial", Font.PLAIN, 16));
                btnElimina.setFont(new Font("Arial", Font.PLAIN, 16));

                // Panel style
                p.setBackground(Color.LIGHT_GRAY);

                // Table style
                table.setBackground(Color.LIGHT_GRAY);

                //MenuBar
                JMenuBar menuBar;
                JMenu menu;
                JMenuItem openFile, closeFile, savefile;

                menuBar = new JMenuBar();

                //Menu "File"
                menu = new JMenu("File");
                menu.setMnemonic(KeyEvent.VK_F);
                menuBar.add(menu);
                
                openFile = new JMenuItem("Apri");
                openFile.setMnemonic(KeyEvent.VK_O);
                
                closeFile = new JMenuItem("Chiudi");
                closeFile.setMnemonic(KeyEvent.VK_X);

                savefile = new JMenuItem("Salva come csv");
                savefile.setMnemonic(KeyEvent.VK_S);
                
                menu.add(openFile);
                menu.add(closeFile);
                menu.add(savefile);
                
                // Eventi menu
                openFile.addActionListener(e ->{
                	JFileChooser filecMenu = new JFileChooser();
                	filecMenu.setCurrentDirectory(new File("."));
                	filecMenu.setFileFilter(new FileNameExtensionFilter("SQLite DataBase","db"));
                	File elencoMenu = null;
                	int returnValMenu = filecMenu.showOpenDialog(null);
                	if(returnValMenu == JFileChooser.APPROVE_OPTION)
                		elencoMenu = filecMenu.getSelectedFile();
                	assert elencoMenu != null;
                	file = elencoMenu.getAbsoluteFile();
                	
                	// Init DB
                	Reload.ReloadDB(model, file);

                });
                
                closeFile.addActionListener(e ->{
                	model.getDataVector().removeAllElements();
                	file = null;
                	table.revalidate();
                });

                savefile.addActionListener(e -> {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setCurrentDirectory(new File("."));
                        chooser.setFileFilter(new FileNameExtensionFilter("CSV files","csv"));
                        int j = chooser.showSaveDialog(f);

                        if (j == JFileChooser.APPROVE_OPTION) {

                                String url = "jdbc:sqlite:" + file;
                                Connection con;
                                String query = "SELECT * FROM Studenti;";
                                Statement stmt;

                                try {
                                        FileWriter csvOutputFile = new FileWriter(chooser.getSelectedFile());

                                        con = DriverManager.getConnection(url, "", "");
                                        stmt = con.createStatement();
                                        ResultSet rs = stmt.executeQuery(query);

                                        while (rs.next()) {
                                                Vector<String> v = new Vector<>();
                                                v.add(rs.getString(1));
                                                v.add(rs.getString(2));
                                                v.add(rs.getString(3));
                                                v.add(rs.getString(4));

                                                //Aggiungo le virgole
                                                String csvValues = String.join(",", v);

                                                //Aggiungo al file .csv
                                                csvOutputFile.write(csvValues + "\n");

                                        }
                                        csvOutputFile.close();

                                        stmt.close();
                                        con.close();

                                        JOptionPane.showMessageDialog(f, "CSV salvato con successo");


                                } catch (SQLException | IOException ex) {
                                        System.err.print("SQLException: ");
                                        System.err.println(ex.getMessage());
                                }
                        }
                });


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
                ImageIcon img = new ImageIcon("databaseimg.png");
                f.setIconImage(img.getImage());
                
                BorderLayout bordlay = new BorderLayout();
                bordlay.setHgap(6);
                bordlay.setVgap(6);
                f.setJMenuBar(menuBar);
                f.setLayout(bordlay);
                f.add(scroll,BorderLayout.CENTER);
                f.add(p,BorderLayout.WEST);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.setSize(800,500);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - f.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - f.getHeight()) / 2);
                f.setLocation(x, y);
                f.setVisible(true);


                // CRUD Buttons
                btnAggiungi.addActionListener(e -> Aggiungi.AggiungiRow(model,file));
                btnModifica.addActionListener(e -> Modifica.ModificaRow(model,table,file));
                btnCerca.addActionListener(e -> Cerca.CercaRow(model,file));
                btnElimina.addActionListener(e -> Elimina.EliminaRow(model,table,file));

        }


}
