import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Journal {
    private List<Entry> entries;

    public Journal() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public List<Entry> getEntries() {
        return new ArrayList<>(entries);
    }

    public Entry getEntryByDate(String date) {
        for (Entry entry : entries) {
            if (entry.getDate().equals(date)) {
                return entry;
            }
        }
        return null;
    }
}
