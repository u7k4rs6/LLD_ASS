public abstract class Exporter {

    public final ExportResult export(ExportRequest req) {
        if (req == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        int maxLength = getMaxBodyLength();
        if (req.body != null && req.body.length() > maxLength) {
            throw new IllegalArgumentException(getName() + " cannot handle content > " + maxLength + " chars");
        }
        
        return doExport(req);
    }
    
    protected abstract ExportResult doExport(ExportRequest req);
    
    protected int getMaxBodyLength() {
        return Integer.MAX_VALUE;
    }
    
    protected abstract String getName();
}
