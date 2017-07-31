/*
 * The MIT License
 * 
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., Kohsuke Kawaguchi
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package avetmiss.util.hudson;

import java.io.*;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * {@link TaskListener} that generates output into a single stream.
 * <p/>
 * <p/>
 * This object is remotable.
 *
 * @author Kohsuke Kawaguchi
 */
public class StreamTaskListener implements TaskListener, Serializable, Closeable {

    private PrintStream out;
    private Charset charset;

    /**
     * @deprecated as of 1.349
     *             The caller should use {@link #StreamTaskListener(OutputStream, Charset)} to pass in
     *             the charset and output stream separately, so that this class can handle encoding correctly,
     *             or use {@link #fromStdout()} or {@link #fromStderr()}.
     */
    @Deprecated
    public StreamTaskListener(PrintStream out) {
        this(out, null);
    }

    public StreamTaskListener(OutputStream out) {
        this(out, null);
    }

    public StreamTaskListener(OutputStream out, Charset charset) {
        try {
            if (charset == null) {
                this.out = (out instanceof PrintStream) ? (PrintStream) out : new PrintStream(out, false);
            } else {
                this.out = new PrintStream(out, false, charset.name());
            }
            this.charset = charset;
        } catch (UnsupportedEncodingException e) {
            // it's not very pretty to do this, but otherwise we'd have to touch too many call sites.
            throw new Error(e);
        }
    }

    public StreamTaskListener(File out) throws IOException {
        this(new FileOutputStream(out), null);
    }

    public static StreamTaskListener fromStdout() {
        return new StreamTaskListener(System.out, Charset.defaultCharset());
    }

    public static StreamTaskListener fromStderr() {
        return new StreamTaskListener(System.err, Charset.defaultCharset());
    }

    @Override
    public PrintStream getLogger() {
        return out;
    }

    private PrintWriter _print(String prefix, String msg) {
        out.print(prefix);
        out.println(msg);

        // the idiom in Hudson is to use the returned writer for writing stack trace,
        // so put the marker here to indicate an exception. if the stack trace isn't actually written,
        // HudsonExceptionNote.annotate recovers gracefully.

        // commented by rock:
//        try {
//            annotate(new HudsonExceptionNote());
//        } catch (IOException e) {
//            // for signature compatibility, we have to swallow this error
//        }
        return new PrintWriter(
                charset != null ? new OutputStreamWriter(out, charset) : new OutputStreamWriter(out), true);
    }

    @Override
    public PrintWriter info(String msg) {
        return _print("[INFO]:", msg);
    }

    @Override
    public PrintWriter info(String format, Object... args) {
        return info(String.format(format, args));
    }

    @Override
    public PrintWriter error(String msg) {
        return _print("[ERROR]: ", msg);
    }

    @Override
    public PrintWriter warn(String msg) {
        return _print("[WARN]: ", msg);
    }

    @Override
    public PrintWriter warn(String format, Object... args) {
        return warn(String.format(format, args));
    }

    @Override
    public PrintWriter error(String format, Object... args) {
        return error(String.format(format, args));
    }

    @Override
    public PrintWriter fatalError(String msg) {
        return _print("[FATAL]: ", msg);
    }

    @Override
    public PrintWriter fatalError(String format, Object... args) {
        return fatalError(String.format(format, args));
    }

    // commented by rock:
//    public void annotate(ConsoleNote ann) throws IOException {
//        ann.encodeTo(out);
//    }

    @Override
    public void close() throws IOException {
        out.close();
    }

    /**
     * Closes this listener and swallows any exceptions, if raised.
     *
     * @since 1.349
     */
    public void closeQuietly() {
        try {
            close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to close", e);
        }
    }

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(StreamTaskListener.class.getName());
}
