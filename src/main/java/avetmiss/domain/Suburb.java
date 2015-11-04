package avetmiss.domain;

public class Suburb {

	private String name;
	private int postCode;

    public Suburb() {
    }

    public Suburb(String name, int postCode) {
        this.name = name;
        this.postCode = postCode;
    }

    public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public int getPostCode() {
		return postCode;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Suburb suburb = (Suburb) o;

        if (postCode != suburb.postCode) return false;
        if (name != null ? !name.equals(suburb.name) : suburb.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + postCode;
        return result;
    }
}
