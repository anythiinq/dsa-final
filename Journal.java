import java.util.ArrayList;
import java.util.List;

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

    public Entry getLatestEntry() {
        if (entries.isEmpty()) return null;
        return entries.get(entries.size() - 1);
    }
}
