package cm.backend.ecommerce.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cm.backend.ecommerce.dtos.UsersRequestDto;

@RestController
@RequestMapping("/api/users")
public interface IUserController {

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody UsersRequestDto dto);

    @GetMapping("/{userId}")
    ResponseEntity<?> getUserById(@PathVariable Long userId);

    @PutMapping("/{userId}")
    ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UsersRequestDto dto);

    @DeleteMapping("/{userId}")
    ResponseEntity<?> deleteUser(@PathVariable Long userId);

    @GetMapping
    ResponseEntity<?> getAllUsers();

}