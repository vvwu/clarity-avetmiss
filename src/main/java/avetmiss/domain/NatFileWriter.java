package avetmiss.domain;

import avetmiss.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class NatFileWriter {
    public static final String SPACE = " ";
    public static final String CARRIAGE_RETURN = "\r\n";
    private Logger log = LoggerFactory.getLogger(this.getClass());

	private StringBuilder buffer;

	public NatFileWriter() {
		this.buffer = new StringBuilder();
	}
	
	public NatFileWriter append(List<Row> rows, String[] columnNameTable, int[] lengthTable) {
		for (Row row : rows) {
			Validate.isTrue(row.values().length == lengthTable.length);
		}

		for (Row row : rows) {
			append(row.values(), columnNameTable, lengthTable);
		}
		return this;
	}

    public NatFileWriter append(String value) {
		this.buffer.append(value);
		return this;
	}

	private NatFileWriter append(String[] rowCols, String[] columnNameTable, int[] lengthTable) {
		Validate.isTrue(rowCols.length == lengthTable.length);
		for (int i = 0; i < rowCols.length; i++) {
			append(rowCols[i], columnNameTable[i], lengthTable[i]);
		}
		// add a line break
		this.append("\r\n");
		return this;
	}

	private String trim(String value) {
		String str = StringUtil.trimString(value);
		if ("null".equalsIgnoreCase(str) || str == null)
			return "";
		return str;
	}

	void close() {
        this.buffer.append(CARRIAGE_RETURN);
		log.debug("done: " + buffer.toString());
	}

    public String closeWithAssertion(int lengthAssertionExcludingCarriageReturn) {
		String str = this.buffer.toString();
		Validate.isTrue(str.length() == lengthAssertionExcludingCarriageReturn, "Exporting " + lengthAssertionExcludingCarriageReturn + " but was " + str.length());
		this.close();
        return str;
	}

	private NatFileWriter append(String value, String columnName, int length) {
		String str = trim(value);
		Validate.isTrue(str.length() <= length, format("length of the value '%s' in column '%s' must not be greater than %s", value, columnName, length));
		String valueWithPadding = StringUtils.rightPad(str, length, SPACE);

		this.buffer.append(valueWithPadding);
		return this;
	}

	public String close(int lengthAssertionExcludingCarriageReturn) {
		return closeWithAssertion(lengthAssertionExcludingCarriageReturn);
	}
}
