package rococo.gateway.dto.museum;

import lombok.Data;
import rococo.gateway.dto.geo.GeoCreateDto;

@Data
public class MuseumCreateDto {
    private String title;
    private String description;
    private String photo;
    private GeoCreateDto geo;
}
