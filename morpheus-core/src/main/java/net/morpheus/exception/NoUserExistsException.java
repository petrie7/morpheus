package net.morpheus.exception;

public class NoUserExistsException extends UserNotInCauthException {

    public NoUserExistsException(String username) {
        super(username);
    }

    @Override
    public String getMessage() {
        return "No user exists in Morpheus " + this.username();
    }
}
