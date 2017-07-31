package avetmiss.client;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang.StringUtils.isBlank;

public enum CoeStatus {
	STUDYING("Studying") {
        @Override public boolean isStudying() {
            return true;
        }
    },
	VISAGRANTED("Visa Granted") {
        @Override public boolean isVisaGranted() {
            return true;
        }
    },
	APPROVED("Approved") {
        @Override public boolean isApproved() {
            return true;
        }
    },
	OFFERED("Offered") {
        @Override public boolean isOffered() {
            return true;
        }
    },
	CANCELLED("Cancelled") {
        @Override public boolean isCancelled() {
            return true;
        }
    },
	FINISHED("Finished") {
        @Override public boolean isFinished() {
            return true;
        }
    };

	private final String name;

	CoeStatus(String name) {
		this.name = name;
	}
	
	private static final Map<String, CoeStatus> stringToEnum = new LinkedHashMap<>();
	static {
		// case-insensitive
		for(CoeStatus status: values()) {
			stringToEnum.put(status.name.toLowerCase(), status);
		}
	}
	
	/**
	 * All status except CANCELLED and UNKNOWN
	 */
	public final static Set<CoeStatus> STATUS_NOT_CANCELLED = EnumSet.of(STUDYING,
			VISAGRANTED, APPROVED, OFFERED, FINISHED);

    /**
     * A collection of effective status (CANCELLED, and UNKNOWN are excluded)
     */
    public static Set<CoeStatus> EFFECTIVE_STATUS = EnumSet.of(STUDYING,
            VISAGRANTED, APPROVED, OFFERED);

    public boolean isCancelled() {
        return false;
    }

    public boolean isStudying() {
        return false;
    }

    public boolean isOffered() {
        return false;
    }

    public boolean isFinished() {
        return false;
    }

    public boolean isApproved() {
        return false;
    }

    public boolean isVisaGranted() {
        return false;
    }


    public static CoeStatus fromString(String statusStr) {
        if (isBlank(statusStr)) {
            return CANCELLED;
        }

        CoeStatus status = stringToEnum.get(statusStr.toLowerCase());
        if (status == null) {
            throw new IllegalStateException("coe statusStr not supported: " + statusStr);
        }
        return status;
    }

	public static boolean isCancelled(String status) {
		return CANCELLED.name.equals(status) || isBlank(status);
	}

    public static boolean isStudying(String status) {
        return STUDYING.name.equals(status);
    }

    public static boolean isOffered(String status) {
		return OFFERED.name.equals(status);
	}
	
	public static boolean isFinished(String status) {
		return FINISHED.name.equals(status);
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
}
