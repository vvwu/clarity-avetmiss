package avetmiss.client;

public enum Endpoint {
    vit("http://localhost:9091/vit/api"),
    rhodes("http://localhost:9092/rhodes/api");

    private String baseUrl;

    Endpoint(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public static Endpoint fromString(String deployment) {
        if (vit.name().equals(deployment)) {
            return vit;
        }

        if (rhodes.name().equals(deployment)) {
            return rhodes;
        }

        throw new IllegalArgumentException("deployment not supported: '" + deployment + "', only vit and rhodes are supported");
    }
}
