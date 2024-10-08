package by.malahovski.repository;


import by.malahovski.model.TourService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing {@link TourService} entities.
 * This interface extends {@link JpaRepository} and provides methods for performing
 * CRUD operations on the TourService entity.
 */
@Repository
public interface TourServiceRepository extends JpaRepository<TourService, Long> {
}
