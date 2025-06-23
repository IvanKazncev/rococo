package rococo.gateway.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rococo.gateway.dto.SessionJsonDto;

import java.util.Date;


@RestController
@RequestMapping("/api/session")
public class SessionController {

    @GetMapping
    public SessionJsonDto session(@AuthenticationPrincipal Jwt principal) {
        if (principal != null) {
            return new SessionJsonDto(
                    principal.getClaim("sub"),
                    Date.from(principal.getIssuedAt()),
                    Date.from(principal.getExpiresAt())
            );
        } else {
            return SessionJsonDto.empty();
        }
    }
}



