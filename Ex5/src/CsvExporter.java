import java.nio.charset.StandardCharsets;

public class CsvExporter extends Exporter {
    @Override
    protected String getName() {
        return "CSV";
    }

    @Override
    protected ExportResult doExport(ExportRequest req) {
        String safeTitle = req.title != null ? escapeCsvField(req.title) : "";
        String safeBody = req.body != null ? escapeCsvField(req.body) : "";
        
        String csv = "title,body\n" + safeTitle + "," + safeBody + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
    
    private String escapeCsvField(String field) {
        if (field.contains(",") || field.contains("\n") || field.contains("\"")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}
