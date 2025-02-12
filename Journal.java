import java.util.HashMap;
import java.util.Map;

/*
 * Purpose: stores all the entries using a hashmap. It is a parent class to Entry.java because the journal has multiple entries, and the “id” is the date for that entry (only 1 journal entry per day)
 * 
 * Author: Sahiti Bulusu, Krystal Sun, Joy Wang
 */
public class Journal {
    private Map<String, Entry> entries;

    // constructor
    public Journal() {
        this.entries = new HashMap<>();
    }

    /**
     * purpose: Stores the entry while mapping it to its respective day.
     * 
     * @param date
     * @param entry
     */
    public void addEntry(String date, Entry entry) {
        // map
        entries.put(date, entry);
    }

    /**
     * purpose: Returns the entry from a given date. 
     * 
     * @param date
     * @return Entry
     */
    public Entry getEntryByDate(String date) {
        return entries.get(date);
    }
}
