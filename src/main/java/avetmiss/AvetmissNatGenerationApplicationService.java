package avetmiss;

import avetmiss.controller.payload.nat.*;
import avetmiss.domain.UnitRepository;
import avetmiss.domain.nat.*;
import avetmiss.controller.payload.nat.Nat00090DisabilityFileRequest;
import avetmiss.util.NatFile;
import avetmiss.util.ZipWriter;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

@Service
public class AvetmissNatGenerationApplicationService {
    private static Logger logger = LoggerFactory.getLogger(AvetmissNatGenerationApplicationService.class);

    @Autowired private UnitRepository unitRepository;

    public byte[] getNatFileZip(NatFilesRequest natFilesRequest) throws IOException {
        NatFile nat00010 = getNat00010TrainingOrganizationFile(natFilesRequest.nat00010TrainingOrganizationFileRequest);
        NatFile nat00020 = getNat00020TrainingOrganisationDeliveryLocationFile(natFilesRequest.nat00020TrainingOrganisationDeliveryLocationFileRequest);
        NatFile nat00030 = getNat00030CourseFileRequest(natFilesRequest.nat00030CourseFileRequests);
        NatFile nat00060 = getNat00060SubjectFile(natFilesRequest.nat00060SubjectFileRequests);
        List<NatFile> nat80And85 = getsClientFiles(natFilesRequest.clientFileRequest);
        NatFile nat00090 = getNat00090DisabilityFile(natFilesRequest.nat0009DisabilityFileRequests);
        NatFile nat00100 = getNat00100PriorEducationFile(natFilesRequest.nat00100PriorEducationFileRequests);
        NatFile nat00120 = getNat00120EnrolmentFile(natFilesRequest.enrolmentFileRequests);
        NatFile nat00130 = getNat00130QualificationCompletedFile(natFilesRequest.nat00130QualificationCompletedFileRequests);

        List<NatFile> natFiles = newArrayList();

        natFiles.add(nat00010);
        natFiles.add(nat00020);
        natFiles.add(nat00030);
        natFiles.add(nat00060);
        natFiles.addAll(nat80And85);
        natFiles.add(nat00090);
        natFiles.add(nat00100);
        natFiles.add(nat00120);
        natFiles.add(nat00130);

        return zipFiles(natFiles);
    }

    public NatFileReadModel getNatFiles(NatFilesRequest natFilesRequest) {
        NatFileReadModel natFileReadModel = new NatFileReadModel();

        NatFile nat00010 = getNat00010TrainingOrganizationFile(natFilesRequest.nat00010TrainingOrganizationFileRequest);
        natFileReadModel.nat00010 = nat00010.content();

        NatFile nat00020 = getNat00020TrainingOrganisationDeliveryLocationFile(natFilesRequest.nat00020TrainingOrganisationDeliveryLocationFileRequest);
        natFileReadModel.nat00020 = nat00020.content();

        NatFile nat00030 = getNat00030CourseFileRequest(natFilesRequest.nat00030CourseFileRequests);
        natFileReadModel.nat00030 = nat00030.content();

        NatFile nat00060 = getNat00060SubjectFile(natFilesRequest.nat00060SubjectFileRequests);
        natFileReadModel.nat00060 = nat00060.content();

        List<NatFile> nat80And85 = getsClientFiles(natFilesRequest.clientFileRequest);
        natFileReadModel.nat00080 = nat80And85.get(0).content();
        natFileReadModel.nat00085 = nat80And85.get(1).content();

        NatFile nat00090 = getNat00090DisabilityFile(natFilesRequest.nat0009DisabilityFileRequests);
        natFileReadModel.nat00090 = nat00090.content();

        NatFile nat00100 = getNat00100PriorEducationFile(natFilesRequest.nat00100PriorEducationFileRequests);
        natFileReadModel.nat00100 = nat00100.content();

        NatFile nat00120 = getNat00120EnrolmentFile(natFilesRequest.enrolmentFileRequests);
        natFileReadModel.nat00120 = nat00120.content();

        NatFile nat00130 = getNat00130QualificationCompletedFile(natFilesRequest.nat00130QualificationCompletedFileRequests);
        natFileReadModel.nat00130 = nat00130.content();

        return natFileReadModel;
    }

    private NatFile getNat00010TrainingOrganizationFile(
            Nat00010TrainingOrganizationFileRequest nat00010TrainingOrganizationFileRequest) {
        NatFile natFile = new NatFile("NAT00010.txt");
        if(nat00010TrainingOrganizationFileRequest == null) {
            return natFile;
        }

        String content = new Nat00010TrainingOrganizationFile().export(nat00010TrainingOrganizationFileRequest);

        logger.info("[nat00010TrainingOrganizationFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private NatFile getNat00020TrainingOrganisationDeliveryLocationFile(
            Nat00020TrainingOrganisationDeliveryLocationFileRequest nat00020TrainingOrganisationDeliveryLocationFileRequest) {
        NatFile natFile = new NatFile("NAT00020.txt");
        if(nat00020TrainingOrganisationDeliveryLocationFileRequest == null) {
            return natFile;
        }

        String content = new Nat00020TrainingOrganisationDeliveryLocationFile().export(nat00020TrainingOrganisationDeliveryLocationFileRequest.rtoIdentifier);

        logger.info("[nat00020TrainingOrganisationDeliveryLocationFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private NatFile getNat00030CourseFileRequest(List<Nat00030CourseFileRequest> requests) {
        NatFile natFile = new NatFile("NAT00030.txt");
        if(requests == null || requests.isEmpty()) {
            return natFile;
        }

        String content = new Nat00030CourseFile().export(requests);

        logger.info("[nat00030CourseFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private List<NatFile> getsClientFiles(List<ClientFileRequest> requests) {
        NatFile natFile1 = new NatFile("NAT00080.txt");
        NatFile natFile2 = new NatFile("NAT00085.txt");
        if(requests == null || requests.isEmpty()) {
            return asList(natFile1, natFile2);
        }

        String content1 = new Nat00080ClientFile().export(requests);
        String content2 = new Nat00085ClientPostalDetailsFile().export(requests);
        return asList(
                natFile1.withContent(content1),
                natFile2.withContent(content2));
    }

    private NatFile getNat00060SubjectFile(List<Nat00060SubjectFileRequest> requests) {
        NatFile natFile = new NatFile("NAT00060.txt");
        if(requests == null || requests.isEmpty()) {
            return natFile;
        }

        Nat00060SubjectFile nat00060SubjectFile = new Nat00060SubjectFile(unitRepository);

        String content = nat00060SubjectFile.export(requests);

        logger.info("[nat00060SubjectFile]: \n{}", content);
        return natFile.withContent(content);
    }


    private NatFile getNat00090DisabilityFile(List<Nat00090DisabilityFileRequest> requests) {
        NatFile natFile = new NatFile("NAT00090.txt");
        if(requests == null || requests.isEmpty()) {
            return natFile;
        }

        String content = new Nat0009DisabilityFile().export(requests);

        logger.info("[nat0009DisabilityFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private NatFile getNat00100PriorEducationFile(List<Nat00100PriorEducationFileRequest> requests) {
        NatFile natFile = new NatFile("NAT00100.txt");
        if(requests == null || requests.isEmpty()) {
            return natFile;
        }

        String content = new Nat00100PriorEducationFile().export(requests);

        logger.info("[nat00100PriorEducationFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private NatFile getNat00120EnrolmentFile(List<EnrolmentFileRequest> requests) {
        NatFile natFile = new NatFile("NAT00120.txt");
        if(requests == null || requests.isEmpty()) {
            return natFile;
        }

        String content = new Nat00120EnrolmentFile().export(requests);
        logger.info("[nat00120EnrolmentFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private NatFile getNat00130QualificationCompletedFile(
            List<Nat00130QualificationCompletedFileRequest> requests) {
        NatFile natFile = new NatFile("NAT00130.txt");
        if(requests == null || requests.isEmpty()) {
            return natFile;
        }

        String content = new Nat00130QualificationCompletedFile().export(requests);

        logger.info("[nat00130QualificationCompletedFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private static byte[] zipFiles(List<NatFile> stringZipEntries) throws IOException {
        File tempDir = Files.createTempDir();
        File natFilesDir = new File(tempDir, "natFiles");
        natFilesDir.mkdir();

        stringZipEntries.forEach(natFile -> {
            try {
                String content = nullToEmpty(natFile.content());
                File to = new File(natFilesDir, natFile.filename());

                logger.info("writing file: {}, with content: {}", to, content);

                Files.write(content, to, Charset.forName("UTF-8"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ZipWriter zipWriter = new ZipWriter();

        File zipFile = new File(tempDir, "output.zip");
        zipWriter.createZip(natFilesDir.getAbsolutePath(), zipFile.getAbsolutePath());

        logger.info("temp nat file generated: {}", zipFile);

        return Files.toByteArray(zipFile);
    }
}


