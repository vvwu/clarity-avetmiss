package avetmiss.util;

/**
 * Collection of string utilities.
 * 
 *
 * Created on May 6, 2009
 */
public class StringUtil {

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

    public static boolean isInteger(String str) {
        return toInteger(str) != null;
    }

    public static boolean isEqualToAny(String str1, String[] strs) {
        if(strs == null || strs.length == 0)
            return false;

        for (String str : strs) {
            if(isEqual(str1, str))
                return true;
        }
        return false;
    }

    public static boolean isEqual(String str1, String str2) {
        return str1 == str2 || (str1 != null && str1.equals(str2));
    }
}
