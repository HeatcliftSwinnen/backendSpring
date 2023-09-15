package be.technifutur.backend.exceptions;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;

@Getter
public class UniqueViolationException extends RuntimeException {

    private final Collection<String> fieldNames;

    public UniqueViolationException(String... fieldNames){
        this(null, Arrays.stream( fieldNames ).toList());
    }

    public UniqueViolationException(Collection<String> fieldNames) {
        this(null, fieldNames);
    }

    public UniqueViolationException(Throwable cause, Collection<String> fieldNames) {
        super("{%s} should be unique".formatted(fieldNames), cause);
        this.fieldNames = fieldNames;
    }
}
