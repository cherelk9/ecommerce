package cm.backend.ecommerce.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cm.backend.ecommerce.dtos.UsersRequestDto;
import jakarta.validation.Valid;

public interface IUserController {

    @PostMapping(value = "/users", consumes = "application/json")
    ResponseEntity<?> createUser(@Valid @RequestBody UsersRequestDto dto);

    @GetMapping("/users/{userId}")
    ResponseEntity<?> getUserById(@PathVariable("userId") Long userId);

    @PutMapping("/users/{userId}")
    ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UsersRequestDto dto);

    @DeleteMapping("/users/{userId}")
    ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId);

    @GetMapping("/users")
    ResponseEntity<?> getAllUsers();

}