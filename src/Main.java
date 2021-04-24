/*
* @author Giuseppe Tutino
* @version 1.0
* @java-version openjdk15
* @sqlite-verision 3.34.0
* * */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

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
                JMenuItem openFile, closeFile;

                menuBar = new JMenuBar();

                //Menu "File"
                menu = new JMenu("File");
                menu.setMnemonic(KeyEvent.VK_F);
                menuBar.add(menu);
                
                openFile = new JMenuItem("Apri");
                openFile.setMnemonic(KeyEvent.VK_O);
                
                closeFile = new JMenuItem("Chiudi");
                closeFile.setMnemonic(KeyEvent.VK_X);
                
                menu.add(openFile);
                menu.add(closeFile);
                
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
                f.setVisible(true);


                // CRUD Buttons
                btnAggiungi.addActionListener(e -> Aggiungi.AggiungiRow(model,file));
                btnModifica.addActionListener(e -> Modifica.ModificaRow(model,table,file));
                btnCerca.addActionListener(e -> Cerca.CercaRow(model,file));
                btnElimina.addActionListener(e -> Elimina.EliminaRow(model,table,file));

        }


}
