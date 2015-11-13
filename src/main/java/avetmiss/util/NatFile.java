package avetmiss.util;

public class NatFile {
    private String filename;
    private String content;

    public NatFile(String content, String filename) {
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
