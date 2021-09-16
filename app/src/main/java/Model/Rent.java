package Model;

public class Rent {
    String username;
    int house_rent;
    int maid;
    int utility;
    String payment_status;
    String month;

    public Rent() {
    }

    public Rent(String username, int house_rent, int maid, int utility, String payment_status, String month) {
        this.username = username;
        this.house_rent = house_rent;
        this.maid = maid;
        this.utility = utility;
        this.payment_status = payment_status;
        this.month = month;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHouse_rent() {
        return house_rent;
    }

    public void setHouse_rent(int house_rent) {
        this.house_rent = house_rent;
    }

    public int getMaid() {
        return maid;
    }

    public void setMaid(int maid) {
        this.maid = maid;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
