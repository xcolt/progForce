package models;

public class TargetAdapter {

    String login;
    String name;
    String company;
    String email;
    String followers;
    String following;
    String public_repos;
    String public_gists;
    String avatar_url;

    @Override
    public String toString() {
        return login + "|" + name + "|" + company + "|" + email + "|" + followers + "|" + following
                + "|" + public_repos + "|" + public_gists + "|" + avatar_url;
    }
}