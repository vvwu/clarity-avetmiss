package avetmiss.util;

import avetmiss.util.ostermiller.CSVParser;
import com.google.common.io.Closeables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Csv {
	protected static Logger log = LoggerFactory.getLogger(Csv.class);
	
	public final static int COLUMN_A = 0;
	public final static int COLUMN_B = 1;
	public final static int COLUMN_C = 2;
	public final static int COLUMN_D = 3;
	public final static int COLUMN_E = 4;
	public final static int COLUMN_F = 5;
	public final static int COLUMN_G = 6;
	public final static int COLUMN_H = 7;
	public final static int COLUMN_I = 8;
	public final static int COLUMN_J = 9;
	public final static int COLUMN_K = 10;
	public final static int COLUMN_L = 11;
	public final static int COLUMN_M = 12;
	public final static int COLUMN_N = 13;
	public final static int COLUMN_O = 14;
	public final static int COLUMN_P = 15;
	public final static int COLUMN_Q = 16;
	public final static int COLUMN_R = 17;
	public final static int COLUMN_S = 18;
	public final static int COLUMN_T = 19;
	public final static int COLUMN_U = 20;
	public final static int COLUMN_V = 21;
	public final static int COLUMN_W = 22;
	public final static int COLUMN_X = 23;
	public final static int COLUMN_Y = 24;
	public final static int COLUMN_Z = 25;
	public final static int COLUMN_AA = 26;
	public final static int COLUMN_AB = 27;
	public final static int COLUMN_AC = 28;
	public final static int COLUMN_AD = 29;
	public final static int COLUMN_AE = 30;
	public final static int COLUMN_AF = 31;
	public final static int COLUMN_AG = 32;
	public final static int COLUMN_AH = 33;
	public final static int COLUMN_AI = 34;
	public final static int COLUMN_AJ = 35;
	public final static int COLUMN_AK = 36;
	public final static int COLUMN_AL = 37;
	public final static int COLUMN_AM = 38;
	public final static int COLUMN_AN = 39;
	public final static int COLUMN_AO = 40;
	public final static int COLUMN_AP = 41;
	public final static int COLUMN_AQ = 42;
	public final static int COLUMN_AR = 43;
	public final static int COLUMN_AS = 44;
	public final static int COLUMN_AT = 45;
	public final static int COLUMN_AU = 46;
	public final static int COLUMN_AV = 47;
	public final static int COLUMN_AW = 48;
	public final static int COLUMN_AX = 49;
	public final static int COLUMN_AY = 50;
	public final static int COLUMN_AZ = 51;
	
	public static List<String[]> read(File file, int numOfRowsToSkip) {
		Assert.isTrue(numOfRowsToSkip >= 0, "'numOfRowsToSkip' can not be negative");
		try {
			return read(new FileInputStream(file), numOfRowsToSkip);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	/**
	 * Read the CSV file into a list of rows. Each element in the return
	 * <code>List</code> is a String array (<code>String[]</code>). Elements in
	 * the CSV file is separated using default delimiter ",".
	 * @param filePath
	 *            full path of the CSV file
	 * @param numOfRowsToSkip
	 *            number of rows (headers) to skip, have to be positive. Specify
	 *            <code>0</code> if no skip is needed.
	 */
	public static List<String[]> read(String filePath, int numOfRowsToSkip) {
		return read(new File(filePath), numOfRowsToSkip);
	}
	
	public static List<String[]> read(InputStream is, int numOfRowsToSkip) {
		Assert.isTrue(numOfRowsToSkip >= 0, "'numOfRowsToSkip' can not be negative");
		List<String[]> rows = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			// Skip the header lines (usually table header)
			while (numOfRowsToSkip-- > 0) {
				reader.readLine();
			}

			CSVParser parser = new CSVParser(reader);
			String values[][] = parser.getAllValues();

			if (values == null) {
				return rows;
			}

			for (String[] value : values) {
				String[] row = value;
				if (row != null) {
					for (int j = 0; j < row.length; j++) {
						row[j] = escapeSql(row[j]);
					}
				}
				rows.add(row);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return rows;
	}
	
	public static <T> List<T> read(InputStream is, CSVRowMapper<T> rowMapper) {
		Assert.notNull(rowMapper, "RowMapper is required");
		
		List<T> rows = new ArrayList<T>();
		Reader reader = null;
		try {
			boolean BOM_exist = removeBOM(is);
			if(BOM_exist) {
				log.debug("The input stream has BOM and has been removed." );
			}
				
			reader = new InputStreamReader(is);
			CSVParser parser = new CSVParser(reader);
			String values[][] = parser.getAllValues();

			if (values == null) {
				return rows;
			}

			for (int i = 0; i < values.length; i++) {
				String[] row = values[i];

				T rowToObject = rowMapper.mapRow(row, i);
				if(rowToObject != null) {
					rows.add(rowToObject);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
            Closeables.closeQuietly(reader);
		}

		return rows;
	}

	public static <T> List<T> read(File file, CSVRowMapper<T> rowMapper) {
		Assert.notNull(rowMapper, "RowMapper is required");
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			return read(is, rowMapper);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return new ArrayList<>();
	}
	
	/**
	 * Remove the BOM header [0xEF, 0xBB, 0xBF] from the input stream if exist
	 * @param inputStream
	 * @return
	 * @throws java.io.IOException
	 */
	private static boolean removeBOM(InputStream inputStream) throws IOException {
		inputStream.mark( 1024 );

		int byte1 = inputStream.read();
		int byte2 = inputStream.read();
		int byte3 = inputStream.read();
		
		if(0xEF == byte1 && 0xBB == byte2 && 0xBF == byte3) {
			return true;
		}
		
		inputStream.reset();
		return false;
	}
	

	//-----------------------------------------------------------------------
    /**
     * <p>Escapes the characters in a <code>String</code> to be suitable to pass to
     * an SQL query.</p>
     *
     * <p>For example,
     * <pre>statement.executeQuery("SELECT * FROM MOVIES WHERE TITLE='" + 
     *   StringEscapeUtils.escapeSql("McHale's Navy") + 
     *   "'");</pre>
     * </p>
     *
     * <p>At present, this method only turns single-quotes into doubled single-quotes
     * (<code>"McHale's Navy"</code> => <code>"McHale''s Navy"</code>). It does not
     * handle the cases of percent (%) or underscore (_) for use in LIKE clauses.</p>
     *
     * see http://www.jguru.com/faq/view.jsp?EID=8881
     * @param str  the string to escape, may be null
     * @return a new String, escaped for SQL, <code>null</code> if null string input
     */
    public static String escapeSql(String str) {
        if (str == null) {
			return null;
		}

        str = str.replaceAll("\"", "");
        str = str.replaceAll("'", "''");
        return str;
    }
}
