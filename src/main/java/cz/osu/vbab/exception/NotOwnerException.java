package cz.osu.vbab.exception;

public class NotOwnerException extends Exception {

    public NotOwnerException() {
        super("You're not the owner!");
    }

    public NotOwnerException(Class<?> clazz, long id) {
        super("You're not the owner of " + clazz.getSimpleName() + " with id " + id);
    }
}
