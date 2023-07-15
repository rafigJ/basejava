package ru.javawebinar.basejava.model;

public class ContactData {
    private String phoneNumber;
    private String email;
    private String linkedin;
    private String skype;
    private String github;
    private String stackOverFlow;
    private String homepage;

    public ContactData(String phoneNumber, String email, String linkedin, String skype, String github, String stackOverFlow, String homepage) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.linkedin = linkedin;
        this.skype = skype;
        this.github = github;
        this.stackOverFlow = stackOverFlow;
        this.homepage = homepage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public String getSkype() {
        return skype;
    }

    public String getGithub() {
        return github;
    }

    public String getStackOverFlow() {
        return stackOverFlow;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public void setStackOverFlow(String stackOverFlow) {
        this.stackOverFlow = stackOverFlow;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
