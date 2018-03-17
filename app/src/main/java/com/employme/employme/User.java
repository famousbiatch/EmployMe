package com.employme.employme;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String city;
    private String pictureUrl;
    private int age;
    private String education;
    private int driverLicense;
    private String phoneNumber;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", age=" + age +
                ", education='" + education + '\'' +
                ", driverLicense=" + driverLicense +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public User(String name, String email, String password, String city, String picture_url, int age, String education, int driverLicense, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.pictureUrl = picture_url;
        this.age = age;
        this.education = education;
        this.driverLicense = driverLicense;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String picture_url) {
        this.pictureUrl = picture_url;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(int driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
