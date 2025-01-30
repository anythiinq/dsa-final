import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Calendar {
    private Map<String, Entry> dateToEntryMap;

    public Calendar() {
        this.dateToEntryMap = new HashMap<>();
    }

    public void addEntryToDate(String date, Entry entry) {
        dateToEntryMap.put(date, entry);
    }

    public Entry getEntryByDate(String date) {
        return dateToEntryMap.getOrDefault(date, null);
    }

    public Map<String, String> getMoodSummary() {
        Map<String, String> moodSummary = new HashMap<>();
        for (Map.Entry<String, Entry> entry : dateToEntryMap.entrySet()) {
            moodSummary.put(entry.getKey(), entry.getValue().getMood());
        }
        return moodSummary;
    }

    public void displayCalendar() {
        for (Map.Entry<String, Entry> entry : dateToEntryMap.entrySet()) {
            String date = entry.getKey();
            String moodColor = entry.getValue().getMoodColor();
            System.out.println("Date: " + date + " | Mood Color: " + moodColor);
        }
    }
}
