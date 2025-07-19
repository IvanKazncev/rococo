package rococo.backends.museums.dto;

import com.shared.proto.geos.Country;
import com.shared.proto.geos.Geo;
import lombok.Data;

@Data
public class CountryDto {
    String id;
    String name;

    public static Country fromEntity(CountryDto entity) {
        return Country.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .build();
    }

    public static CountryDto fromProto(Country countryProto) {
        CountryDto country = new CountryDto();
        country.setId(countryProto.getId());
        country.setName(countryProto.getName());
        return country;
    }
}
