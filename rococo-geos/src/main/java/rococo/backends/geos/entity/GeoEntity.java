package rococo.backends.geos.entity;

import com.shared.proto.geos.Geo;
import com.shared.proto.geos.GeoAdd;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "geos")
@Data
public class GeoEntity {
    @Id
    private String id; //UUID
    private String city;
    private String country_id;

    public static GeoEntity fromProto(GeoAdd geoProto) {
        GeoEntity artist = new GeoEntity();
        artist.setId(geoProto.getId());
        artist.setCity(geoProto.getCity());
        artist.setCountry_id(geoProto.getCountryId());
        return artist;
    }

    public static Geo fromEntity(GeoEntity entity) {
        return Geo.newBuilder()
                .setCity(entity.getCity())
                .build();
    }
}
