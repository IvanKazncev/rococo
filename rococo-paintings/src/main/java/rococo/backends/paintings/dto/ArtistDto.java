package rococo.backends.paintings.dto;

import com.shared.proto.artists.Artist;
import com.shared.proto.geos.Geo;
import lombok.Data;

@Data
public class ArtistDto {
    String id;
    String biography;
    String name;
    String photo;

    public static Artist fromEntity(ArtistDto entity) {
        return Artist.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setBiography(entity.getBiography())
                .setPhoto(entity.getPhoto())
                .build();
    }
}
