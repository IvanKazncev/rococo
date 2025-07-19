package rococo.gateway.dto.painting;

import com.shared.proto.paintings.Painting;
import lombok.Data;
import rococo.gateway.dto.artist.ArtistJsonDto;
import rococo.gateway.dto.museum.MuseumRelationJsonDto;

@Data
public class PaintingRelationJsonDto {
    private String id;
    private String title;
    private String description;
    private String content;
    private ArtistJsonDto artist;
    private MuseumRelationJsonDto museum;

    public static PaintingRelationJsonDto fromGrpcMessage(Painting grpcMessage) {
        PaintingRelationJsonDto json = new PaintingRelationJsonDto();
        json.setId(grpcMessage.getId());
        json.setTitle(grpcMessage.getTitle());
        json.setDescription(grpcMessage.getDescription());
        json.setContent(grpcMessage.getContent());
        json.setArtist(ArtistJsonDto.fromGrpcMessage(grpcMessage.getArtist()));
        json.setMuseum(MuseumRelationJsonDto.fromGrpcMessage(grpcMessage.getMuseum()));
        return json;
    }
}
