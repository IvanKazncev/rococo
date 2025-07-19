package rococo.backends.users.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import rococo.backends.users.dto.UserJson;
import rococo.backends.users.entity.UserEntity;
import rococo.backends.users.repository.UserRepository;
import jakarta.annotation.Nonnull;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserService {

  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public static boolean isPhotoString(String photo) {
    return photo != null && photo.startsWith("data:image");
  }

  @Transactional
  @KafkaListener(topics = "users", groupId = "userdata")
  public void listener(@Payload String rawMessage, ConsumerRecord<String, UserJson> cr) throws JsonProcessingException {
      ObjectMapper mapper = new ObjectMapper();
      UserJson user = mapper.readValue(rawMessage, UserJson.class);

    userRepository.findByUsername(user.username())
        .ifPresentOrElse(
            u -> LOG.info("### User already exist in DB, kafka event will be skipped: {}", cr.toString()),
            () -> {
              LOG.info("### Kafka consumer record: {}", cr.toString());

              UserEntity userDataEntity = new UserEntity();
              userDataEntity.setUsername(user.username());
              userDataEntity.setFirstname("");
              userDataEntity.setLastname("");

              UserEntity userEntity = userRepository.save(userDataEntity);

              LOG.info(
                  "### User '{}' successfully saved to database with id: {}",
                  user.username(),
                  userEntity.getId()
              );
            }
        );
  }

  @Nonnull
  public UserEntity getRequiredUser(@Nonnull String username) throws ChangeSetPersister.NotFoundException {
    return userRepository.findByUsername(username).orElseThrow(
            ChangeSetPersister.NotFoundException::new
    );
  }

  @Transactional
  public @Nonnull
  UserJson update(@Nonnull UserJson user) {
    UserEntity userEntity = userRepository.findByUsername(user.username())
        .orElseGet(() -> {
            UserEntity emptyUser = new UserEntity();
            emptyUser.setUsername(user.username());
            return emptyUser;
        });

    userEntity.setFirstname(user.firstname());
    userEntity.setLastname(user.lastname());

    if (isPhotoString(user.avatar())) {
        userEntity.setAvatar(new SmallPhoto(100, 100, user.avatar()).bytesToBase64String());
    }

    UserEntity saved = userRepository.save(userEntity);
    return UserJson.fromEntity(saved);
  }
}
