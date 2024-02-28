package com.example.backend.user.controller;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.exceptions.NotFoundException;
import com.example.backend.common.response.BaseResponse;
import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.response.UserStatus;
import com.example.backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<BaseResponse<UserDTO>> getUserInfo(@PathVariable String username) {
        UserDTO userDTO = userService.getUserByUsername(username);

        return new ResponseEntity(
                BaseResponse.success(
                        UserStatus.SUCCESS.getCode(), UserStatus.SUCCESS.getMessage(), userDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<BaseResponse<String>> deleteUser(@PathVariable String username) {
        if (userService.deleteUser(username)) {
            return new ResponseEntity(
                    BaseResponse.success(
                            UserStatus.SUCCESS.getCode(), UserStatus.SUCCESS.getMessage()),
                    HttpStatus.OK);
        } else {
            throw new NotFoundException(ExceptionStatus.USER_NOT_FOUND);
        }
    }
}
