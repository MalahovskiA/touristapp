package by.malahovski.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a tour service entity in the system.
 * The TourService class maps to the "tour_service" table in the database and contains
 * details about the tour services offered, including their name and description.
 * It establishes a many-to-many relationship with the {@link Attraction} entity,
 * allowing multiple attractions to be associated with a tour service and vice versa.
 */
@Getter
@Setter
@Entity
@Table(name = "tour_service", schema = "public")
public class TourService implements Serializable {

    /** The unique identifier for the tour service. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the tour service. */
    @Column(name = "name")
    private String name;

    /** A description of the tour service. */
    @Column(name = "description")
    private String description;

    /**
     * The list of attractions associated with the tour service.
     * This relationship is managed by the {@link Attraction} entity.
     */
    @ManyToMany(mappedBy = "tourServices")
    @JsonManagedReference
    private List<Attraction> attractions;
}