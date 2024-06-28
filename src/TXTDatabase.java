import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TXTDatabase {
    private String filePath;

    public TXTDatabase(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, String> loadCarSpecifications() {
        Map<String, String> specs = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    specs.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return specs;
    }
}
