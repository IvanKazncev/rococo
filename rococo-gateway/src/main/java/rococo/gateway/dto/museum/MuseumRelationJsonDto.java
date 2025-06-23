package rococo.gateway.dto.museum;

import com.shared.proto.museums.MuseumRelation;
import lombok.Data;
import rococo.gateway.dto.geo.GeoRelationJsonDto;

@Data
public class MuseumRelationJsonDto {
    private String id;
    private String title;
    private String description;
    private String photo;
    private GeoRelationJsonDto geo;

    public static MuseumRelationJsonDto fromGrpcMessage(MuseumRelation grpcMessage) {
        MuseumRelationJsonDto json = new MuseumRelationJsonDto();
        json.setId(grpcMessage.getId());
        json.setTitle(grpcMessage.getTitle());
        json.setDescription(grpcMessage.getDescription());
        json.setPhoto(grpcMessage.getPhoto());
        json.setGeo(GeoRelationJsonDto.fromGrpcMessage(grpcMessage.getGeo()));
        return json;
    }
}
