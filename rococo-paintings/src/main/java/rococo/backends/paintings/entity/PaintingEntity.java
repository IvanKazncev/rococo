package rococo.backends.paintings.entity;

import com.shared.proto.artists.Artist;
import com.shared.proto.paintings.Painting;
import com.shared.proto.paintings.UpdatePaintingRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import rococo.backends.paintings.dto.ArtistDto;

@Entity
@Table(name = "paintings")
@Data
public class PaintingEntity {
    @Id
    private String id; //UUID
    private String title;
    private String description;
    private String content;
    private String artistId;
    private String museumId;

    public static PaintingEntity fromProto(UpdatePaintingRequest geoProto) {
        PaintingEntity painting = new PaintingEntity();
        painting.setId(geoProto.getId());
        painting.setTitle(geoProto.getTitle());
        painting.setDescription(geoProto.getDescription());
        painting.setContent(geoProto.getContent());
        painting.setArtistId(geoProto.getArtist().getId());
        painting.setMuseumId(geoProto.getMuseum().getId());
        return painting;
    }

    public static Painting fromEntity(PaintingEntity entity, Artist artist) {
        ArtistDto artistDto = new ArtistDto();
        artistDto.setId(artist.getId());
        artistDto.setName(artist.getName());
        artistDto.setBiography(artist.getBiography());
        artistDto.setPhoto(artist.getPhoto());

        return Painting.newBuilder()
                .setId(entity.getId())
                .setTitle(entity.getTitle())
                .setDescription(entity.getDescription())
                .setContent(entity.getContent())
                .setArtist(ArtistDto.fromEntity(artistDto))
                .build();
    }
}
