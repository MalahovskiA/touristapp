package by.malahovski.repository.impl;

import by.malahovski.model.Attraction;
import by.malahovski.repository.AttractionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AttractionRepositoryImpl implements AttractionRepository {
    private final List<Attraction> attractions = new ArrayList<>();
    private long currentId = 1;

    @Override
    public void save(Attraction attraction) {
        attraction.setId(currentId++);
        attractions.add(attraction);
    }

    @Override
    public Attraction findById(Long id) {
        return attractions.stream()
                .filter(attraction -> attraction.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Attraction> findAll() {
        return new ArrayList<>(attractions);
    }

    @Override
    public List<Attraction> findByCity(Long cityId) {
        List<Attraction> result = new ArrayList<>();
        for (Attraction attraction : attractions) {
            if (attraction.getCity() != null && attraction.getCity().getId().equals(cityId)) {
                result.add(attraction);
            }
        }
        return result;
    }

    @Override
    public void update(Attraction attraction) {
        Optional<Attraction> existingAttraction = attractions.stream()
                .filter(a -> a.getId().equals(attraction.getId()))
                .findFirst();
        existingAttraction.ifPresent(a -> {
            a.setName(attraction.getName());
            a.setDescription(attraction.getDescription());
            a.setCreationDate(attraction.getCreationDate());
            a.setType(attraction.getType());
            a.setCity(attraction.getCity());
        });
    }

    @Override
    public void delete(Long id) {
        attractions.removeIf(attraction -> attraction.getId().equals(id));
    }
}
