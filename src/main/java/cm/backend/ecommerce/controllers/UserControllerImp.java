package cm.backend.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import cm.backend.ecommerce.controllers.interfaces.IUserController;
import cm.backend.ecommerce.dtos.UsersRequestDto;
import cm.backend.ecommerce.exceptions.UserNotFoundException;
import cm.backend.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserControllerImp implements IUserController {

    private final cm.backend.ecommerce.services.interfaces.IUsersService usersService;

    @Override
    public ResponseEntity<?> createUser(UsersRequestDto dto) {

        if (!UserUtils.isValidEmail(dto.email())) {
            return new ResponseEntity<>(UserUtils.INVALIDE_EMAIL_FORMAT, HttpStatus.BAD_REQUEST);
        } else if (usersService.existsByEmail(dto.email())) {
            return new ResponseEntity<>(UserUtils.USER_EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(usersService.createUser(dto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getUserById(Long id) {
        try {
            return new ResponseEntity<>(usersService.getUserById(id), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(UserUtils.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> updateUser(Long id, UsersRequestDto dto) {
        /**
         * var userExist = usersService.getUserById(id);
         * if (userExist == null) {
         * return new ResponseEntity<>(UserUtils.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
         * }
         * return new ResponseEntity<>(usersService.updateUser(id, dto), HttpStatus.OK);
         */

        try {
            usersService.getUserById(id);
            return new ResponseEntity<>(usersService.updateUser(id, dto), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(UserUtils.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(Long id) {
        var userExist = usersService.getUserById(id);
        if (userExist == null) {
            return new ResponseEntity<>(UserUtils.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        usersService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        return usersService.getAllUsers() != null
                ? new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
