public abstract class person {
    protected int id;
    protected String name;
    protected String phone;

    public person(String name, int id, String phone) {
        this.id =id;
        this.name = name;
        this.phone = phone;
    }
    public int  getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
}
