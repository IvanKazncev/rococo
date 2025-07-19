package rococo.backends.geos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rococo.backends.geos.entity.CountryEntity;

import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, UUID>, PagingAndSortingRepository<CountryEntity, UUID> {
    CountryEntity findById(String id);
    Page<CountryEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
    long countByNameContainingIgnoreCase(String name);
}
