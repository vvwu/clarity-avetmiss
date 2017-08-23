package avetmiss;

import avetmiss.controller.payload.inputFile.*;
import avetmiss.domain.*;
import avetmiss.util.Csv;
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

        List<EnrolmentInput> rows
                = Csv.read(new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)), inputRowMapper);

        errorList.addAll(inputRowMapper.errors());

        List<ClientReadModel> clientReadModels = toClientReadModels(rows);

        return new AvetmissInputFileProcessResult(
                clientReadModels,
                new TaskListenerReadModel(infoList, warnList, errorList));
    }

    private List<ClientReadModel> toClientReadModels(List<EnrolmentInput> enrolmentRowReadModels) {
        Map<Integer, ClientReadModel> clientByStudentId = newLinkedHashMap();

        for(EnrolmentInput enrolmentRowReadModel: enrolmentRowReadModels) {
            int studentId = enrolmentRowReadModel.getStudentId();
            ClientReadModel clientReadModel = clientByStudentId.get(studentId);

            if(clientReadModel == null) {
                clientReadModel = new ClientReadModel(studentId);
                clientByStudentId.put(studentId, clientReadModel);
            }

            clientReadModel.enrolments.add(enrolmentRowReadModel);
        }

        return newArrayList(clientByStudentId.values());
    }
}
