package avetmiss;

import avetmiss.controller.payload.nat.*;
import avetmiss.domain.UnitRepository;
import avetmiss.domain.nat.*;
import avetmiss.util.NatFile;
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

    private NatFile getNat00010TrainingOrganizationFile(
            Nat00010TrainingOrganizationFileRequest nat00010TrainingOrganizationFileRequest) {
        if(nat00010TrainingOrganizationFileRequest == null) {
            return null;
        }

        String content = new Nat00010TrainingOrganizationFile().export(nat00010TrainingOrganizationFileRequest);

        logger.info("[nat00010TrainingOrganizationFile]: \n{}", content);
        return new NatFile(content, "NAT00010.txt");
    }

    private NatFile getNat00020TrainingOrganisationDeliveryLocationFile(
            Nat00020TrainingOrganisationDeliveryLocationFileRequest nat00020TrainingOrganisationDeliveryLocationFileRequest) {
        if(nat00020TrainingOrganisationDeliveryLocationFileRequest == null) {
            return null;
        }

        String content = new Nat00020TrainingOrganisationDeliveryLocationFile().export(nat00020TrainingOrganisationDeliveryLocationFileRequest.rtoIdentifier);

        logger.info("[nat00020TrainingOrganisationDeliveryLocationFile]: \n{}", content);
        return new NatFile(content, "NAT00020.txt");
    }

    private NatFile getNat00030CourseFileRequest(List<Nat00030CourseFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat00030CourseFile().export(requests);

        logger.info("[nat00030CourseFile]: \n{}", content);
        return new NatFile(content, "NAT00030.txt");
    }

    private NatFile getNat00060SubjectFile(List<Nat00060SubjectFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        Nat00060SubjectFile nat00060SubjectFile = new Nat00060SubjectFile(unitRepository);

        String content = nat00060SubjectFile.export(requests);

        logger.info("[nat00060SubjectFile]: \n{}", content);
        return new NatFile(content, "NAT00060.txt");
    }

    private List<NatFile> getsClientFiles(List<ClientFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return Collections.emptyList();
        }

        NatFile natFile1 = new NatFile(new Nat00080ClientFile().export(requests), "NAT00080.txt");
        NatFile natFile2 = new NatFile(new Nat00085ClientPostalDetailsFile().export(requests), "NAT00085.txt");
        return Arrays.asList(natFile1, natFile2);
    }

    private NatFile getNat00090DisabilityFile(List<Nat0009DisabilityFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat0009DisabilityFile().export(requests);

        logger.info("[nat0009DisabilityFile]: \n{}", content);
        return new NatFile(content, "NAT00090.txt");
    }


    private NatFile getNat00100PriorEducationFile(List<Nat00100PriorEducationFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat00100PriorEducationFile().export(requests);

        logger.info("[nat00100PriorEducationFile]: \n{}", content);
        return new NatFile(content, "NAT00100.txt");
    }

    private NatFile getNat00120EnrolmentFile(List<EnrolmentFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat00120EnrolmentFile().export(requests);
        logger.info("[nat00120EnrolmentFile]: \n{}", content);
        return new NatFile(content, "NAT00120.txt");
    }

    private NatFile getNat00130QualificationCompletedFile(
            List<Nat00130QualificationCompletedFileRequest> requests) {
        if(requests == null || requests.isEmpty()) {
            return null;
        }

        String content = new Nat00130QualificationCompletedFile().export(requests);

        logger.info("[nat00130QualificationCompletedFile]: \n{}", content);
        return new NatFile(content, "NAT00130.txt");
    }

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

        List<NatFile> natFiles = Lists.newArrayList();
        addIfNotNull(natFiles, nat00010);
        addIfNotNull(natFiles, nat00020);
        addIfNotNull(natFiles, nat00030);
        addIfNotNull(natFiles, nat00060);
        natFiles.addAll(nat80And85);
        addIfNotNull(natFiles, nat00090);
        addIfNotNull(natFiles, nat00100);
        addIfNotNull(natFiles, nat00120);
        addIfNotNull(natFiles, nat00130);

        return zipFiles(natFiles);
    }

    public NatFileReadModel getNatFiles(NatFilesRequest natFilesRequest) {
        NatFileReadModel natFileReadModel = new NatFileReadModel();

        NatFile nat00010 = getNat00010TrainingOrganizationFile(natFilesRequest.nat00010TrainingOrganizationFileRequest);
        if(nat00010 != null) {
            natFileReadModel.nat00010 = nat00010.content();
        }

        NatFile nat00020 = getNat00020TrainingOrganisationDeliveryLocationFile(natFilesRequest.nat00020TrainingOrganisationDeliveryLocationFileRequest);
        if(nat00020 != null) {
            natFileReadModel.nat00020 = nat00020.content();
        }

        NatFile nat00030 = getNat00030CourseFileRequest(natFilesRequest.nat00030CourseFileRequests);
        if(nat00030 != null) {
            natFileReadModel.nat00030 = nat00030.content();
        }

        NatFile nat00060 = getNat00060SubjectFile(natFilesRequest.nat00060SubjectFileRequests);
        if(nat00060 != null) {
            natFileReadModel.nat00060 = nat00060.content();
        }

        List<NatFile> nat80And85 = getsClientFiles(natFilesRequest.clientFileRequest);
        if (!nat80And85.isEmpty()) {
            natFileReadModel.nat00080 = nat80And85.get(0).content();
            natFileReadModel.nat00085 = nat80And85.get(1).content();
        }

        NatFile nat00090 = getNat00090DisabilityFile(natFilesRequest.nat0009DisabilityFileRequests);
        if(nat00090 != null) {
            natFileReadModel.nat00090 = nat00090.content();
        }

        NatFile nat00100 = getNat00100PriorEducationFile(natFilesRequest.nat00100PriorEducationFileRequests);
        if(nat00100 != null) {
            natFileReadModel.nat00100 = nat00100.content();
        }

        NatFile nat00120 = getNat00120EnrolmentFile(natFilesRequest.enrolmentFileRequests);
        if(nat00120 != null) {
            natFileReadModel.nat00120 = nat00120.content();
        }

        NatFile nat00130 = getNat00130QualificationCompletedFile(natFilesRequest.nat00130QualificationCompletedFileRequests);
        if(nat00130 != null) {
            natFileReadModel.nat00130 = nat00130.content();
        }

        return natFileReadModel;
    }

    private void addIfNotNull(List<NatFile> stringZipEntries, NatFile zipEntry) {
        if(zipEntry != null) {
            stringZipEntries.add(zipEntry);
        }
    }

    private byte[] zipFiles(List<NatFile> stringZipEntries) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        byte bytes[] = new byte[2048];

        for (NatFile natFile : stringZipEntries) {
            byte[] contentBytes = natFile.content().getBytes(StandardCharsets.UTF_8);

            BufferedInputStream bis =
                    new BufferedInputStream(new ByteArrayInputStream(contentBytes));

            zos.putNextEntry(new ZipEntry(natFile.filename()));

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


