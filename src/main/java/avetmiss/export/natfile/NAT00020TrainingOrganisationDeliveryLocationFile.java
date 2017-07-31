package avetmiss.export.natfile;


import avetmiss.controller.payload.nat.Nat00020TrainingOrganisationDeliveryLocationFileRequest;

public class NAT00020TrainingOrganisationDeliveryLocationFile {

    public Nat00020TrainingOrganisationDeliveryLocationFileRequest deliveryLocationFileRequest(String toid) {
        return new Nat00020TrainingOrganisationDeliveryLocationFileRequest(toid);
    }

}
