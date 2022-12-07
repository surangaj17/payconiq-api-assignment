package pojo;

public class PatchBookings {

    private String firstname;
    private String lastname;

    public PatchBookings(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public PatchBookings() {
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
