import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

public class guiImplementation {
    private JFrame frame;
    private JLayeredPane layeredPane;
    private BufferedImage home, entry, calendarImage;
    private Journal journal;
    private Calendar calendar;
    private HashMap<String, JButton> dateButtons;
    private JLabel moodLabel;
    private Themes currentTheme;

    public guiImplementation(BufferedImage homeP, BufferedImage addEntryP, BufferedImage calendarP) {
        frame = new JFrame("TeddyPen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 768);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1366, 768);
        frame.setContentPane(layeredPane);
        frame.setLayout(null);

        home = homeP;
        entry = addEntryP;
        calendarImage = calendarP;
        journal = new Journal();
        calendar = new Calendar();
        dateButtons = new HashMap<>();
        moodLabel = new JLabel("Mood: ");
        moodLabel.setBounds(600, 300, 300, 50);
        moodLabel.setForeground(new Color(102, 51, 0));
        moodLabel.setFont(new Font("Dialog", Font.BOLD, 24));

        currentTheme = new Themes(this);

        launchHome();
        frame.setVisible(true);
    }

    public void launchHome() {
        layeredPane.removeAll();
        displayPage(home);

        JButton addEntry = setTransparentButton(new JButton(""), 230, 60, 568, 408);
        JButton calendarButton = setTransparentButton(new JButton(""), 230, 60, 568, 501);
        JButton theme = setTransparentButton(new JButton(""), 230, 60, 568, 593);

        layeredPane.add(addEntry, Integer.valueOf(2));
        layeredPane.add(calendarButton, Integer.valueOf(3));
        layeredPane.add(theme, Integer.valueOf(4));

        layeredPane.revalidate();
        layeredPane.repaint();

        addEntry.addActionListener(e -> journalPage());
        calendarButton.addActionListener(e -> launchCalendar());
        theme.addActionListener(e -> currentTheme.launchPage());
    }

    public void launchCalendar() {
        layeredPane.removeAll();
        displayPage(calendarImage);

        JButton backButton = setTransparentButton(new JButton(""), 164, 46, 51, 667);
        layeredPane.add(backButton, Integer.valueOf(2));

        int xStart = 268; 
        int yStart = 160; 
        int buttonSize = 115; 
        int gap = 5; 
        int daysInMonth = calendar.getDaysInMonth();
        int firstDayOfMonth = calendar.getFirstDayOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            int row = (day + firstDayOfMonth - 2) / 7;
            int col = (day + firstDayOfMonth - 2) % 7;

            JButton dateButton = new JButton(String.valueOf(day));
            dateButton.setBounds(
                xStart + col * (buttonSize + gap), 
                yStart + row * (buttonSize + gap), 
                buttonSize, 
                buttonSize
            );
            dateButton.setOpaque(true);
            dateButton.setBorderPainted(false);
            dateButton.setBackground(getMoodColorForDate(String.valueOf(day)));
            dateButton.addActionListener(new DateButtonListener(day));

            dateButtons.put(String.valueOf(day), dateButton);
            layeredPane.add(dateButton, Integer.valueOf(3));
        }

        layeredPane.revalidate();
        layeredPane.repaint();

        backButton.addActionListener(e -> launchHome());
    }

public void journalPage() {
    layeredPane.removeAll();
    displayPage(entry);

    String today = String.valueOf(LocalDate.now().getDayOfMonth());
    Entry existingEntry = journal.getEntryByDate(today);

    // Mood buttons
    JButton happy = setMoodButton("happy", 131, 109, 1200, 34);
    JButton anxious = setMoodButton("anxious", 131, 109, 1200, 201);
    JButton angry = setMoodButton("angry", 131, 109, 1200, 368);
    JButton ennui = setMoodButton("ennui", 131, 109, 1200, 543);
    JButton enter = setTransparentButton(new JButton(""), 164, 46, 986, 712);
    JButton backButton = setTransparentButton(new JButton(""), 200, 70, 130, 70);

    // Mood label
    moodLabel.setBounds(600, 100, 200, 120);
    layeredPane.add(moodLabel, Integer.valueOf(2));

    // Label for text entry
    JLabel label = new JLabel("Enter your thoughts:");
    label.setBounds(500, 180, 300, 30);
    label.setFont(new Font("Arial", Font.BOLD, 16));
    label.setForeground(Color.BLACK);
    layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);

    // Text entry area
    JTextArea textArea = new JTextArea();
    textArea.setFont(new Font("Arial", Font.PLAIN, 18));
    textArea.setForeground(Color.BLACK);
    textArea.setBackground(Color.WHITE);
    textArea.setCaretColor(Color.BLACK);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setOpaque(true);

    // Preload existing text if entry already exists
    if (existingEntry != null) {
        textArea.setText(existingEntry.getContent());
        moodLabel.setText("Mood: " + existingEntry.getMood()); // Load stored mood
    }

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setBounds(180, 215, 940, 450);
    layeredPane.add(scrollPane, JLayeredPane.PALETTE_LAYER);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    // Add buttons to UI
    layeredPane.add(happy, Integer.valueOf(3));
    layeredPane.add(anxious, Integer.valueOf(4));
    layeredPane.add(angry, Integer.valueOf(5));
    layeredPane.add(ennui, Integer.valueOf(6));
    layeredPane.add(backButton, Integer.valueOf(7));
    layeredPane.add(enter, Integer.valueOf(7));

    // Enter button action: save mood & text only when pressed
    enter.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String journalText = textArea.getText();
            String selectedMood = moodLabel.getText().replace("Mood: ", "").trim();

            if (!selectedMood.equals("Mood:") && !journalText.isEmpty()) {
                Entry entry = new Entry(selectedMood, journalText);
                journal.addEntry(today, entry);
                calendar.addEntry(today, entry);
                
                System.out.println("Journal Entry Saved: " + journalText);
            } else {
                System.out.println("Please select a mood and enter some text before saving.");
            }
        }
    });

    layeredPane.revalidate();
    layeredPane.repaint();

    backButton.addActionListener(e -> launchHome());
}

    private JButton setMoodButton(String mood, int width, int height, int x, int y) {
    JButton button = setTransparentButton(new JButton(""), width, height, x, y);
    button.addActionListener(e -> {
        moodLabel.setText("Mood: " + mood);
        moodLabel.setForeground(new Color(102, 51, 0));
        moodLabel.setFont(new Font("Dialog", Font.BOLD, 24));
    });
    return button;
}

    private JButton setTransparentButton(JButton button, int width, int height, int x, int y) {
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    private void displayPage(BufferedImage page) {
        JLabel imageLabel = new JLabel(new ImageIcon(page));
        imageLabel.setBounds(0, 0, 1366, 768);
        layeredPane.add(imageLabel, Integer.valueOf(1));
    }

    private Color getMoodColorForDate(String date) {
        Entry entry = calendar.getEntryByDate(date);
        return (entry != null) ? getMoodColor(entry.getMood()) : new Color(245, 224, 201);
    }

    private Color getMoodColor(String mood) {
        switch (mood) {
            case "happy": return Color.YELLOW;
            case "anxious": return Color.BLUE;
            case "angry": return Color.RED;
            case "ennui": return Color.MAGENTA;
            default: return new Color(245, 224, 201);
        }
    }

    private class DateButtonListener implements ActionListener {
        private int day;

        public DateButtonListener(int day) {
            this.day = day;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            launchJournalPageForDate(String.valueOf(day));
        }
    }

    private void launchJournalPageForDate(String date) {
        layeredPane.removeAll();
        displayPage(entry);

        Entry entry = calendar.getEntryByDate(date);
        JLabel moodLabel = new JLabel("Mood: " + (entry != null ? entry.getMood() : "No entry"));
        moodLabel.setBounds(600, 100, 200, 120);
        moodLabel.setForeground(new Color(102, 51, 0));
        moodLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        layeredPane.add(moodLabel, Integer.valueOf(2));
        
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setForeground(Color.BLACK);
        textArea.setBackground(Color.WHITE);
        textArea.setCaretColor(Color.BLACK);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(true);
    
        if (entry != null) {
            textArea.setText(entry.getContent());
        }
    
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(180, 215, 940, 450);
        layeredPane.add(scrollPane, JLayeredPane.PALETTE_LAYER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton backButton = setTransparentButton(new JButton(""), 200, 70, 130, 70);
        layeredPane.add(backButton, Integer.valueOf(3));

        layeredPane.revalidate();
        layeredPane.repaint();

        backButton.addActionListener(e -> launchCalendar());
    }
    public JLayeredPane getLayeredPane() {
            return layeredPane;
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
                entry = ImageIO.read(new File(backgroundImage));
            } catch (IOException e) {
                System.out.println("Error: Unable to load image(s).");
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
    try {
        BufferedImage home = ImageIO.read(new File("homepage.png"));
        BufferedImage entryImage = ImageIO.read(new File("addEntry.png"));
        BufferedImage calendar = ImageIO.read(new File("calendar.png"));
        guiImplementation gui = new guiImplementation(home, entryImage, calendar);
    } catch (IOException e) {
        System.out.println("Error loading images.");
        e.printStackTrace();
    }
}
}
