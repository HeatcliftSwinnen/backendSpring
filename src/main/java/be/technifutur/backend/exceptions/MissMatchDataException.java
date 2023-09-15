package be.technifutur.backend.exceptions;

public class MissMatchDataException extends RuntimeException{
    public MissMatchDataException(String message) {
        super(message);
    }
}

