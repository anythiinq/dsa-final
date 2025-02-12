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
import java.util.LinkedList;
import java.util.*;
import java.util.List;

/*
Class name: guiImplementation
Authors: Sahiti Bulusu, Krystal Sun, Joy Wang
Instructor: Ms. Shahin
Pre-requisites: SahitiBulusu_KrystalSun_JoyWang_FinalProject_25_ProjectGraphics

The purpose of this class is to serve as the main class for the pop-up app, Teddy Pen. Users just need to run the main method of this class to run the app.
*/

public class guiImplementation {
    private JFrame frame;
    private JLayeredPane layeredPane;
    private BufferedImage home, entry, calendarImage;
    private Journal journal;
    private Calendar calendar;
    private HashMap<String, JButton> dateButtons;
    private JLabel moodLabel;
    private Themes currentTheme;

    //constructor
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

 /*
     * launchHome() launches the homepage (for the current theme)
     * no return (void)
     */
    public void launchHome() {
        layeredPane.removeAll();
        displayPage(home); //display the homepage

        //create the invisible buttons
        JButton addEntry = setTransparentButton(new JButton(""), 230, 60, 568, 408);
        JButton calendarButton = setTransparentButton(new JButton(""), 230, 60, 568, 501);
        JButton theme = setTransparentButton(new JButton(""), 230, 60, 568, 593);

        //add the different invisible buttons
        layeredPane.add(addEntry, Integer.valueOf(2));
        layeredPane.add(calendarButton, Integer.valueOf(3));
        layeredPane.add(theme, Integer.valueOf(4));

        //update
        layeredPane.revalidate();
        layeredPane.repaint();

        //link the button to different pages and actions
        addEntry.addActionListener(e -> journalPage());
        calendarButton.addActionListener(e -> launchCalendar());
        theme.addActionListener(e -> currentTheme.launchPage());
    }
 /*
     * launchCalendar() launches the calendar page
     * no return (void)
     */
public void launchCalendar() {
    layeredPane.removeAll();
    displayPage(calendarImage); //displays the calendar page

    //create and add the back button
    JButton backButton = setTransparentButton(new JButton(""), 164, 46, 51, 667);
    layeredPane.add(backButton, Integer.valueOf(2));

    int xStart = 268;  
    int yStart = 160;  
    int buttonSize = 115;  
    int gap = 5;  
    int daysInMonth = calendar.getDaysInMonth();
    int firstDayOfMonth = calendar.getFirstDayOfMonth();

    HashMap<Integer, LinkedList<String>> weeklyMoods = new HashMap<>();

    //set the calendar and the different buttons for the date
    for (int week = 0; week < 6; week++) {
        weeklyMoods.put(week, new LinkedList<>());
    }

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

        layeredPane.add(dateButton, Integer.valueOf(3));

        Entry entry = calendar.getEntryByDate(String.valueOf(day));
        if (entry != null) {
            weeklyMoods.get(row).add(entry.getMood());
        }

        dateButtons.put(String.valueOf(day), dateButton);
    }

    // Add weekly summary buttons
    for (int week = 0; week < 6; week++) {
        JButton summaryButton = new JButton("Weekly Summary");
        summaryButton.setBounds(
            xStart + 7 * (buttonSize + gap),  // Place at the end of the row
            yStart + week * (buttonSize + gap),
            150, 
            buttonSize
        );
        summaryButton.setOpaque(true);
        summaryButton.setBackground(new Color(220, 200, 180));  // Slightly darker neutral color

        int currentWeek = week;
        summaryButton.addActionListener(e -> showWeeklySummary(currentWeek, weeklyMoods));

        layeredPane.add(summaryButton, Integer.valueOf(4));
    }

    layeredPane.revalidate();
    layeredPane.repaint();

    backButton.addActionListener(e -> launchHome());
}

 /*
     * showWeeklySummary() displays the weekly summary from the calendar in a pop-up
     * no return (void)
     */
private void showWeeklySummary(int week, HashMap<Integer, LinkedList<String>> weeklyMoods) {
    LinkedList<String> moods = weeklyMoods.get(week);

    if (moods == null || moods.isEmpty()) { //in the case where there are no moods for the week
        JOptionPane.showMessageDialog(frame, "Summary Unavailable. No entries for this week.");
        return;
    }

    // Count occurrences of each mood
    HashMap<String, Integer> moodCounts = new HashMap<>();
    for (String mood : moods) {
        moodCounts.put(mood, moodCounts.getOrDefault(mood, 0) + 1);
    }

    // Categorize moods
    int positiveCount = 0;
    int negativeCount = 0;
    String mostCommonMood = "";
    int maxCount = 0;
    boolean tie = false;

    for (Map.Entry<String, Integer> entry : moodCounts.entrySet()) {
        String mood = entry.getKey();
        int count = entry.getValue();

        if (mood.equals("happy")) {
            positiveCount += count;
        } else {
            negativeCount += count;  // Any non-happy mood is considered negative
        }

        if (count > maxCount) {
            mostCommonMood = mood;
            maxCount = count;
            tie = false;
        }
        else if (count == maxCount) {
            tie = true;  
        }
        
    }

    // Generate a personalized summary message
    String message = "";
    
    if (!tie) {
        message+=("Your most common emotion this week was ") + (mostCommonMood) + ".\n\n";
    } else {
        message+="It looks like you've had a mix of emotions this week.\n\n";
    }

    //custom message for if the week was positive or negative
    if (positiveCount > negativeCount) {
        message += "\nYou're feeling mostly positive! Keep up the great work. Here's a tip:\n";
        message += getRandomMessage("happy");
    } else if (negativeCount > positiveCount) {
        message += "\nIt looks like you've had a rough week. Here are some tips:\n";
        if (moodCounts.containsKey("anxious")) {
            message += getRandomMessage("anxious") + "\n";
        }
        if (moodCounts.containsKey("angry")) {
            message += getRandomMessage("angry") + "\n";
        }
        if (moodCounts.containsKey("ennui")) {
            message += getRandomMessage("ennui") + "\n";
        }
    } else {
        message += "\nIt looks like you had a mix of positive and negative emotions. Try focusing on self-care!\n";
        message += getRandomMessage(mostCommonMood);
    }

    JOptionPane.showMessageDialog(frame, message);
}

 /*
     * getRandomMessage() takes in the mood and returns the message to be displayed in the weekly schedule to increase modularity of the code
     * return String
     */
private String getRandomMessage(String mood) {
    Random rand = new Random();
    HashMap<String, Set<String>> moodMessages = new HashMap<>();

    //happy
    moodMessages.put("happy", new HashSet<>(Arrays.asList(
            "Keep up the positive mindset! Maybe try journaling more.",
            "Stay engaged with activities that make you happy!",
            "Surround yourself with positive people and gratitude."
    )));

    //anxious
    moodMessages.put("anxious", new HashSet<>(Arrays.asList(
            "Take deep breaths and go for a walk. Relaxation exercises might help.",
            "Try mindfulness meditation to calm your thoughts.",
            "Reduce screen time before bed to ease anxiety."
    )));

    //angry
    moodMessages.put("angry", new HashSet<>(Arrays.asList(
            "Try some calming techniques, like listening to music or meditation.",
            "Express your emotions in a healthy way—talk to someone you trust.",
            "Engage in physical activity to release built-up tension."
    )));

    //ennui
    moodMessages.put("ennui", new HashSet<>(Arrays.asList(
            "Find something exciting to do! Maybe take up a new hobby.",
            "Try breaking your routine and exploring new interests.",
            "Reflect on past moments that brought you excitement and joy."
    )));

   
    if (!moodMessages.containsKey(mood)) {
        return "Keep tracking your moods and see how they evolve over time.";
    }

    
    List<String> messageList = new ArrayList<>(moodMessages.get(mood));
    return messageList.get(rand.nextInt(messageList.size()));
}
 /*
     * journalPage() takes in no parameters and runs the journal page where users can enter in their entries
     * no return (void)
     */
public void journalPage() {
    layeredPane.removeAll();
    displayPage(entry); //display the journal page

    String today = String.valueOf(LocalDate.now().getDayOfMonth()); //organize by day
    Entry existingEntry = journal.getEntryByDate(today);

    // Mood buttons
    JButton happy = setMoodButton("happy", 131, 109, 1200, 34, moodLabel);
    JButton anxious = setMoodButton("anxious", 131, 109, 1200, 201, moodLabel);
    JButton angry = setMoodButton("angry", 131, 109, 1200, 368, moodLabel);
    JButton ennui = setMoodButton("ennui", 131, 109, 1200, 543, moodLabel);
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

    //Allows users to scroll down to view and write more for their entry
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
                launchHome();
            } else {
                System.out.println("Please select a mood and enter some text before saving.");
            }
        }
    });

    layeredPane.revalidate();
    layeredPane.repaint();

    backButton.addActionListener(e -> launchHome());
}
 /*
     * setMoodButton() takes in (mood reported, width of button, height of button, x position, y position, label to display mood)
     * alters the JLabel displaying the mood depending on the mood reported
     * no return (void)
     */
private JButton setMoodButton(String mood, int width, int height, int x, int y, JLabel moodLabel) {
    JButton button = setTransparentButton(new JButton(""), width, height, x, y);
    button.addActionListener(e -> {
        moodLabel.setText("Mood: " + mood);
        moodLabel.setForeground(new Color(102, 51, 0));
        moodLabel.setFont(new Font("Dialog", Font.BOLD, 24));
    });
    return button;
}

/*
     * setTransparentButton() takes in (button to be used, width of button, height of button, x position, y position)
     * creates a transparent button to be used on any page
     * returns the JButton
     */
    private JButton setTransparentButton(JButton button, int width, int height, int x, int y) {
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }
/*
     * displayPage() takes in (image to be displayed)
     * displays the image of the page
     * no return (void)
     */
    private void displayPage(BufferedImage page) {
        JLabel imageLabel = new JLabel(new ImageIcon(page));
        imageLabel.setBounds(0, 0, 1366, 768);
        layeredPane.add(imageLabel, Integer.valueOf(1));
    }

/*
     * getMoodColorForDate() takes in (date of the entry)
     * retrieves the mood color associated with a specific date
     * return Color
     */
    private Color getMoodColorForDate(String date) {
        Entry entry = calendar.getEntryByDate(date);
        return (entry != null) ? getMoodColor(entry.getMood()) : new Color(245, 224, 201); //returns generic color if no color for that day
    }

/*
     * getMoodColor() takes in (mood)
     * retrieves the color associated with a specific mood for increased modularity
     * return Color
     */
    private Color getMoodColor(String mood) {
        switch (mood) {
            case "happy": return Color.YELLOW;
            case "anxious": return Color.BLUE;
            case "angry": return Color.RED;
            case "ennui": return Color.MAGENTA;
            default: return new Color(245, 224, 201); //returns default color if no mood
        }
    }

/*
    * DateButtonListener class handles button clics for selecting a specific date to launch the journal page for the date
*/
    private class DateButtonListener implements ActionListener {
        private int day;

        public DateButtonListener(int day) { // Constructs a DateButtonListener for a specific day.
            this.day = day;
        }

    @Override
    public void actionPerformed(ActionEvent e) {
            launchJournalPageForDate(String.valueOf(day));
        }
    }

/*
     * launchJournalPageForData() takes in (date that the user wishes to view)
     * opens the journal page with the saved mood and entry for a certain day
     * return null (void)
     */
private void launchJournalPageForDate(String date) {
    layeredPane.removeAll();
    displayPage(entry);

    // Check if an entry exists, otherwise create a new one
    Entry existingEntry = calendar.getEntryByDate(date);
    String previousMood = (existingEntry != null) ? existingEntry.getMood() : "";
    String previousText = (existingEntry != null) ? existingEntry.getContent() : "";

    // Mood Label
    JLabel moodLabel = new JLabel("Mood: " + (previousMood.isEmpty() ? "None" : previousMood));
    moodLabel.setBounds(600, 100, 200, 120);
    moodLabel.setForeground(new Color(102, 51, 0));
    moodLabel.setFont(new Font("Dialog", Font.BOLD, 24));
    layeredPane.add(moodLabel, Integer.valueOf(2));

    // Journal Text Entry
    JLabel label = new JLabel("Enter your thoughts:");
    label.setBounds(500, 180, 300, 30);
    label.setFont(new Font("Arial", Font.BOLD, 16));
    label.setForeground(Color.BLACK);
    layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);

    JTextArea textArea = new JTextArea(previousText);
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

    // Mood Selection Buttons
    JButton happy = setMoodButton("happy", 131, 109, 1200, 34, moodLabel);
    JButton anxious = setMoodButton("anxious", 131, 109, 1200, 201, moodLabel);
    JButton angry = setMoodButton("angry", 131, 109, 1200, 368, moodLabel);
    JButton ennui = setMoodButton("ennui", 131, 109, 1200, 543, moodLabel);

    // Enter Button (Save Entry)
    JButton enter = setTransparentButton(new JButton(""), 164, 46, 986, 712);

    // Back Button
    JButton backButton = setTransparentButton(new JButton(""), 200, 70, 130, 70);

    layeredPane.add(happy, Integer.valueOf(3));
    layeredPane.add(anxious, Integer.valueOf(4));
    layeredPane.add(angry, Integer.valueOf(5));
    layeredPane.add(ennui, Integer.valueOf(6));
    layeredPane.add(enter, Integer.valueOf(7));
    layeredPane.add(backButton, Integer.valueOf(8));

    // Enter Button Action (Save Mood & Text)
    enter.addActionListener(e -> {
        String selectedMood = moodLabel.getText().replace("Mood: ", "").trim();
        String journalText = textArea.getText().trim();

        if (selectedMood.equals("None")) {
            JOptionPane.showMessageDialog(frame, "Please select a mood before saving.");
            return;
        }

        // Create a new entry or update existing one
        Entry newEntry = new Entry(selectedMood, journalText);
        journal.addEntry(date, newEntry);  // Store in Journal
        calendar.addEntry(date, newEntry); // Store in Calendar

        JOptionPane.showMessageDialog(frame, "Entry saved for " + date);
        launchCalendar(); // Return to Calendar after saving
    });

    backButton.addActionListener(e -> launchCalendar());

    layeredPane.revalidate();
    layeredPane.repaint();
}
/*
     * getLayeredPane() returns the layeredPane used to organize the different elements of the frame
     */
    public JLayeredPane getLayeredPane() {
            return layeredPane;
    }

/*
     * changeHomepageTheme() takes in (background image of the new theme)
     * changes the theme for the homepage page
     * return null (void)
     */
    public void changeHomepageTheme(String backgroundImage) {
        try {
            home = ImageIO.read(new File(backgroundImage));
        } catch (IOException e) {
            System.out.println("Error: Unable to load image(s).");
            e.printStackTrace();
        }
        
    }
/*
     * changeJournalTheme() takes in (background image of the new theme)
     * changes the theme for the journal page
     * return null (void)
     */
    public void changeJournalTheme(String backgroundImage) {
        try {
            entry = ImageIO.read(new File(backgroundImage));
        } catch (IOException e) {
            System.out.println("Error: Unable to load image(s).");
            e.printStackTrace();
        }
    }
/*
     * changeCalendarTheme() takes in (background image of the new theme)
     * changes the theme for the calendar page
     * return null (void)
     */
    public void changeCalendarTheme(String backgroundImage) {
        try {
            calendarImage = ImageIO.read(new File(backgroundImage));
        } catch (IOException e) {
            System.out.println("Error: Unable to load image(s).");
        }
    }

/*
     * main method to run the app
     */
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
