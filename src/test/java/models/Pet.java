package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Pet {
    private Long id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Tag[] tagsList;
    private String status;
}
