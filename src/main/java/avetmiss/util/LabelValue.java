package avetmiss.util;

import java.io.Serializable;

public class LabelValue implements Comparable<LabelValue>, Serializable {
    private static final long serialVersionUID = 3689355407466181430L;

    private String label;
    private String value;

    public static LabelValue labelValue(final String label, final String value) {
    	return new LabelValue(label, value);
    }

    public static LabelValue labelValue(final String label, final String value, final String title) {
    	return new LabelValue(label, value, title);
    }

    private LabelValue(final String label, final String value) {
        this.label = label;
        this.value = value;
    }

    private LabelValue(final String label, final String value, final String title) {
        this.label = label;
        this.value = value;
        this.title = title;
    }


    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String title;
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int compareTo(LabelValue o) {
	    // Implicitly tests for the correct type, throwing
        // ClassCastException as required by interface
		String otherLabel = o.getLabel();
        return this.getLabel().compareTo(otherLabel);
	}

    public String toString() {
        StringBuilder sb = new StringBuilder("LabelValue[");
        sb.append(this.label);
        sb.append(", ");
        sb.append(this.value);
        sb.append("]");
        return (sb.toString());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof LabelValue)) {
            return false;
        }

        LabelValue bean = (LabelValue) obj;
        int nil = (this.getValue() == null) ? 1 : 0;
        nil += (bean.getValue() == null) ? 1 : 0;

        if (nil == 2) {
            return true;
        } else if (nil == 1) {
            return false;
        } else {
            return this.getValue().equals(bean.getValue());
        }
    }

    public int hashCode() {
        return (this.getValue() == null) ? 17 : this.getValue().hashCode();
    }
}