package myy803.socialbookstore.datamodel;

public enum Role {
	USER("User");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
