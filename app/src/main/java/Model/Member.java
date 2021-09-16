package Model;

public class Member {
    String username;
    String password;
    String name;
    String managing_status;
    String hostel_id;
    String email;
    String contact;
    String blood;

    public Member() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member(String username, String password, String name, String managing_status, String hostel_id, String email, String contact, String blood) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.managing_status = managing_status;
        this.hostel_id = hostel_id;
        this.email = email;
        this.contact = contact;
        this.blood = blood;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaging_status() {
        return managing_status;
    }

    public void setManaging_status(String managing_status) {
        this.managing_status = managing_status;
    }

    public String getHostel_id() {
        return hostel_id;
    }

    public void setHostel_id(String hostel_id) {
        this.hostel_id = hostel_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}
