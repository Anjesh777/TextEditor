package texteditorjava;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class texteditor extends JFrame implements ActionListener{

    JTextArea textArea;
    JScrollPane scrollpane;
    JSpinner fontsizespinner;
    JLabel Fontlabel ;
    JButton fontcolor;
    JComboBox fontbox;

    JMenuBar menubar;
    JMenu filemenu;
    JMenuItem opMenuItem;
    JMenuItem savMenuItem;
    JMenuItem closMenuItem;

    
    texteditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());
        this.setTitle("Text editor");
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Aerial", Font.PLAIN, 25));

        scrollpane = new JScrollPane(textArea);
        scrollpane.setPreferredSize(new Dimension(450, 400));
        scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        Fontlabel= new JLabel("font");
        this.add(Fontlabel);

        fontsizespinner = new JSpinner();
        fontsizespinner.setPreferredSize(new Dimension(50,25));
        fontsizespinner.setValue(20);
        fontsizespinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontsizespinner.getValue()));
            }
            
        });

        fontcolor = new JButton("COLOR");
        fontcolor.addActionListener(this);


        String [] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontbox = new JComboBox<>(fonts);
        fontbox.addActionListener(this);
        fontbox.setSelectedItem("Arial");

        menubar = new JMenuBar();
        filemenu = new JMenu("File");
        opMenuItem = new JMenuItem("Open");
        savMenuItem= new JMenuItem("Save");
        closMenuItem = new JMenuItem("Close");
        
        filemenu.add(opMenuItem);
        filemenu.add(savMenuItem);
        filemenu.add(closMenuItem);
        menubar.add(filemenu);

        opMenuItem.addActionListener(this);
        closMenuItem.addActionListener(this);
        savMenuItem.addActionListener(this);
        
        this.setJMenuBar(menubar);
        this.add(fontcolor);
        this.add(fontsizespinner);
        this.add(fontbox);
        this.add(scrollpane);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fontcolor){
            JColorChooser colorchoser = new JColorChooser();
            Color color = colorchoser.showDialog(null, "Choose color", Color.black);
            textArea.setForeground(color);

        }

        if(e.getSource()==fontbox){
            textArea.setFont(new Font((String)fontbox.getSelectedItem(), Font.PLAIN,textArea.getFont().getSize()));
        }

        if(e.getSource()== opMenuItem){
            JFileChooser filechooser = new JFileChooser();
            filechooser.setCurrentDirectory(new File("."));

            int resposed = filechooser.showOpenDialog(null);
            if(resposed == filechooser.APPROVE_OPTION){
                File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                Scanner filein = null;
                try {
                    filein = new Scanner(file);
                    if(file.isFile()){
                        while(filein.hasNextLine()){
                            String line = filein.nextLine()+"\n";
                            textArea.append(line);
                        
                        }
                    }

                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finally{
                    filein.close();
                }
            }

        }
        if(e.getSource()== closMenuItem){
            System.exit(0);

        }
        if(e.getSource()== savMenuItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int resposed = fileChooser.showSaveDialog(fileChooser);

            if(resposed == 0){
                File file;
                PrintWriter fileout=null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileout = new PrintWriter(file);
                    fileout.println(textArea.getText());
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finally{
                    fileout.close();
                }
            }

        }

    }

        
        
    
    


}
