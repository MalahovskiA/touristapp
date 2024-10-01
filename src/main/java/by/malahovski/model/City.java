package by.malahovski.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "city", schema = "public")
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "population", nullable = false)
    private int population;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Attraction> attractions;

    @Column(name = "metro", nullable = false)
    private boolean hasMetro;
}