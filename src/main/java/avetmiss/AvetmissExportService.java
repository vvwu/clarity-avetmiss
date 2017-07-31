package avetmiss;

import avetmiss.client.ClarityShareServiceClient;
import avetmiss.controller.payload.inputFile.AvetmissInputFileProcessResult;
import avetmiss.controller.payload.inputFile.TaskListenerReadModel;
import avetmiss.controller.payload.nat.NatFilesRequest;
import avetmiss.domain.EnrolmentSubject;
import avetmiss.export.Client;
import avetmiss.export.InputReader2;
import avetmiss.export.NatFileConfig;
import avetmiss.client.payload.OrganizationConstantReadModel;
import avetmiss.export.natfile.V20140301NATFileConfig;
import avetmiss.util.hudson.StreamTaskListener;
import avetmiss.util.hudson.TaskListener;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import static avetmiss.domain.AvetmissUtil.collectCompetencies;
import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class AvetmissExportService {

    private InputReader2 inputReader;
    private ClarityShareServiceClient clarityShareServiceClient;
    private AvetmissInputFileApplicationService avetmissInputFileApplicationService;
    private AvetmissNatGenerationApplicationService avetmissNatGenerationApplicationService;

    public AvetmissExportService(
            InputReader2 inputReader,
            ClarityShareServiceClient clarityShareServiceClient, AvetmissInputFileApplicationService avetmissInputFileApplicationService,
            AvetmissNatGenerationApplicationService avetmissNatGenerationApplicationService) {
        this.inputReader = inputReader;
        this.clarityShareServiceClient = clarityShareServiceClient;
        this.avetmissInputFileApplicationService = avetmissInputFileApplicationService;
        this.avetmissNatGenerationApplicationService = avetmissNatGenerationApplicationService;
    }

    public String process(File requiredInputFile, File outputZipFile) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        StreamTaskListener listener = new StreamTaskListener(baos);

        try {

            OrganizationConstantReadModel organizationConstant = clarityShareServiceClient.organizationConstant();

            assertThatTrainingOrganisationIdentifierIsConfigured(organizationConstant);

            String csvContent = csvContent(requiredInputFile);
            AvetmissInputFileProcessResult result = avetmissInputFileApplicationService.readAndValidate(csvContent);

            this.logToTaskListener(listener, result.taskListener);

            List<Client> clients = inputReader.readAndValidate(result.clients, listener);

            startExport(
                    organizationConstant,
                    clients,
                    outputZipFile,
                    listener);

            return baos.toString();

        } catch (IOException e) {
            listener.error(ExceptionUtils.getFullStackTrace(e));
            return baos.toString();

        } finally {
            if (listener != null) {
                listener.closeQuietly();
            }
        }
    }

    private String csvContent(File file) {
        Resource fileResource = new FileSystemResource(file);
        try {
            return IOUtils.toString(new InputStreamReader(fileResource.getInputStream(), UTF_8));
        } catch (IOException e) {
            throw new IllegalArgumentException("Fail to open file: " + file, e);
        }
    }

    private void startExport(
            OrganizationConstantReadModel organizationConstant,
            List<Client> clients,
            File outputZipFile,
            TaskListener listener) throws IOException {

        NatFileConfig natFileConfig = new V20140301NATFileConfig(organizationConstant.abn);

        listener.info("%d students to report", clients.size());

        String TOID = organizationConstant.toid + "";

        NatFilesRequest natFilesRequest = new NatFilesRequest();
        Set<EnrolmentSubject> competencies = collectCompetencies(clients);

        natFilesRequest.enrolmentFileRequests = natFileConfig.nat00120EnrolmentFile().enrolmentFileRequests(clients, TOID, listener);
        natFilesRequest.nat00030CourseFileRequests = natFileConfig.nat00030CourseFile().nat00030CourseFileRequests(clients, listener);
        natFilesRequest.nat00060SubjectFileRequests = natFileConfig.nat00060SubjectFile().nat00060SubjectFileRequests(competencies);
        natFilesRequest.clientFileRequest = natFileConfig.nat00080ClientFile().clientFileRequest(clients);
        natFilesRequest.nat00100PriorEducationFileRequests = natFileConfig.nat00100PriorEducationFile().priorEducationFileRequests(clients, listener);
        natFilesRequest.nat00010TrainingOrganizationFileRequest = natFileConfig.nat00010TrainingOrganizationFile().trainingOrganizationFileRequest(organizationConstant);
        natFilesRequest.nat00020TrainingOrganisationDeliveryLocationFileRequest = natFileConfig.nat00020TrainingOrganisationDeliveryLocationFile().deliveryLocationFileRequest(TOID);
        natFilesRequest.nat0009DisabilityFileRequests = natFileConfig.nat000900DisabilityFile().nat0009DisabilityFileRequests(clients);
        natFilesRequest.nat00130QualificationCompletedFileRequests = natFileConfig.nat00130QualificationCompletedFile().qualificationCompletedFile(clients, TOID);

        byte[] natZip = avetmissNatGenerationApplicationService.getNatFileZip(natFilesRequest);
        Files.write(outputZipFile.toPath(), natZip);

        listener.info("Reporting completed.");
    }

    private void ensureOutputDirIsClean(File outputDir) {
        try {
            FileUtils.cleanDirectory(outputDir);
        } catch (Exception e) {
            // ignore
        }
    }

    private void assertThatTrainingOrganisationIdentifierIsConfigured(OrganizationConstantReadModel organizationConstant) {
        String TOID = organizationConstant.toid + "";
        checkArgument(isNotBlank(TOID), "training.organisation.identifier is not available, please provide the identifier in clarity.properties file");
    }

    private void logToTaskListener(TaskListener listener, TaskListenerReadModel taskListener) {
        if (taskListener == null) {
            return;
        }

        if (taskListener.infoList != null) {
            taskListener.infoList.forEach(listener::info);
        }

        if (taskListener.warnList != null) {
            taskListener.warnList.forEach(listener::warn);
        }

        if (taskListener.errorList != null) {
            taskListener.errorList.forEach(listener::info);
        }
    }

}
