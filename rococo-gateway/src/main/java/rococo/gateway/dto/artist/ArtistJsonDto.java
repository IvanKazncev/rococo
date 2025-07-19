package rococo.gateway.dto.artist;

import com.shared.proto.artists.Artist;
import lombok.Data;

@Data
public class ArtistJsonDto {
    private String id;
    private String name;
    private String biography;
    private String photo;

    public static ArtistJsonDto fromGrpcMessage(Artist grpcMessage) {
        ArtistJsonDto json = new ArtistJsonDto();
        json.setId(grpcMessage.getId());
        json.setName(grpcMessage.getName());
        json.setBiography(grpcMessage.getName());
        json.setPhoto(grpcMessage.getPhoto());
        return json;
    }
}
