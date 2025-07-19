package rococo.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;

import java.util.Date;

public record SessionJsonDto(@JsonProperty("username")
                          String username,
                             @JsonProperty("issuedAt")
                          Date issuedAt,
                             @JsonProperty("expiresAt")
                          Date expiresAt) {
    public static @Nonnull SessionJsonDto empty() {
        return new SessionJsonDto(null, null, null);
    }
}
