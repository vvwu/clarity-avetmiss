package avetmiss;

import avetmiss.controller.payload.inputFile.*;
import avetmiss.domain.*;
import avetmiss.util.Csv;
import avetmiss.util.Dates;
import com.google.common.collect.Lists;
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

        InputRowMapper inputRowMapper = new InputRowMapper();

        List<Enrolment> rows
                = Csv.read(new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)), inputRowMapper);

        errorList.addAll(inputRowMapper.errors());

        List<EnrolmentRowReadModel> enrolmentRowReadModels = Lists.newArrayList();
        for(Enrolment enrolment: rows) {
            try {
                loadUnitDetails(enrolment);

                EnrolmentRowReadModel enrolmentRowReadModel = toEnrolmentRowReadModel(enrolment);
                enrolmentRowReadModels.add(enrolmentRowReadModel);
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
        EnrolmentRowReadModel enrolmentRowReadModel = new EnrolmentRowReadModel();

        EnrolmentSubject unit = enrolment.getUnit();

        enrolmentRowReadModel.rowNum = enrolment.getRowNum();
        enrolmentRowReadModel.studentId = enrolment.getStudentId();
        enrolmentRowReadModel.studentName = enrolment.studentName();
        enrolmentRowReadModel.courseCode = enrolment.courseCode();
        enrolmentRowReadModel.unitCode = enrolment.getUnitCode();
        enrolmentRowReadModel.startDate = Dates.toISO(enrolment.startDate());
        enrolmentRowReadModel.endDate = Dates.toISO(enrolment.endDate());
        enrolmentRowReadModel.nominalHour = enrolment.nominalHour();
        enrolmentRowReadModel.supervisedHours = enrolment.supervisedHours();
        enrolmentRowReadModel.hoursAttended = enrolment.hoursAttended();
        enrolmentRowReadModel.outcomeIdentifier = enrolment.getOutcomeIdentifier().code();
        enrolmentRowReadModel.tuitionFee = enrolment.tuitionFee();
        enrolmentRowReadModel.subjectName = unit.subjectName();
        enrolmentRowReadModel.fieldOfEducationIdentifier = unit.fieldOfEducationIdentifier();

        return enrolmentRowReadModel;
    }

    private void loadUnitDetails(Enrolment enrolment) {
        String unitCode = enrolment.getUnitCode();
        Unit unit = this.unitRepository.findByCode(unitCode);

        checkNotNull(unit, "rowNum=%s: unitCode '%s' not found in NTIS unit list", enrolment.getRowNum(), unitCode);

        enrolment.setUnit(
                new EnrolmentSubject(
                        unit.getCode(),
                        unit.getDescription(),
                        unit.getFieldOfEducationIdentifier(),
                        enrolment.nominalHour()));
    }
}
