package avetmiss.export.natfile;

import avetmiss.controller.payload.nat.Nat00060SubjectFileRequest;
import avetmiss.domain.EnrolmentSubject;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NAT00060SubjectFile {

    public List<Nat00060SubjectFileRequest> nat00060SubjectFileRequests(Set<EnrolmentSubject> competencies) {
        return competencies.stream()
                .map(unit -> new Nat00060SubjectFileRequest(unit.subjectIdentifier(), unit.norminalHours()))
                .collect(Collectors.toList());
    }

}
