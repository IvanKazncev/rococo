package rococo.gateway.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import rococo.gateway.dto.UserDto;
import rococo.gateway.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userDataClient;

    @Autowired
    public UserController(UserService userDataClient) {
        this.userDataClient = userDataClient;
    }

    @GetMapping
    public UserDto getUser(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userDataClient.currentUser(username);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping
    public UserDto updateUser(@Valid @RequestBody UserDto user) {
        return userDataClient.updateUser(user);
    }
}
