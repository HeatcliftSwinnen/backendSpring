package be.technifutur.backend.exceptions;

public class NotEnoughResourceException extends RuntimeException{
    public NotEnoughResourceException(String message) {
        super(message);
    }
}
