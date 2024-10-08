package org.algosolved.backend.user.controller;

import lombok.RequiredArgsConstructor;

import org.algosolved.backend.common.enums.ExceptionStatus;
import org.algosolved.backend.common.response.BaseResponse;
import org.algosolved.backend.user.dto.UserDTO.Profile;
import org.algosolved.backend.user.response.UserStatus;
import org.algosolved.backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @Value("${client.base.url}")
    private String clientUrl;

    //    @GetMapping("/auth/success")
    //    public ResponseEntity<BaseResponse<String>> loginSuccess(
    //            @AuthenticationPrincipal OAuth2User oAuth2User) {
    //
    //        String jwtToken =
    //                jwtProvider.createToken(
    //                        new UserJwtDto(
    //                                Long.parseLong(
    //
    // Objects.requireNonNull(oAuth2User.getAttribute("id"))),
    //                                oAuth2User.getName(),
    //                                oAuth2User.getAuthorities()),
    //                        "access");
    //
    //        return new ResponseEntity(
    //                BaseResponse.success(
    //                        UserStatus.SUCCESS.getCode(), UserStatus.SUCCESS.getMessage(),
    // jwtToken),
    //                HttpStatus.OK);
    //    }

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
