package by.malahovski.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


/**
 * Entity class representing an attraction in the application.
 * This class maps to the "attraction" table in the database, and it contains attributes
 * that describe an attraction, including its name, creation date, description,
 * type, and associated city. It also defines relationships with the {@link City}
 * and {@link TourService} entities.
 */
@Getter
@Setter
@Entity
@Table(name = "attraction", schema = "public")
public class Attraction implements Serializable {

    /**
     * Unique identifier for the attraction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the attraction. This field is mandatory.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Date when the attraction was created. This field is mandatory.
     */
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    /**
     * Detailed description of the attraction. This field can contain large text.
     */
    @Column(name = "description", columnDefinition = "text")
    private String description;

    /**
     * Type of the attraction, represented as an enumeration.
     * This field can be null.
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AttractionType type;

    /**
     * The city associated with this attraction.
     * This field is mandatory and establishes a many-to-one relationship with the {@link City}.
     */
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(name = "fk_attraction_city"))
    @JsonBackReference
    private City city;

    /**
     * List of tour services that include this attraction.
     * This establishes a many-to-many relationship with the {@link TourService}.
     */
    @ManyToMany
    @JoinTable(
            name = "attraction_tour_service",
            joinColumns = @JoinColumn(name = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_service_id")
    )
    @JsonBackReference
    private List<TourService> tourServices;
}