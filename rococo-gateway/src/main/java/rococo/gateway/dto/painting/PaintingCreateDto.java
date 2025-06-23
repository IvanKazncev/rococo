package rococo.gateway.dto.painting;

import lombok.Data;
import rococo.gateway.dto.artist.ArtistIdDto;
import rococo.gateway.dto.museum.MuseumIdDto;

@Data
public class PaintingCreateDto {
    private String title;
    private String description;
    private String content;
    private ArtistIdDto artist;
    private MuseumIdDto museum;
}
