package clarity.controller.payload;

public class SuburbReadModel {
	public String name;
    public int postCode;

    public SuburbReadModel() {
    }

    public SuburbReadModel(String name, int postCode) {
        this.name = name;
        this.postCode = postCode;
    }
}
