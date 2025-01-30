import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Entry {
    private String mood;
    private String content;
    private String date;
    private static final Map<String, String> MOOD_COLORS;

    // Define valid moods and their associated colors
    static {
        MOOD_COLORS = new HashMap<>();
        MOOD_COLORS.put("anxious", "blue");
        MOOD_COLORS.put("happiness", "yellow");
        MOOD_COLORS.put("anger", "red");
        MOOD_COLORS.put("ennui", "purple");
    }

    public Entry(String mood, String content) {
        if (!MOOD_COLORS.containsKey(mood)) {
            throw new IllegalArgumentException("Invalid mood. Please select a valid mood: " + MOOD_COLORS.keySet());
        }
        this.mood = mood;
        this.content = content;
        this.date = java.time.LocalDate.now().toString(); // Automatically sets the current date
    }

    public String getMood() {
        return mood;
    }
    
    public static Map getMOODCOLORS() {
        return MOOD_COLORS;
    }

    public void setMood(String mood) {
        if (!MOOD_COLORS.containsKey(mood)) {
            throw new IllegalArgumentException("Invalid mood. Please select a valid mood: " + MOOD_COLORS.keySet());
        }
        this.mood = mood;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoodColor() {
        return MOOD_COLORS.get(mood);
    }

    @Override
    public String toString() {
        return "Date: " + date + "\nMood: " + mood + " (Color: " + getMoodColor() + ")\nContent: " + content;
    }
}
