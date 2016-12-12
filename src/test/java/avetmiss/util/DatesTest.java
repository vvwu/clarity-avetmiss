package avetmiss.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatesTest {
    private LocalDate date = LocalDate.of(2016, 12, 1);

    @Test
    public void currentYear() throws Exception {
        assertTrue(Dates.currentYear() >= 2016);
    }

    @Test
    public void toISO() throws Exception {
        assertEquals("2016-12-01", Dates.toISO(date));
        assertEquals(null, Dates.toISO(null));
    }

    @Test
    public void toLocalDateISO() throws Exception {
        assertEquals(this.date, Dates.toLocalDateISO("2016-12-01"));
        assertEquals(null, Dates.toLocalDateISO(null));
    }

}