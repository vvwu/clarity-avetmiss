package avetmiss.util;

public class StringZipEntry {
    private String content;
    private String filename;

    public StringZipEntry(String content, String filename) {
        this.content = content;
        this.filename = filename;
    }

    public String content() {
        return content;
    }

    public String filename() {
        return filename;
    }
}
