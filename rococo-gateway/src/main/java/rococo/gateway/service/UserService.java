package rococo.gateway.service;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rococo.gateway.dto.UserDto;

import java.util.Optional;

@Service
public class UserService {
    private final RestTemplate restTemplate;
    private final String userDataApiUri;

    @Autowired
    public UserService(RestTemplate restTemplate,
                          @Value("${rococo-userdata.base-uri}") String userDataApiUri) {
        this.restTemplate = restTemplate;
        this.userDataApiUri = userDataApiUri + "/internal";
    }

    @Nonnull
    public UserDto currentUser(@Nonnull String username) {
        return Optional.ofNullable(
                restTemplate.getForObject(
                        userDataApiUri + "/users?username={username}",
                        UserDto.class,
                        username
                )
        ).orElseThrow();
    }

    @Nonnull
    public UserDto updateUser(@Nonnull UserDto user) {
        return Optional.ofNullable(
                restTemplate.patchForObject(
                        userDataApiUri + "/users/update",
                        user,
                        UserDto.class
                )
        ).orElseThrow();
    }
}
