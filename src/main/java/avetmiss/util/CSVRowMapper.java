package avetmiss.util;

/**
 * An interface used by {@link Csv} for mapping rows of a csv file on a
 * per-row basis. Implementations of this interface perform the actual work of
 * mapping each row to a object.
 *
 */
public interface CSVRowMapper<T> {
	/**
	 * Implementations must implement this method to map each row of data in the
	 * csv file.
	 * @param line
	 *            All the values from a line.
	 * @param rowNum
	 *            rowNum the number of the current row in the csv file
	 */
	T mapRow(String[] line, int rowNum);
}
