package rococo.backends.museums.entity;

import com.shared.proto.geos.Country;
import com.shared.proto.museums.Museum;
import com.shared.proto.museums.MuseumRelation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import rococo.backends.museums.dto.CountryDto;
import rococo.backends.museums.dto.GeoDto;

@Entity
@Table(name = "museums")
@Data
public class MuseumEntity {
    @Id
    private String id; //UUID
    private String title;
    private String description;
    private String city;
    private String photo;
    private String country_id;

    public static MuseumEntity fromProto(Museum geoProto) {
        MuseumEntity museum = new MuseumEntity();
        museum.setId(geoProto.getId());
        museum.setTitle(geoProto.getTitle());
        museum.setDescription(geoProto.getDescription());
        museum.setPhoto(geoProto.getPhoto());
        museum.setCity(geoProto.getCity());
        museum.setCountry_id(geoProto.getCountryId());
        return museum;
    }

    public static MuseumRelation fromEntity(MuseumEntity entity, Country country) {
        GeoDto geo = new GeoDto();
        geo.setCity(entity.getCity());
        geo.setCountry(CountryDto.fromProto(country));

        return MuseumRelation.newBuilder()
                .setId(entity.getId())
                .setTitle(entity.getTitle())
                .setDescription(entity.getDescription())
                .setPhoto(entity.getPhoto())
                .setGeo(GeoDto.fromEntity(geo))
                .build();
    }
}
