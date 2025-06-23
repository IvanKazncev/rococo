package rococo.gateway.dto.artist;

import lombok.Data;

@Data
public class ArtistCreateDto {
    private String name;
    private String biography;
    private String photo;
}
