package avetmiss.client;

public enum CoeStatus {
    STUDYING("Studying"),
    VISAGRANTED("Visa Granted"),
    APPROVED("Approved"),
    OFFERED("Offered"),
    CANCELLED("Cancelled"),
    FINISHED("Finished");

	private final String name;

	CoeStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
}
