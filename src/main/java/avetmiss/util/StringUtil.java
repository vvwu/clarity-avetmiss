package avetmiss.util;

/**
 * Collection of string utilities.
 * 
 *
 * Created on May 6, 2009
 */
public class StringUtil {

	/**
	 * The empty String <code>""</code>.
	 */
	public static final String EMPTY = "";
	
	/**
	 * The single space String <code>" "</code>.
	 */
	public static final String SPACE = " ";

	/**
	 * Trim a string. Removes leading and trailing blanks. If the resulting
	 * string is empty, normalizes the string to an null string.
	 * @param str
	 *            the string to trim
	 * @return the trimmed string, or null if the string is empty
	 */

	public static String trimString(String str) {
		if (str == null)
			return null;
		str = str.trim();
		if (str.length() == 0)
			return null;
		return str;
	}

	/**
	 * Trim a string, returns "" if the given string is a null
	 * @param str
	 * @return
	 */
	public static String nullSafeTrimString(String str) {
		if (str == null)
			return "";
		return str = str.trim();
	}

	/**
	 * Returns if the two string are null or equal. The
	 * {@link java.lang.String#equalsIgnoreCase(String)}is used to compare two
	 * strings.
	 *
	 * @param str1
	 *            the string to compare
	 * @param str2
	 *            the string to compare
	 * @return true, if the two string are null, or the two string are equal
	 *         with case sensitive.
	 */

	public static boolean isEqualIgnoreCase(String str1, String str2) {
		return str1 == str2 || (str1 != null && str1.equalsIgnoreCase(str2));
	}

	/**
	 * Reports if a string is blank. A string is considered blank either if it
	 * is null, is an empty string, of consists entirely of white space.
	 * <p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null) = true
	 * StringUtils.isBlank(&quot;&quot;) = true
	 * StringUtils.isBlank(&quot; &quot;) = true
	 * StringUtils.isBlank(&quot;Hello&quot;) = false
	 * </pre>
	 * @param str
	 *            the string to check
	 * @return true if the string is blank, false otherwise.
	 */

	public static boolean isBlank(String str) {
		return trimString(str) == null;
	}

	/**
	 * Return <code>true</code> if the given string is not blank.
	 * {@link #isBlank(String)}
	 * @param str
	 * @return Return <code>true</code> if the given string is not blank.
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

    public static boolean hasLength(String str) {
        return !isBlank(str);
    }

	/**
	 * Tests if this string ends with the second string.
	 */
	public static boolean endsWith(String str, String end) {
		if (isEmpty(end)) {
			return true;
		} else if (isEmpty(str)) {
			return false;
		}
		int stringLen = str.length();
		int endLen = end.length();
		if (endLen > stringLen) {
			return false;
		}

		return end.equals(str.substring(stringLen - endLen, stringLen));
	}

	/**
	 * Reports if a string is empty. A string is considered empty either if it
	 * is null, is an empty string("").
	 * <p>
	 * For example,
	 * <ul>
	 * <li>Both null and "" are empty strings
	 * <li>" " is not empty string.
	 * </ul>
	 *
	 * @param value
	 *            the string to check
	 * @return true if the string is empty, false otherwise.
	 */

	public static boolean isEmpty(String value) {
		return value == null || value.length() == 0;
	}

	/**
	 * Parse a string to an integer value, return <code>null</code> if the given
	 * string is not in a correct integer representation.
	 *
	 * @param str
	 * @return an Integer value of the given string representation or
	 *         code>null</code> if the given string is in a correct integer
	 *         representation.
	 */
	public static Integer toInteger(String str) {
		if(isBlank(str))
			return null;

		try {
			int intValue = Integer.parseInt(str);
			return new Integer(intValue);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * Parse a string to a Double value, return <code>null</code> if the given
	 * string is not in a correct Double representation.
	 *
	 * @param str
	 * @return a Double value of the given string representation or
	 *         code>null</code> if the given string is not in a correct Double
	 *         representation.
	 */
	public static Double toDouble(String str) {
		if(str == null)
			return null;

		try {
			double doubleValue = Double.parseDouble(str);
			return new Double(doubleValue);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * Quote a string, returning "abc" for a given abc string.
	 * @param str
	 * @return
	 */
	public static String quote(String str) {
		return "\"" + str + "\"";
	}

    public static String[] wrap(int[] arrays) {
    	String[] strings = new String[arrays.length];
    	for (int i = 0; i < arrays.length; i ++) {
			strings[i] = String.valueOf(arrays[i]);
		}
    	return strings;
    }
    
    public static int[] unwrap(String[] arrays) {
    	int[] ints = new int[arrays.length];
		for (int i = 0; i < arrays.length; i++) {
			ints[i] = Integer.parseInt(arrays[i]);
		}
		return ints;
    }

    public static String camelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public static boolean isInteger(String str) {
        return toInteger(str) != null;
    }
}
