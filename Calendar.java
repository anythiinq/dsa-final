import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Purpose: Divides each entry by date. This class provides methods to add, retrieve, and process journal entries stored within the calendar structure.
 * 
 * Author: Sahiti Bulusu, Krystal Sun, Joy Wang
 */
public class Calendar {
    private Map<String, Entry> dateToEntryMap;

    // constructor
    public Calendar() {
        this.dateToEntryMap = new HashMap<>();
    }

    /**
     * Purpose: Adds a journal entry for a specific date. If an entry for the given date already exists, it will be replaced
     * 
     * @param date  The date of the entry in String format
     * @param entry The Entry object containing mood and journal text
     */
    public void addEntry(String date, Entry entry) {
        dateToEntryMap.put(date, entry);
    }

    /**
     * Purpose: Retrieves a journal entry based on the given date. If no entry exists for the given date, returns null.
     * 
     * @param date The date for which the entry is requested.
     * @return The Entry object for the given date or null if not found.
     */
    public Entry getEntryByDate(String date) {
        return dateToEntryMap.getOrDefault(date, null);
    }

    /**
     * Purpose: Gets the number of days in the current month.
     * 
     * @return int number of days in the month.
     */
    public int getDaysInMonth() {
        LocalDate today = LocalDate.now();
        return today.lengthOfMonth(); // Correct number of days in month
    }

    /**
     * Purpose: Determines the first day of the current month. 
     * 
     * @return The first day of the current month as an index (0 for Sunday, 1-6 for Monday-Saturday).
     */
    public int getFirstDayOfMonth() {
        LocalDate today = LocalDate.now().withDayOfMonth(1);
        int dayOfWeek = today.getDayOfWeek().getValue(); // Returns 1 (Monday) - 7 (Sunday)

        return (dayOfWeek == 7) ? 0 : dayOfWeek; // Convert Sunday (7) to 0-based index
    }
}
