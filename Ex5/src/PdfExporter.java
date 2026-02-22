import java.nio.charset.StandardCharsets;

public class PdfExporter extends Exporter {
    
    @Override
    protected int getMaxBodyLength() {
        return 20;
    }

    @Override
    protected String getName() {
        return "PDF";
    }

    @Override
    protected ExportResult doExport(ExportRequest req) {
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
