package ulaval.glo2003;

public enum Environment {
    PRODUCTION,
    STAGING,
    TEST;

    public String enumToString() {
        return this.toString().toLowerCase();
    }
}
