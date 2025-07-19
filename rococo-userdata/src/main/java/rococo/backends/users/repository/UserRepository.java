package rococo.backends.users.repository;

import rococo.backends.users.entity.UserEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  @Nonnull
  Optional<UserEntity> findByUsername(@Nonnull String username);
}
