package be.g00glen00b.service;

public class BlogpostNotFoundException extends RuntimeException {
    public BlogpostNotFoundException() {
    }

    public BlogpostNotFoundException(String message) {
        super(message);
    }
}
