import java.util.HashMap;
import java.util.Map;

public class Journal {
    private Map<String, Entry> entries;

    public Journal() {
        this.entries = new HashMap<>();
    }

    public void addEntry(String date, Entry entry) {
        entries.put(date, entry);
    }

    public Entry getEntryByDate(String date) {
        return entries.get(date);
    }
}
