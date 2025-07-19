package rococo.backends.artists.entity;

import com.shared.proto.artists.Artist;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "artists")
@Data
public class ArtistEntity {
    @Id
    private String id; //UUID
    private String name;
    private String biography;
    private String photo;

    public static ArtistEntity fromProto(Artist artistProto) {
        ArtistEntity artist = new ArtistEntity();
        artist.setId(artistProto.getId());
        artist.setName(artistProto.getName());
        artist.setBiography(artistProto.getBiography());
        artist.setPhoto(artistProto.getPhoto());
        return artist;
    }

    public static Artist fromEntity(ArtistEntity entity) {
        return Artist.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setBiography(entity.getBiography())
                .setPhoto(entity.getPhoto())
                .build();
    }
}
