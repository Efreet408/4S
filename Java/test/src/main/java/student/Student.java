package student;

public class Student {
    private int id;
    private String fullName;
    private int year;
    private int group;
    private double avarageRating;

    public Student() {
    }

    public Student(int id, String fullName, int year, int group, double avarageRating) {
        this.id = id;
        this.fullName = fullName;
        this.year = year;
        this.group = group;
        this.avarageRating = avarageRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public double getAvarageRating() {
        return avarageRating;
    }

    public void setAvarageRating(double avarageRating) {
        this.avarageRating = avarageRating;
    }
}
