public enum gender {
    M ("male"),
    F ("female");

    private final String val="";

    gender(String value) {
    }

    public String getGender(String value) {
        return this.val;
    }
}
