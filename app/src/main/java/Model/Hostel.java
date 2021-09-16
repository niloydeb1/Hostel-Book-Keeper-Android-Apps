package Model;

import java.util.ArrayList;

public class Hostel {
    String hostel_id;
    String Hostel_password;
    ArrayList<String> members;

    public Hostel() {
    }

    public Hostel(String hostel_id, String hostel_password, ArrayList<String> members) {
        this.hostel_id = hostel_id;
        Hostel_password = hostel_password;
        this.members = members;
    }

    public Hostel(ArrayList<String> members) {
        this.members = members;
    }

    public Hostel(String hostel_id, String hostel_password) {
        this.hostel_id = hostel_id;
        Hostel_password = hostel_password;
    }

    public String getHostel_id() {
        return hostel_id;
    }

    public void setHostel_id(String hostel_id) {
        this.hostel_id = hostel_id;
    }

    public String getHostel_password() {
        return Hostel_password;
    }

    public void setHostel_password(String hostel_password) {
        Hostel_password = hostel_password;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
}
