package rococo.gateway.dto.geo;

import com.shared.proto.geos.Geo;
import lombok.Data;
import rococo.gateway.dto.CountryJsonDto;

@Data
public class GeoCreateDto {
    private String city;
    private CountryJsonDto country;

    public static GeoCreateDto fromGrpcMessage(Geo grpcMessage) {
        GeoCreateDto json = new GeoCreateDto();
        json.setCity(grpcMessage.getCity());
        json.setCountry(CountryJsonDto.fromGrpcMessage(grpcMessage.getCountry()));
        return json;
    }

    public static Geo fromEntity(GeoCreateDto entity) {
        return Geo.newBuilder()
                .setCity(entity.getCity())
                .setCountry(CountryJsonDto.fromEntity(entity.getCountry()))
                .build();
    }
}
