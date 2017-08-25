package avetmiss.domain.nat;

import avetmiss.client.payload.OrganizationConstantReadModel;
import avetmiss.controller.payload.nat.Nat00010TrainingOrganizationFileRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class Nat00010TrainingOrganizationFileTest {
    private Nat00010TrainingOrganizationFile instance;

    @Before
    public void setUp() throws Exception {
        this.instance = new Nat00010TrainingOrganizationFile();
    }

    @Test
    public void testExport() throws Exception {
        OrganizationConstantReadModel request = organizationConstant();

        String export = instance.export(request);
        assertThat(export, is("0000020829Victorian Institute of Technology Pty Ltd                                                           91Level 10, 123 Queen Street                                                                          Melbourne                                         300002Surapaneni,Nagarjun,Mr,CEO                                  0396707848          0396707848          info@vit.edu.au                                                                 Clarity2008         rock.yu99@gmail.com                                                             \r\n"));
    }

    public static int TOID = 20829;

    private OrganizationConstantReadModel organizationConstant() {
        OrganizationConstantReadModel constant = new OrganizationConstantReadModel();

        constant.toid = TOID;
        constant.fullName = "Victorian Institute of Technology Pty Ltd";
        constant.address = "Level 10, 123 Queen Street";
        constant.suburb = "Melbourne";
        constant.postcode = "3000";
        constant.contactName = "Surapaneni,Nagarjun,Mr,CEO";
        constant.contactPhone = "0396707848";
        constant.contactFax = "0396707848";
        constant.contactEmail = "info@vit.edu.au";

        return constant;
    }

}
