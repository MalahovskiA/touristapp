package by.malahovski.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "tour_service", schema = "public")
public class TourService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "tourServices")
    @JsonManagedReference
    private List<Attraction> attractions;
}