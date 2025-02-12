import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Purpose: To change the theme (6 themes to choose from) for Teddy Pen to what the user wants. 
 * 
 * Author: Sahiti Bulusu, Krystal Sun, Joy Wang
 */
public class Themes {
    private JLabel backgroundLabel;
    private guiImplementation gui;

    // constructor for Themes Class
    public Themes(guiImplementation g) {
        gui = g;

        // background label to hold the image
        backgroundLabel = new JLabel(new ImageIcon("theme-background.png"));
        backgroundLabel.setBounds(0, 0, 1366, 768); // set size to match the frame
    }

    /**
     * purpose: creates the 6 buttons that enables the user to choose the theme.
     * 
     * @param none
     * @return none
     */
    public void launchPage() {
        // remove all components from layeredPane
        gui.getLayeredPane().removeAll();

        gui.getLayeredPane().add(backgroundLabel, Integer.valueOf(1));

        // buttons
        JButton teddyButton = createTransparentButton("theme-background.png", "homepage.png", "addEntry.png", "calendar.png", 721, 221, 230, 60);
        gui.getLayeredPane().add(teddyButton);

        JButton floralButton = createTransparentButton("floral-theme-bg.png", "flower-homepage.png", "floral-journal-background.png", "floral-calendar-background.png", 721, 319, 230, 60);
        gui.getLayeredPane().add(floralButton);

        JButton bakeryButton = createTransparentButton("bakery-theme-bg.png", "bakery-homepage.png", "bakery-journal-background.png", "bakery-calendar-background.png", 721, 417, 230, 60);
        gui.getLayeredPane().add(bakeryButton);

        JButton holidayButton = createTransparentButton("holiday-theme-bg.png", "holiday-homepage.png", "holiday-journal-background.png", "holiday-calendar-background.png", 1020, 221, 230, 60);
        gui.getLayeredPane().add(holidayButton);

        JButton gothicButton = createTransparentButton("gothic-theme-bg.png", "gothic-homepage.png", "gothic-journal-background.png", "gothic-calendar-background.png", 1020, 319, 230, 60);
        gui.getLayeredPane().add(gothicButton);

        JButton plantButton = createTransparentButton("plant-theme-bg.png", "plant-homepage.png", "plant-journal-background.png", "plant-calendar-background.png", 1020, 417, 230, 60);
        gui.getLayeredPane().add(plantButton);

        // back button to return to home page
        JButton backButton = createTransparentButton(null, null, null, null, 568, 555, 230, 60);
        gui.getLayeredPane().add(backButton);

        // refresh the frame to display the updated panel
        gui.getLayeredPane().revalidate();
        gui.getLayeredPane().repaint();
    }

    /**
     * Purpose: to change the background of all of the pages in the app when a theme is chosen. 
     * 
     * @param themeImagePath
     * @param homepageImagePath
     * @param journalImagePath
     * @param calendarImagePath
     * 
     * @return none
     */
    private void changeBackground(String themeImagePath, String homepageImagePath, String journalImagePath, String calendarImagePath) {
        backgroundLabel.setIcon(new ImageIcon(themeImagePath)); // update the background image
        gui.changeHomepageTheme(homepageImagePath);
        gui.changeJournalTheme(journalImagePath);
        gui.changeCalendarTheme(calendarImagePath);
    }

    /**
     * Purpose: to create a layover button for the designed button. Ensures that the button allows the user to switch themes and navigate between pages.
     * 
     * @param themeImage
     * @param homepageImage
     * @param journalImage
     * @param calendarImage
     * @param x position
     * @param y position
     * @param width
     * @param height
     */
    private JButton createTransparentButton(String themeImage, String homepageImage, String journalImage, String calendarImage, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.setBounds(x, y, width, height);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (themeImage == null) {
                    gui.launchHome(); // go back to home
                } else {
                    changeBackground(themeImage, homepageImage, journalImage, calendarImage);
                }
            }
        });
        return button;
    }
}
