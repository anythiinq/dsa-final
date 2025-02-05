import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Calendar {
    private Map<String, Entry> dateToEntryMap;

    public Calendar() {
        this.dateToEntryMap = new HashMap<>();
    }

    public void addEntry(String date, Entry entry) {
        dateToEntryMap.put(date, entry);
    }

    public Entry getEntryByDate(String date) {
        return dateToEntryMap.getOrDefault(date, null);
    }

    public int getDaysInMonth() {
        LocalDate today = LocalDate.now();
        return today.lengthOfMonth(); // Correct number of days in month
    }

    public int getFirstDayOfMonth() {
        LocalDate today = LocalDate.now().withDayOfMonth(1);
        int dayOfWeek = today.getDayOfWeek().getValue(); // Returns 1 (Monday) - 7 (Sunday)

        return (dayOfWeek == 7) ? 0 : dayOfWeek; // Convert Sunday (7) to 0-based index
    }
}
