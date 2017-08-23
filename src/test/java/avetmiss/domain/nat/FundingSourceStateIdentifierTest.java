package avetmiss.domain.nat;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FundingSourceStateIdentifierTest {
    @Test
    public void testFundingSourceNationalIdentifierFrom() throws Exception {
        assertThat(FundingSourceStateIdentifier.fundingSourceNationalIdentifierFrom("P"), is("11"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFundingSourceNationalIdentifierFromShouldRaiseException() throws Exception {
        FundingSourceStateIdentifier.fundingSourceNationalIdentifierFrom("unknown");
    }
}