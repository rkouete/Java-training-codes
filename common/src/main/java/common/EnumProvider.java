package common;

public enum EnumProvider {
    S3("S3"), GOOGLE_STORAGE("GOOGLE_STORAGE"), BLOB_STORAGE("BLOB_STORAGE");
    private final String value;

    EnumProvider(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
