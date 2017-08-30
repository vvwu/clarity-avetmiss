package avetmiss;

import avetmiss.client.payload.OrganizationConstantReadModel;
import avetmiss.controller.payload.nat.*;
import avetmiss.domain.EnrolmentSubject;
import avetmiss.domain.UnitRepository;
import avetmiss.domain.nat.*;
import avetmiss.controller.payload.nat.Nat00090DisabilityFileRequest;
import avetmiss.export.Client;
import avetmiss.export.NatCourse;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Strings.nullToEmpty;
import static java.util.Arrays.asList;

@Service
public class AvetmissNatGenerationApplicationService {
    private static Logger logger = LoggerFactory.getLogger(AvetmissNatGenerationApplicationService.class);

    @Autowired private UnitRepository unitRepository;

    public byte[] getNatFileZip(NatFilesRequest natFilesRequest) throws IOException {
        NatFile nat00010 = getNat00010TrainingOrganizationFile(natFilesRequest.nat00010TrainingOrganizationFileRequest);
        NatFile nat00020 = getNat00020TrainingOrganisationDeliveryLocationFile(natFilesRequest.rtoIdentifier);
        NatFile nat00030 = getNat00030CourseFileRequest(natFilesRequest.nat00030CourseFileRequests);
        NatFile nat00060 = getNat00060SubjectFile(natFilesRequest.nat00060SubjectFileRequests);
        List<NatFile> nat80And85 = getsClientFiles(natFilesRequest.clientFileRequest);
        NatFile nat00090 = getNat00090DisabilityFile(natFilesRequest.nat0009DisabilityFileRequests);
        NatFile nat00100 = getNat00100PriorEducationFile(natFilesRequest.nat00100PriorEducationFileRequests);
        NatFile nat00120 = getNat00120EnrolmentFile(natFilesRequest.enrolmentFileRequests);
        NatFile nat00130 = getNat00130QualificationCompletedFile(natFilesRequest.nat00130QualificationCompletedFileRequests);

        List<NatFile> natFiles = new ArrayList<>();

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

    private NatFile getNat00010TrainingOrganizationFile(
            OrganizationConstantReadModel nat00010TrainingOrganizationFileRequest) {
        NatFile natFile = new NatFile("NAT00010.txt");
        if(nat00010TrainingOrganizationFileRequest == null) {
            return natFile;
        }

        String content = new Nat00010TrainingOrganizationFile().export(nat00010TrainingOrganizationFileRequest);

        logger.info("[nat00010TrainingOrganizationFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private NatFile getNat00020TrainingOrganisationDeliveryLocationFile(
            String rtoIdentifier) {
        NatFile natFile = new NatFile("NAT00020.txt");
        if(rtoIdentifier == null) {
            return natFile;
        }

        String content = new Nat00020TrainingOrganisationDeliveryLocationFile().export(rtoIdentifier);

        logger.info("[nat00020TrainingOrganisationDeliveryLocationFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private NatFile getNat00030CourseFileRequest(Collection<NatCourse> requests) {
        NatFile natFile = new NatFile("NAT00030.txt");
        if(requests == null || requests.isEmpty()) {
            return natFile;
        }

        String content = new Nat00030CourseFile().export(requests);

        logger.info("[nat00030CourseFile]: \n{}", content);
        return natFile.withContent(content);
    }

    private List<NatFile> getsClientFiles(List<Client> requests) {
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

    private NatFile getNat00060SubjectFile(List<EnrolmentSubject> requests) {
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

    private NatFile getNat00130QualificationCompletedFile(List<Nat00130QualificationCompletedFileRequest> requests) {
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


