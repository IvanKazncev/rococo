package rococo.backends.museums.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rococo.backends.museums.entity.MuseumEntity;

import java.util.UUID;

@Repository
public interface MuseumRepository extends JpaRepository<MuseumEntity, UUID>, PagingAndSortingRepository<MuseumEntity, UUID> {
    MuseumEntity findById(String id);
    Page<MuseumEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    long countByTitleContainingIgnoreCase(String title);
}
