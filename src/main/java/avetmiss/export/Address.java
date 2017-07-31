package avetmiss.export;

public class Address {

    private String addressBuildingName;
    private String addressFlatOrUnitDetails;
    private String addressStreetNumber;
    private String addressStreetName;

    public Address(
            String addressBuildingName,
            String addressFlatOrUnitDetails,
            String addressStreetNumber,
            String addressStreetName) {
        this.addressBuildingName = addressBuildingName;
        this.addressFlatOrUnitDetails = addressFlatOrUnitDetails;
        this.addressStreetNumber = addressStreetNumber;
        this.addressStreetName = addressStreetName;
    }

    public static Address EMPTY = new Address(null, null, "NOT SPECIFIED", "NOT SPECIFIED");

    public String addressBuildingName() {
        // This field may be blank
        return addressBuildingName;
    }

    public String addressFlatOrUnitDetails() {
        return addressFlatOrUnitDetails;
    }

    public String addressStreetNumber() {
        // Where client do not provide a residential address details or address does not contain a street address (e
        // .g. clients from Aboriginal communities), the Address street name must have the value ‘NOT SPECIFIED’.
        return addressStreetNumber;
    }

    public String addressStreetName() {
        return addressStreetName;
    }

}
