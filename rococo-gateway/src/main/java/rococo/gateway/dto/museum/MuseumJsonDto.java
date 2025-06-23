package rococo.gateway.dto.museum;

import com.shared.proto.museums.Museum;
import lombok.Data;
import rococo.gateway.dto.geo.GeoCreateDto;

@Data
public class MuseumJsonDto {
    private String id;
    private String title;
    private String description;
    private String photo;
    private GeoCreateDto geo;

    public static MuseumJsonDto fromGrpcMessage(Museum grpcMessage) {
        MuseumJsonDto json = new MuseumJsonDto();
        json.setId(grpcMessage.getId());
        json.setTitle(grpcMessage.getTitle());
        json.setDescription(grpcMessage.getDescription());
        json.setPhoto(grpcMessage.getPhoto());
        return json;
    }
}
