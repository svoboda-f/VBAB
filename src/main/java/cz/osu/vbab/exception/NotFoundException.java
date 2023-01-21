package cz.osu.vbab.exception;

public class NotFoundException extends Exception{
    public NotFoundException() {
        super("Item not found!");
    }

    public NotFoundException(Class<?> clazz,long id) {
        super(clazz.getSimpleName() + " with id " + id + " not found");
    }
}
