package avetmiss;

import avetmiss.controller.payload.nat.*;
import avetmiss.domain.UnitRepository;
import avetmiss.domain.nat.*;
import avetmiss.util.StringZipEntry;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class AvetmissNatGenerationApplicationService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnitRepository unitRepository;

    private StringZipEntry getNat00010TrainingOrganizationFile(
            Nat00010TrainingOrganizationFileRequest nat00010TrainingOrganizationFileRequest) {
        if(nat00010TrainingOrganizationFileRequest == null) {
            return null;
        }

        String content = new Nat00010TrainingOrganizationFile().export(nat00010TrainingOrganizationFileRequest);

        logger.info("[nat00010TrainingOrganizationFile]: \n{}", content);
        return new StringZipEntry(content, "NAT00010.txt");
    }

    public StringZipEntry getNat00020TrainingOrganisationDeliveryLocationFile(
            Nat00020TrainingOrganisationDeliveryLocationFileRequest nat00020TrainingOrganisationDeliveryLocationFileRequest) {
        if(nat00020TrainingOrganisationDeliveryLocationFileRequest == null) {
            return null;
        }

        String content = new Nat00020TrainingOrganisationDeliveryLocationFile().export(nat00020TrainingOrganisationDeliveryLocationFileRequest.rtoIdentifier);

        logger.info("[nat00020TrainingOrganisationDeliveryLocationFile]: \n{}", content);
        return new StringZipEntry(content, "NAT00020.txt");
    }

    public StringZipEntry getNat00030CourseFileRequest(List<Nat00030CourseFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat00030CourseFile().export(requests);

        logger.info("[nat00030CourseFile]: \n{}", content);
        return new StringZipEntry(content, "NAT00030.txt");
    }

    public StringZipEntry getNat00060SubjectFile(List<Nat00060SubjectFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        Nat00060SubjectFile nat00060SubjectFile = new Nat00060SubjectFile(unitRepository);

        String content = nat00060SubjectFile.export(requests);

        logger.info("[nat00060SubjectFile]: \n{}", content);
        return new StringZipEntry(content, "NAT00060.txt");
    }

    public List<StringZipEntry> getsClientFiles(List<ClientFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return Collections.emptyList();
        }

        StringZipEntry entry1 = new StringZipEntry(new Nat00080ClientFile().export(requests), "NAT00080.txt");
        StringZipEntry entry2 = new StringZipEntry(new Nat00085ClientPostalDetailsFile().export(requests), "NAT00085.txt");
        return Arrays.asList(entry1, entry2);
    }

    public StringZipEntry getNat00090DisabilityFile(List<Nat0009DisabilityFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat0009DisabilityFile().export(requests);

        logger.info("[nat0009DisabilityFile]: \n{}", content);
        return new StringZipEntry(content, "NAT00090.txt");
    }


    public StringZipEntry getNat00100PriorEducationFile(List<Nat00100PriorEducationFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat00100PriorEducationFile().export(requests);

        logger.info("[nat00100PriorEducationFile]: \n{}", content);
        return new StringZipEntry(content, "NAT00100.txt");
    }

    public StringZipEntry getNat00120EnrolmentFile(List<EnrolmentFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat00120EnrolmentFile().export(requests);
        logger.info("[nat00120EnrolmentFile]: \n{}", content);
        return new StringZipEntry(content, "NAT00120.txt");
    }

    public StringZipEntry getNat00130QualificationCompletedFile(
            List<Nat00130QualificationCompletedFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat00130QualificationCompletedFile().export(requests);

        logger.info("[nat00130QualificationCompletedFile]: \n{}", content);
        return new StringZipEntry(content, "NAT00130.txt");
    }

    public byte[] getNats(NatFilesRequest natFilesRequest) throws IOException {
        StringZipEntry nat00010 = getNat00010TrainingOrganizationFile(natFilesRequest.nat00010TrainingOrganizationFileRequest);
        StringZipEntry nat00020 = getNat00020TrainingOrganisationDeliveryLocationFile(natFilesRequest.nat00020TrainingOrganisationDeliveryLocationFileRequest);
        StringZipEntry nat00030 = getNat00030CourseFileRequest(natFilesRequest.nat00030CourseFileRequests);
        StringZipEntry nat00060 = getNat00060SubjectFile(natFilesRequest.nat00060SubjectFileRequests);
        List<StringZipEntry> nat80And85 = getsClientFiles(natFilesRequest.clientFileRequest);
        StringZipEntry nat00090 = getNat00090DisabilityFile(natFilesRequest.nat0009DisabilityFileRequests);
        StringZipEntry nat00100 = getNat00100PriorEducationFile(natFilesRequest.nat00100PriorEducationFileRequests);
        StringZipEntry nat00120 = getNat00120EnrolmentFile(natFilesRequest.enrolmentFileRequests);
        StringZipEntry nat00130 = getNat00130QualificationCompletedFile(natFilesRequest.nat00130QualificationCompletedFileRequests);

        List<StringZipEntry> stringZipEntries = Lists.newArrayList();
        addIfNotNull(stringZipEntries, nat00010);
        addIfNotNull(stringZipEntries, nat00020);
        addIfNotNull(stringZipEntries, nat00030);
        addIfNotNull(stringZipEntries, nat00060);
        stringZipEntries.addAll(nat80And85);
        addIfNotNull(stringZipEntries, nat00090);
        addIfNotNull(stringZipEntries, nat00100);
        addIfNotNull(stringZipEntries, nat00120);
        addIfNotNull(stringZipEntries, nat00130);

        return zipFiles(stringZipEntries);
    }

    private void addIfNotNull(List<StringZipEntry> stringZipEntries, StringZipEntry zipEntry) {
        if(zipEntry != null) {
            stringZipEntries.add(zipEntry);
        }
    }

    private byte[] zipFiles(List<StringZipEntry> stringZipEntries) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        byte bytes[] = new byte[2048];

        for (StringZipEntry stringZipEntry : stringZipEntries) {
            byte[] contentBytes = stringZipEntry.content().getBytes(StandardCharsets.UTF_8);

            BufferedInputStream bis =
                    new BufferedInputStream(new ByteArrayInputStream(contentBytes));

            zos.putNextEntry(new ZipEntry(stringZipEntry.filename()));

            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
        }

        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        return baos.toByteArray();
    }
}


