package avetmiss.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class EnrolmentSubjectTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fieldOfEducationIdentifierIsRequired() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("fieldOfEducationIdentifier can not be null, subjectIdentifier: BSBCMN407A, subjectName: Coordinate business resources");

        new EnrolmentSubject(
                "BSBCMN407A", "Coordinate business resources", null, 100);

    }
}