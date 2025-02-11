import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Themes {
    private JLabel backgroundLabel;
    private guiImplementation gui;

    public Themes(guiImplementation g) {
        gui = g;

        // background label to hold the image
        backgroundLabel = new JLabel(new ImageIcon("theme-background.png"));
        backgroundLabel.setBounds(0, 0, 1366, 768); // set size to match the frame
    }

    public void launchPage() {
        // remove all components from layeredPane
        gui.getLayeredPane().removeAll();

        gui.getLayeredPane().add(backgroundLabel, Integer.valueOf(1));

        // buttons
        JButton teddyButton = createTransparentButton("theme-background.png", "homepage.png", "addEntry.png", "calendar.png", 721, 221, 230, 60);
        gui.getLayeredPane().add(teddyButton);

        JButton floralButton = createTransparentButton("floral-theme-bg.png", "flower-homepage.png", "floral-journal-background.png", "floral-calendar-background.png", 721, 319, 230, 60);
        gui.getLayeredPane().add(floralButton);

        JButton bakeryButton = createTransparentButton("bakery-theme-bg.png", "bakery-homepage.png", "bakery-journal-background", "bakery-calendar-background.png", 721, 417, 230, 60);
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

    private void changeBackground(String themeImagePath, String homepageImagePath, String journalImagePath, String calendarImagePath) {
        backgroundLabel.setIcon(new ImageIcon(themeImagePath)); // update the background image
        gui.changeHomepageTheme(homepageImagePath);
        gui.changeJournalTheme(journalImagePath);
        gui.changeCalendarTheme(calendarImagePath);
    }

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
