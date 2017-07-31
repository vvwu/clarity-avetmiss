package avetmiss.export;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

/**
 * Reports validation exceptions.
 *
 */
public class ValidationException extends RuntimeException {

    private Collection<? extends Exception> validationExceptions = new ArrayList();

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Collection<? extends Exception> errors) {
        this();
		this.validationExceptions = Collections.unmodifiableCollection(errors);
    }

    public ValidationException(Exception ex) {
        this(singletonList(ex));
    }

    public Collection<? extends Exception> getValidationExceptions() {
        return validationExceptions;
    }

    public String getMessage() {
		List<String> messages = getMessages();
		return StringUtils.join(messages.toArray(new String[0]), '\n');
    }
    
    public List<String> getMessages() {
    	List<String> messages = new ArrayList<>();

    	Collection<? extends Exception> exceptions = getValidationExceptions();
    	if (exceptions.size() == 0) {
    		messages.add(super.getMessage());
    		return messages;
    	}

        messages.addAll(exceptions.stream().map((Function<Exception, String>) Throwable::getMessage).collect(Collectors.toList()));
    	return messages;
    }
}
