import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/*
 * Purpose: stores user’s mood and journal entry for that day. It also has getter and setter methods so that the user can edit their entries for that day and it will be saved/ updated in the back-end
 * 
 * Author: Sahiti Bulusu, Krystal Sun, Joy Wang
 */
public class Entry {
    private String mood;
    private String content;
    private String date;
    private static final Map<String, String> MOOD_COLORS;

    // Maps each mood to a color for easy visualization in the calendar layout page (color coding)
    static {
        MOOD_COLORS = new HashMap<>();
        MOOD_COLORS.put("anxious", "blue");
        MOOD_COLORS.put("happy", "yellow");
        MOOD_COLORS.put("angry", "red");
        MOOD_COLORS.put("ennui", "purple");
    }

    // constructor
    public Entry(String mood, String content) {
        if (!MOOD_COLORS.containsKey(mood)) {
            throw new IllegalArgumentException("Invalid mood. Please select a valid mood: " + MOOD_COLORS.keySet());
        }
        this.mood = mood;
        this.content = content;
        this.date = LocalDate.now().toString();
    }

    // accessor method
    public String getMood() {
        return mood;
    }

    // accessor method
    public String getContent() {
        return content;
    }

    // accessor method
    public String getDate() {
        return date;
    }

    // accessor method
    public String getMoodColor() {
        return MOOD_COLORS.get(mood);
    }

    // toString method
    @Override
    public String toString() {
        return "Date: " + date + "\nMood: " + mood + " (Color: " + getMoodColor() + ")\nContent: " + content;
    }

    // accessor method
    public static Map getMOODCOLORS() {
        return MOOD_COLORS;
    }
}
