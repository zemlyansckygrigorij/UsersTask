package org.example.userstask.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.userstask.entity.User;

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

    @Schema(description = "Имя пользователя.")
    @JsonProperty("username")
    private String username;

    @Schema(description = "email пользователя.")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Пароль пользователя.")
    @JsonProperty("password")
    private String password;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
