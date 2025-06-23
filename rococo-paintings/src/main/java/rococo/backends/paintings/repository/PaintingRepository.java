package rococo.backends.paintings.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rococo.backends.paintings.entity.PaintingEntity;

import java.util.UUID;

@Repository
public interface PaintingRepository extends JpaRepository<PaintingEntity, UUID>, PagingAndSortingRepository<PaintingEntity, UUID> {
    PaintingEntity findById(String id);
    Page<PaintingEntity> findByArtistId(String artistId, Pageable pageable);
    long countByArtistId(String artistId);
    Page<PaintingEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    long countByTitleContainingIgnoreCase(String title);
}
