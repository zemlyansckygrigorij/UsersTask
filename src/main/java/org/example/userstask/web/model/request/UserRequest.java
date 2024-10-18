package org.example.userstask.web.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class UserRequest
 * for work with /users
 */
@Schema(description = "Данные пользователя")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Schema(description = "Имя пользователя")
    @JsonProperty("username")
    private String username;

    @Schema(description = "Email пользователя")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Пароль пользователя")
    @JsonProperty("password")
    private String password;
}
