package avetmiss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NatFile {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String filename;
    private String content;

    public NatFile(String content, String filename) {
        this.content = content;
        this.filename = filename;
    }

    public NatFile(String filename) {
        this.filename = filename;
    }

    public NatFile withContent(String content) {
        logger.info("[{}]: \n{}", this.filename, content);

        this.content = content;
        return this;
    }

    public String content() {
        return content;
    }

    public String filename() {
        return filename;
    }
}
