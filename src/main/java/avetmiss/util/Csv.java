package avetmiss.util;

import au.com.bytecode.opencsv.CSVReader;
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

	public static <T> List<T> read(InputStream is, CSVRowMapper<T> rowMapper) {
		Assert.notNull(rowMapper, "RowMapper is required");
		
		List<T> rows = new ArrayList<T>();
		Reader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			CSVReader csvReader = new CSVReader(reader);
			List<String[]> values = csvReader.readAll();

			// String values[][] = parser.getAllValues();

			if (values == null) {
				return rows;
			}

			for (int i = 0; i < values.size(); i++) {
				String[] row = values.get(i);

				if (row != null) {
					T rowToObject = rowMapper.mapRow(row, i);
					if (rowToObject != null) {
						rows.add(rowToObject);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
            Closeables.closeQuietly(reader);
		}

		return rows;
	}

	public static <T> List<T> read(String csvContent, CSVRowMapper<T> rowMapper) {
		Assert.notNull(rowMapper, "RowMapper is required");
		try {
			InputStream is = new ByteArrayInputStream(csvContent.getBytes());
			return read(is, rowMapper);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return new ArrayList<>();
	}
}
