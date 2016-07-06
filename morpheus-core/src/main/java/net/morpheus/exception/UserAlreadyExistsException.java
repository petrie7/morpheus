package net.morpheus.exception;

public class UserAlreadyExistsException extends UserNotInCauthException {

    public UserAlreadyExistsException(String username) {
        super(username);
    }

    @Override
    public String getMessage() {
        return "Already exists in Morpheus " + this.username();
    }
}
