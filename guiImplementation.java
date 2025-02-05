package dataStructuresandAlgorithms.dsaFinalProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;

public class guiImplementation {
    private JFrame frame; // Frame to display
    private JLayeredPane layeredPane; // Layered pane for managing component layers
    private BufferedImage home; // Picture for the homepage
    private BufferedImage entry;
    private  Themes currentTheme;

    
   
    public guiImplementation(BufferedImage homeP, BufferedImage addEntryP) {
        frame = new JFrame("TeddyPen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 768);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1366, 768);

        frame.setContentPane(layeredPane);
        frame.setLayout(null);

        home = homeP;
        entry = addEntryP;

        launchHome();
        frame.setVisible(true);

        currentTheme = new Themes(this);
    }

    public void launchHome() {
        layeredPane.removeAll();

        displayPage(home);
        
        JButton addEntry = new JButton("");
        addEntry = setButtons(addEntry, 230, 60, 568, 415);
        JButton calendar = new JButton("");
        calendar = setButtons(calendar, 230, 60, 568, 500);
        JButton theme = new JButton("");
        theme = setButtons(theme, 230, 60, 568, 593);
        
        layeredPane.add(addEntry, 2);
        layeredPane.add(calendar, 3);
        layeredPane.add(theme, 4);

        layeredPane.revalidate();
        layeredPane.repaint();
        
        addEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                journalPage();
            }	
        });
        
        calendar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.println("Calendar button successful");
        	}
        });
        
        theme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	layeredPane.removeAll();
                currentTheme.launchPage();
            }
        });
    }

    public void journalPage() {
        layeredPane.removeAll();
        displayPage(entry);
        layeredPane.setLayout(null); 

        //buttons
        JButton happy = setButtons(new JButton(), 131, 109, 1200, 34);
        JButton anxious = setButtons(new JButton(), 131, 109, 1200, 201);
        JButton angry = setButtons(new JButton(), 131, 109, 1200, 368);
        JButton ennui = setButtons(new JButton(), 131, 109, 1200, 543);
        JButton enter = setButtons(new JButton(), 164, 46, 986, 712);
        JButton back = setButtons(new JButton(), 164, 46, 135, 69);
        
        JLabel label = new JLabel("Enter your thoughts:");
        label.setBounds(500, 180, 300, 30);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.BLACK);
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);

        
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setForeground(Color.BLACK);
        textArea.setBackground(Color.WHITE);
        textArea.setCaretColor(Color.BLACK);
        textArea.setLineWrap(true);      
        textArea.setWrapStyleWord(true);  
        textArea.setOpaque(true);      
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(180, 215, 940, 450); 
        layeredPane.add(scrollPane, JLayeredPane.PALETTE_LAYER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        happy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.out.println("happy");
            }
        });
         
         anxious.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
           	  System.out.println("anxious");
             }
         });
   	
         angry.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
           	  System.out.println("angry");
             }
         });
         ennui.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
           	  System.out.println("ennui");
             }
         });
         
         back.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
           	 launchHome();
             }
         });
        
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 System.out.println("User input: " + textArea.getText());
            }
        });        

        layeredPane.add(happy);
        layeredPane.add(anxious);
        layeredPane.add(angry);
        layeredPane.add(ennui);
        layeredPane.add(enter);
        layeredPane.add(back);
        
        layeredPane.revalidate();
        layeredPane.repaint();
    }


    public JButton setButtons(JButton button, int width, int height, int x, int y) {
    	button.setBounds(x, y, width, height);
    	button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBorder(null);
        button.setFocusPainted(false);
        return button;
    }
    
   
    public void changeHomepageTheme(String backgroundImage) {
        try {
            home = ImageIO.read(new File(backgroundImage));
        } catch (IOException e) {
            System.out.println("Error: Unable to load image(s).");
            e.printStackTrace();
        }
        
    }

    public void changeJournalTheme(String backgroundImage) {
        try {
            home = ImageIO.read(new File(backgroundImage));
        } catch (IOException e) {
            System.out.println("Error: Unable to load image(s).");
            e.printStackTrace();
        }
    }
    
    public void displayPage(BufferedImage page) {
        ImageIcon imdisplay = new ImageIcon(page);

        JLabel imageLabel = new JLabel(imdisplay);
        imageLabel.setBounds(0, 0, 1366, 768);

        layeredPane.add(imageLabel, Integer.valueOf(1));
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }
    
    
    public static void main(String[] args) {
        try {
            // Load the homepage image
            BufferedImage home = ImageIO.read(new File("src/homepage.png"));
            BufferedImage addEntry = ImageIO.read(new File("src/addEntry.png"));
            new guiImplementation(home, addEntry);
        } catch (IOException e) {
            System.out.println("Error: Unable to load image(s).");
            e.printStackTrace();
        }
    }
}

