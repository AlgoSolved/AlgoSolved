package com.example.backend.user.controller;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.response.BaseResponse;
import com.example.backend.user.dto.UserDTO.Profile;
import com.example.backend.user.response.UserStatus;
import com.example.backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Profile>> getUserInfo(@PathVariable("id") Long id) {
        Profile userInfo = userService.getUserProfile(id);

        return new ResponseEntity(
                BaseResponse.success(
                        UserStatus.SUCCESS.getCode(), UserStatus.SUCCESS.getMessage(), userInfo),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Boolean>> deleteUser(
            @PathVariable("id") Long userId, @RequestParam String inputUsername) {
        if (!userService.verifyUsername(userId, inputUsername)) {
            return new ResponseEntity(
                    BaseResponse.failure(
                            ExceptionStatus.USERNAME_MISMATCH.getCode(),
                            ExceptionStatus.USERNAME_MISMATCH.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        boolean result = userService.deleteUser(userId);
        return new ResponseEntity(
                BaseResponse.success(
                        UserStatus.SUCCESS.getCode(), UserStatus.SUCCESS.getMessage(), result),
                HttpStatus.OK);
    }
}
