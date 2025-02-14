import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TextEditor extends JFrame implements ActionListener{

    private static JTextArea area;
    private static JFrame frame;
    private static int returnValue = 0;


    public TextEditor(){
        run();
    }


    
    private void run() {
        frame = new JFrame("Text Editor");

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex){
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

        area = new JTextArea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setSize(640,480);
        frame.setVisible(true);

        JMenuBar menu_main = new JMenuBar();
        JMenu menu_file = new JMenu("File");
        JMenuItem menuitem_new = new JMenuItem("New");
        JMenuItem menuitem_open = new JMenuItem("Open");
        JMenuItem menuitem_save = new JMenuItem("Save");
        JMenuItem menuitem_quit = new JMenuItem("Quit");


        menu_main.add(menu_file);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_quit);

        frame.setJMenuBar(menu_main);


    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String ingest = null;
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose Destination");;
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);


        String ae = e.getActionCommand();
        if(ae.equals("Open")){
            returnValue = jfc.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                try{
                    FileReader read = new FileReader(f);
                    try (Scanner scan = new Scanner(read)) {
                        while(scan.hasNextLine()){
                            String line = scan.nextLine() + "\n";
                            ingest = ingest + line;
                        }
                    }
                    area.setText(ingest);
                    
                }
                catch(FileNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        }

        else if(ae.equals("Save")){
            returnValue = jfc.showSaveDialog(null);

            try{
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                FileWriter out = new FileWriter(f);
                out.write(area.getText());
                out.close();
            }

            catch(FileNotFoundException ex){
                Component f = null;
                JOptionPane.showMessageDialog(f,"File not Fount.");
            }
            catch(IOException ex){
                Component f = null;
                JOptionPane.showMessageDialog(f,"Error.");
            }
        }
        
        else if(ae.equals("New")){
            area.setText(" ");

        }
            
        else if(ae.equals("Quit")){
            System.exit(0);
            
        }
        
    }
    
    
}