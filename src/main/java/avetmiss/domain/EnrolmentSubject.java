package avetmiss.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

import static com.google.common.base.Preconditions.checkArgument;

public class EnrolmentSubject {
    private String subjectIdentifier;
    private String fieldOfEducationIdentifier;
    private int nominalHours;

    public EnrolmentSubject(String subjectIdentifier, String fieldOfEducationIdentifier, int nominalHours) {
        checkArgument(subjectIdentifier != null, "subjectIdentifier can not be null");
        checkArgument(fieldOfEducationIdentifier != null, "fieldOfEducationIdentifier can not be null, subjectIdentifier: %s", subjectIdentifier);

        this.subjectIdentifier = subjectIdentifier;
        this.fieldOfEducationIdentifier = fieldOfEducationIdentifier;
        this.nominalHours = nominalHours;
    }

    public String subjectIdentifier() {
        return subjectIdentifier;
    }

    public String fieldOfEducationIdentifier() {
        return fieldOfEducationIdentifier;
    }

    public int norminalHours() {
        return nominalHours;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof EnrolmentSubject)) {
            return false;
        }

        EnrolmentSubject that = (EnrolmentSubject) obj;
        return new EqualsBuilder().append(this.subjectIdentifier, that.subjectIdentifier).isEquals();
    }

    @Override
    public int hashCode() {
        return subjectIdentifier.hashCode();
    }
}
