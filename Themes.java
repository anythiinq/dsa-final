import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Themes {
    private static JLabel backgroundLabel;

    public static void setPage() {
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
        JButton teddyButton = createTransparentButton();
        teddyButton.setBounds(721, 210, 230, 60);
        page.add(teddyButton);
        teddyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBackground("theme-background.png");
            }
        });

        JButton floralButton = createTransparentButton();
        floralButton.setBounds(721, 307, 230, 60);
        page.add(floralButton);
        floralButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBackground("floral-theme-bg.png");
            }
        });

        JButton bakeryButton = createTransparentButton();
        bakeryButton.setBounds(721, 403, 230, 60);
        page.add(bakeryButton);
        bakeryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBackground("bakery-theme-bg.png");
            }
        });

        JButton holidayButton = createTransparentButton();
        holidayButton.setBounds(1020, 210, 230, 60);
        page.add(holidayButton);
        holidayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBackground("holiday-theme-bg.png");
            }
        });

        JButton gothicButton = createTransparentButton();
        gothicButton.setBounds(1020, 307, 230, 60);
        page.add(gothicButton);
        gothicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBackground("gothic-theme-bg.png");
            }
        });

        JButton plantButton = createTransparentButton();
        plantButton.setBounds(1020, 403, 230, 60);
        page.add(plantButton);
        plantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBackground("plant-theme-bg.png");
            }
        });

        page.setVisible(true);
    }

    private static void changeBackground(String imagePath) {
        backgroundLabel.setIcon(new ImageIcon(imagePath)); // update the background image
    }

    public static JButton createTransparentButton() {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    public static void main(String[] args) {
        setPage();
    }
}