package net.morpheus.exception;

public class UserNotInCauthException extends RuntimeException {

    private String username;

    public UserNotInCauthException(String username) {
        this.username = username;
    }

    public String username() {
        return username;
    }

    @Override
    public String getMessage() {
        return "Not in CAUTH " + username;
    }
}
