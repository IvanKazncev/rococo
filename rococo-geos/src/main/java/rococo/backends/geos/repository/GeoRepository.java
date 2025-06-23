package rococo.backends.geos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rococo.backends.geos.entity.GeoEntity;

import java.util.UUID;

@Repository
public interface GeoRepository extends JpaRepository<GeoEntity, UUID>, PagingAndSortingRepository<GeoEntity, UUID> {
    @Query("SELECT g FROM GeoEntity g WHERE g.country_id = :countryId")
    GeoEntity findByCountryId(@Param("countryId") String countryId);
    Page<GeoEntity> findByCityContainingIgnoreCase(String city, Pageable pageable);
    long countByCityContainingIgnoreCase(String city);
}
