package by.malahovski.model;

import java.io.Serializable;

/**
 * Enum representing the different types of attractions.
 * This enumeration defines the possible categories of attractions that can be
 * associated with the {@link Attraction} entity. Each type represents a
 * distinct category of attractions that may be included in the system.
 */
public enum AttractionType implements Serializable {

    /** Represents a palace attraction type. */
    PALACE,

    /** Represents a park attraction type. */
    PARK,

    /** Represents a museum attraction type. */
    MUSEUM,

    /** Represents an archaeological attraction type. */
    ARCHAEOLOGICAL,

    /** Represents a reserve attraction type. */
    RESERVE
}