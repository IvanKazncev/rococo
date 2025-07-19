package rococo.gateway.dto.geo;

import com.shared.proto.geos.Geo;
import lombok.Data;

@Data
public class GeoJsonDto {
    private String city;
    private String country_id;

    public static GeoJsonDto fromGrpcMessage(Geo grpcMessage) {
        GeoJsonDto json = new GeoJsonDto();
        json.setCity(grpcMessage.getCity());
        json.setCountry_id(grpcMessage.getCountry().getId());
        return json;
    }
}
