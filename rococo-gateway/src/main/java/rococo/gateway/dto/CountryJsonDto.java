package rococo.gateway.dto;

import com.shared.proto.geos.Country;
import com.shared.proto.geos.Geo;
import lombok.Data;

@Data
public class CountryJsonDto {
    private String id;
    private String name;

    public static CountryJsonDto fromGrpcMessage(Country grpcMessage) {
        CountryJsonDto json = new CountryJsonDto();
        json.setId(grpcMessage.getId());
        json.setName(grpcMessage.getName());
        return json;
    }

    public static Country fromEntity(CountryJsonDto entity) {
        Country.Builder builder = Country.newBuilder()
                .setId(entity.getId());

        String name = entity.getName();
        if(name != null && !name.isEmpty()) {
            builder.setName(name);
        }

        return builder.build();
    }
}
