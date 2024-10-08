package by.malahovski.handler;


/**
 * Exception thrown when an entity is not found.
 * This exception is a custom runtime exception that indicates a requested entity
 * could not be located in the data source. It can be used to signal issues
 * when trying to retrieve entities that do not exist.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new EntityNotFoundException with the specified detail message.
     *
     * @param message The detail message which is saved for later retrieval by
     *                the {@link Throwable#getMessage()} method
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
