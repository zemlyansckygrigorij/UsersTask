package org.example.userstask.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.userstask.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class UserResponse
 * for send response
 */
@Schema(description = "Данные пользователя")
@Data
@Getter
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "Идентификатор пользователя.")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Имя пользователя.")
    @JsonProperty("username")
    private String username;

    @Schema(description = "email пользователя.")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Пароль пользователя.")
    @JsonProperty("password")
    private String password;

    @Schema(description = "Дата создания пользователя.")
    @JsonProperty("createdAt")
    private String createdAt;

    @Schema(description = "Дата создания пользователя.")
    @JsonProperty("updatedAt")
    private String updatedAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();

        String timeColonPattern = "HH:mm:ss";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);

        this.createdAt = user.getCreatedAt().format(timeColonFormatter);
        this.updatedAt = user.getUpdatedAt().format(timeColonFormatter);
    }
}
