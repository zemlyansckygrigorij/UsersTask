package org.example.userstask.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.userstask.entity.User;
import org.example.userstask.services.UserComponent;
import org.example.userstask.web.model.request.UserRequest;
import org.example.userstask.web.model.response.UserResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class UserController
 * for work with /users
 */
@RestController
@Validated
@Tag(name = "API работы с пользователями",
        description = "Api work users")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserComponent component;

    @Operation(
            description = "Поиск пользователя по идентификатору",
            summary = "Retrieve user by id")
    @ApiResponse(responseCode = "200", description = "Get user by id")
    @GetMapping("/{id}")
    public UserResponse findByIdOrDie(@PathVariable(name = "id") final Long id) {
        return new UserResponse(component.findByIdOrDie(id));
    }

    @Operation(
            description = "Вставка пользователя",
            summary = "insert new user into database")
    @ApiResponse(responseCode = "200", description = "list of UserResponse")
    @PostMapping()
    public UserResponse commit(@RequestBody UserRequest userRequest){
        User user = new User();
        try {
            user = component.commit(userRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
       return  new UserResponse(user);
    }

    @Operation(
            description = "Получение всех пользователей",
            summary = "Retrieve all users")
    @ApiResponse(responseCode = "200", description = "list of UserResponse")
    @GetMapping()
    public List<UserResponse> findAll(){
        return component.findAll().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Operation(
            description = "Удаление пользователя",
            summary = "delete user from database by id")
    @ApiResponse(responseCode = "200", description = "null")
    @DeleteMapping("/{id}")
    public void deleteUserById(
            @PathVariable(name = "id") final long id) {
        try {
            component.deleteUserById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Operation(
            description = "Обновление пользователя",
            summary = "update user by id")
    @ApiResponse(responseCode = "200", description = "null")
    @PutMapping("/{id}")
    public void updateUserById(@RequestBody UserRequest request,
    @PathVariable(name = "id") final long id) {
        component.updateUserById(id, request);
    }
}
