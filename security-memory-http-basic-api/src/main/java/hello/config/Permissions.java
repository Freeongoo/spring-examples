package hello.config;

public enum Permissions {

    COMPANY_VIEW("company#V"),      // GET
    COMPANY_EDIT("company#E"),      // PATCH or PUT
    COMPANY_CREATE("company#C");    // POST or DELETE

    private final String permissionString;

    private Permissions(String permissionString) {
        this.permissionString = permissionString;
    }

    public String getValue() {
        return permissionString;
    }

}
