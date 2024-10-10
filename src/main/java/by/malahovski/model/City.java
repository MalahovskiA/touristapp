package by.malahovski.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.List;


/**
 * Represents a city entity in the system.
 * The City class maps to the "city" table in the database and contains
 * information about the city, including its name, population, and whether it has a metro.
 * It also establishes a one-to-many relationship with the {@link Attraction} entity,
 * allowing each city to have multiple attractions associated with it.
 */
@Getter
@Setter
@Entity
@Table(name = "city", schema = "public")
public class City implements Serializable {

    /** The unique identifier for the city. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the city. */
    @Column(name = "name", nullable = false)
    private String name;

    /** The population of the city. */
    @Column(name = "population", nullable = false)
    private int population;

    /**
     * The list of attractions associated with the city.
     * This relationship is managed by the {@link Attraction} entity.
     */
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<Attraction> attractions;

    /** Indicates whether the city has a metro system. */
    @Column(name = "metro", nullable = false)
    private boolean hasMetro;
}