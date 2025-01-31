import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Themes {
    private static JLabel backgroundLabel;
    private static guiImplementation gui;

    public Themes(guiImplementation g) {
        gui = g;
    }

    public void launchPage() {
        JFrame page = new JFrame();
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        page.setSize(1366, 768);
        page.setLayout(null); // use absolute positioning
        page.setLocationRelativeTo(null);

        // background label to hold the image
        backgroundLabel = new JLabel(new ImageIcon("theme-background.png"));
        backgroundLabel.setBounds(0, 0, 1366, 768); // set size to match the frame
        page.add(backgroundLabel);

        // buttons
        JButton teddyButton = createTransparentButton("theme-background.png", 721, 210, 230, 60);
        page.add(teddyButton);
        JButton floralButton = createTransparentButton("floral-theme-bg.png", 721, 307, 230, 60);
        page.add(floralButton);
        JButton bakeryButton = createTransparentButton("bakery-theme-bg.png", 721, 403, 230, 60);
        page.add(bakeryButton);
        JButton holidayButton = createTransparentButton("holiday-theme-bg.png", 1020, 210, 230, 60);
        page.add(holidayButton);
        JButton gothicButton = createTransparentButton("gothic-theme-bg.png", 1020, 307, 230, 60);
        page.add(gothicButton);
        JButton plantButton = createTransparentButton("plant-theme-bg.png", 1020, 403, 230, 60);
        page.add(plantButton);

        // back button
        JButton backButton = createTransparentButton(null, 568, 555, 230, 60);
        page.add(backButton);

        page.setVisible(true);
    }

    private static void changeBackground(String imagePath) {
        backgroundLabel.setIcon(new ImageIcon(imagePath)); // update the background image
    }

    public static JButton createTransparentButton(String imagePath, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.setBounds(x, y, width, height);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagePath.equals(null)) {
                    gui.launchHome();
                } else {
                    changeBackground(imagePath);
                }
            }
        });
        return button;
    }
}
