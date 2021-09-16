package Model;

import java.util.Date;

public class Meal {
    String username;
    int meal_count;
    int todays_meal;
    double meal_fee;
    String current_date;
    double due;
    String month;

    public Meal() {
    }

    public Meal(String username, int meal_count, int todays_meal, double meal_fee, String current_date, double due, String month) {
        this.username = username;
        this.meal_count = meal_count;
        this.todays_meal = todays_meal;
        this.meal_fee = meal_fee;
        this.current_date = current_date;
        this.due = due;
        this.month = month;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMeal_count() {
        return meal_count;
    }

    public void setMeal_count(int meal_count) {
        this.meal_count = meal_count;
    }

    public int getTodays_meal() {
        return todays_meal;
    }

    public void setTodays_meal(int todays_meal) {
        this.todays_meal = todays_meal;
    }

    public double getMeal_fee() {
        return meal_fee;
    }

    public void setMeal_fee(double meal_fee) {
        this.meal_fee = meal_fee;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
