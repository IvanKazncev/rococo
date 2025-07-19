package rococo.gateway.dto.geo;

import com.shared.proto.geos.Geo;
import lombok.Data;
import rococo.gateway.dto.CountryJsonDto;

@Data
public class GeoRelationJsonDto {
    private String city;
    private CountryJsonDto country;

    public static GeoRelationJsonDto fromGrpcMessage(Geo grpcMessage) {
        GeoRelationJsonDto json = new GeoRelationJsonDto();
        json.setCity(grpcMessage.getCity());
        json.setCountry(CountryJsonDto.fromGrpcMessage(grpcMessage.getCountry()));
        return json;
    }
}
