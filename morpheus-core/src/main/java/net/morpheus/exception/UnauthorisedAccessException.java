package net.morpheus.exception;

public class UnauthorisedAccessException extends RuntimeException {

    @Override
    public String getMessage() {
        return "You do not have permission to perform that action";
    }
}
