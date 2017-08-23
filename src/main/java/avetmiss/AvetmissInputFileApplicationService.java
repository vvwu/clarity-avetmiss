package avetmiss;

import avetmiss.controller.payload.inputFile.*;
import avetmiss.domain.*;
import avetmiss.util.Csv;
import avetmiss.util.Dates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newLinkedHashMap;

@Service
public class AvetmissInputFileApplicationService {

    @Autowired
    private UnitRepository unitRepository;

    public AvetmissInputFileProcessResult readAndValidate(String csvContent) {

        List<String> infoList = newArrayList();
        List<String> warnList = newArrayList();
        List<String> errorList = newArrayList();

        infoList.add("Validate input file: %s, please wait......");

        InputRowMapper inputRowMapper = new InputRowMapper(unitRepository);

        List<Enrolment> rows
                = Csv.read(new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)), inputRowMapper);

        errorList.addAll(inputRowMapper.errors());

        List<EnrolmentRowReadModel> enrolmentRowReadModels = newArrayList();
        for(Enrolment enrolment: rows) {
            try {
                enrolmentRowReadModels.add(toEnrolmentRowReadModel(enrolment));
            } catch (Exception ex) {
                errorList.add("Enrolments rowNumber:" + enrolment.getRowNum() + ": " + ex.getMessage());
            }
        }

        List<ClientReadModel> clientReadModels = toClientReadModels(enrolmentRowReadModels);

        return new AvetmissInputFileProcessResult(
                clientReadModels,
                new TaskListenerReadModel(infoList, warnList, errorList));
    }

    private List<ClientReadModel> toClientReadModels(List<EnrolmentRowReadModel> enrolmentRowReadModels) {
        Map<Integer, ClientReadModel> clientByStudentId = newLinkedHashMap();

        for(EnrolmentRowReadModel enrolmentRowReadModel: enrolmentRowReadModels) {
            int studentId = enrolmentRowReadModel.studentId;
            ClientReadModel clientReadModel = clientByStudentId.get(studentId);

            if(clientReadModel == null) {
                clientReadModel = new ClientReadModel(studentId);
                clientByStudentId.put(studentId, clientReadModel);
            }

            clientReadModel.enrolments.add(enrolmentRowReadModel);
        }

        return newArrayList(clientByStudentId.values());
    }

    private EnrolmentRowReadModel toEnrolmentRowReadModel(Enrolment enrolment) {
        EnrolmentRowReadModel readModel = new EnrolmentRowReadModel();

        EnrolmentSubject unit = enrolment.getUnit();

        readModel.rowNum = enrolment.getRowNum();
        readModel.studentId = enrolment.getStudentId();
        readModel.studentName = enrolment.studentName();
        readModel.courseCode = enrolment.courseCode();
        readModel.unitCode = enrolment.getUnitCode();
        readModel.startDate = Dates.toISO(enrolment.startDate());
        readModel.endDate = Dates.toISO(enrolment.endDate());
        readModel.nominalHour = enrolment.nominalHour();
        readModel.totalSupervisedHours = enrolment.totalSupervisedHours();
        readModel.hoursAttended = enrolment.hoursAttended();
        readModel.outcomeIdentifier = enrolment.getOutcomeIdentifier().code();
        readModel.tuitionFee = enrolment.tuitionFee();
        readModel.subjectName = unit.subjectName();
        readModel.fieldOfEducationIdentifier = unit.fieldOfEducationIdentifier();

        return readModel;
    }

}
