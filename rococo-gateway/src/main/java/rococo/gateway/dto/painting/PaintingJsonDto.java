package rococo.gateway.dto.painting;

import com.shared.proto.paintings.Painting;
import lombok.Data;
import rococo.gateway.dto.artist.ArtistIdDto;
import rococo.gateway.dto.museum.MuseumIdDto;

@Data
public class PaintingJsonDto {
    private String id;
    private String title;
    private String description;
    private String content;
    private ArtistIdDto artist;
    private MuseumIdDto museum;

    public static PaintingJsonDto fromGrpcMessage(Painting grpcMessage) {
        PaintingJsonDto json = new PaintingJsonDto();
        json.setId(grpcMessage.getId());
        json.setTitle(grpcMessage.getTitle());
        json.setDescription(grpcMessage.getDescription());
        json.setContent(grpcMessage.getContent());
        return json;
    }
}
