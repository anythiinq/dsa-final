import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class guiImplementation {
    private JFrame frame; // Frame to display
    private JLayeredPane layeredPane; // Layered pane for managing component layers
    private BufferedImage home; // Picture for the homepage
    private BufferedImage entry;
    
   
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
        
       
    }

    public void launchHome() {
        layeredPane.removeAll();

        displayPage(home);
        
        JButton addEntry = new JButton("");
        addEntry = setButtons(addEntry, 230, 60, 568, 415);
        JButton settings = new JButton("");
        settings = setButtons(settings, 230, 60, 586, 500);
        
        layeredPane.add(addEntry, 2);
        layeredPane.add(settings, 3);

        layeredPane.revalidate();
      
        layeredPane.repaint();
        
        addEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                journalPage();
            }
        });
         
         settings.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                System.out.println("SETTINGS");
             }
         });
         
         
        
    }

    
    
    public void journalPage() {
    	 layeredPane.removeAll();

         displayPage(entry);
         
         JButton happy = new JButton("");
         happy = setButtons(happy, 131, 109, 1200, 34);
         
         JButton anxious = new JButton("");
         anxious = setButtons(anxious, 131, 109, 1200, 201);
         
         JButton angry = new JButton("");
         angry = setButtons(angry, 131, 109, 1200, 368);
        
         
         JButton ennui = new JButton("");
         ennui = setButtons(ennui, 131, 109, 1200, 543);
         
         
         
         
         layeredPane.add(happy, 2);
         layeredPane.add(anxious, 3);
         layeredPane.add(angry, 4);
         layeredPane.add(ennui, 5);

         layeredPane.revalidate();
       
         layeredPane.repaint();
         
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
    
    public void displayPage(BufferedImage page) {
        ImageIcon imdisplay = new ImageIcon(page);

        JLabel imageLabel = new JLabel(imdisplay);
        imageLabel.setBounds(0, 0, 1366, 768);

        layeredPane.add(imageLabel, Integer.valueOf(1));
    }

    public static void main(String[] args) {
        try {
            // Load the homepage image
            BufferedImage home = ImageIO.read(new File("src/homepage.jpg"));
            BufferedImage addEntry = ImageIO.read(new File("src/addEntry.png"));
            new guiImplementation(home, addEntry);
        } catch (IOException e) {
            System.out.println("Error: Unable to load image(s).");
            e.printStackTrace();
        }
    }
}
