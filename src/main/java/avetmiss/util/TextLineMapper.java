package avetmiss.util;

public interface TextLineMapper<T> {
	T mapRow(String line, int lineNum);
}
