package by.malahovski.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "tour_service", schema = "public")
public class TourService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column (name = "description")
    private String description;

    @ManyToMany(mappedBy = "tourServices")
    private List<Attraction> attractions;
}