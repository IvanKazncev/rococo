package rococo.backends.geos.entity;

import com.shared.proto.geos.Country;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "countries")
@Data
public class CountryEntity {
    @Id
    private String id; //UUID
    private String name;

    public static Country fromEntity(CountryEntity entity) {
        return Country.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .build();
    }
}
