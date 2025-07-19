package rococo.backends.museums.dto;

import com.shared.proto.geos.Geo;
import lombok.Data;

@Data
public class GeoDto {
    String city;
    CountryDto country;

    public static Geo fromEntity(GeoDto entity) {
        return Geo.newBuilder()
                .setCity(entity.getCity())
                .setCountry(CountryDto.fromEntity(entity.getCountry()))
                .build();
    }

    public static GeoDto fromProto(Geo geoProto) {
        GeoDto geo = new GeoDto();
        geo.setCity(geoProto.getCity());
        geo.setCountry(CountryDto.fromProto(geoProto.getCountry()));
        return geo;
    }
}
