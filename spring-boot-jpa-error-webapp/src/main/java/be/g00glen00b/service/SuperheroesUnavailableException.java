package be.g00glen00b.service;

public class SuperheroesUnavailableException extends RuntimeException {
    public SuperheroesUnavailableException(String message) {
        super(message);
    }
}
