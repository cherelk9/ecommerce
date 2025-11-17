package cm.backend.ecommerce.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.backend.ecommerce.dtos.UsersRequestDto;
import jakarta.validation.Valid;

@RequestMapping("/api/v1")
public interface IUserController {

    @PostMapping(value = "/users", consumes = "application/json")
    ResponseEntity<?> createUser(@Valid @RequestBody UsersRequestDto dto);

    @GetMapping("/users/{userId}")
    ResponseEntity<?> getUserById(@PathVariable Long userId);

    @PutMapping("/users/{userId}")
    ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UsersRequestDto dto);

    @DeleteMapping("/users/{userId}")
    ResponseEntity<?> deleteUser(@PathVariable Long userId);

    @GetMapping("/users")
    ResponseEntity<?> getAllUsers();

}