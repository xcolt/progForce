package models;

public class ReposAdapter {

    String name;
    String language;
    int watchers_count;
    int stargazers_count;

    @Override
    public String toString() {
        return name + "|" + language + "|" + watchers_count + "|" + stargazers_count;
    }

}