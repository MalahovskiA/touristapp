package by.malahovski.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Data Transfer Object (DTO) for representing a tour service.
 * This class holds information about a tour service, including its ID, name, description,
 * and a list of associated attraction IDs. It is used to transfer tour service data between
 * different layers of the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourServiceDTO {

    /**
     * The unique identifier of the tour service.
     */
    private Long id;

    /**
     * The name of the tour service.
     */
    private String name;

    /**
     * A description of the tour service.
     */
    private String description;

    /**
     * A list of attraction IDs associated with this tour service.
     * This field contains a list of Long values, each representing the unique identifier
     * of an attraction that is included in the tour service.
     */
    private List<Long> attractionsIDs;
}