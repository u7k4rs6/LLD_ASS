import java.util.LinkedHashMap;
import java.util.Map;

public class InMemoryMenuRegistry implements MenuRegistry {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();

    @Override
    public void addToMenu(MenuItem i) {
        menu.put(i.id, i);
    }

    @Override
    public MenuItem getItem(String id) {
        return menu.get(id);
    }
}
