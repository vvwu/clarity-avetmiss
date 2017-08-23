package avetmiss.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OutcomeIdentifierNationalTest {

    @Test
    public void testCode() throws Exception {
        OutcomeIdentifierNational instance = new OutcomeIdentifierNational("40");
        assertThat(instance.code(), is(40));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionIfInvalidValueGiven() throws Exception {
        OutcomeIdentifierNational instance = new OutcomeIdentifierNational("invalid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionIfEmptyValueGiven() throws Exception {
        OutcomeIdentifierNational instance = new OutcomeIdentifierNational("");
    }
}