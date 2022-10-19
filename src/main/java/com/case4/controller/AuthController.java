package com.case4.controller;

import com.case4.model.dto.ChangePassword;
import com.case4.model.dto.JwtResponse;
import com.case4.model.dto.ResponseMessage;
import com.case4.model.dto.SignUpForm;
import com.case4.model.entity.blog.Blog;
import com.case4.model.entity.extra.Status;
import com.case4.model.entity.user.User;
import com.case4.model.entity.user.UserInfo;
import com.case4.model.entity.user.UserStatus;
import com.case4.service.JwtService;
import com.case4.service.user.IUserService;
import com.case4.service.userInfo.UserInfoService;
import com.case4.service.userStatus.IUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserStatusService userStatusService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        //Kiểm tra username và pass có đúng hay không
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        //Lưu user đang đăng nhập vào trong context của security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUserName(user.getUsername());
        UserInfo userInfo = userInfoService.findByUserId(currentUser.getId());
        UserStatus userStatus = userInfo.getUserStatus();
        if (!userStatus.isVerify()){
            return new ResponseEntity<>("Your account has banned by Admin !",HttpStatus.LOCKED);
        }
        userStatus.setStatus(Status.ONLINE);
        userStatusService.save(userStatus);
        return ResponseEntity.ok(new JwtResponse(currentUser.getId(), jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpForm user) {
        ResponseMessage message=new ResponseMessage();
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            message.setMessage("Confirm-password does not match password");
            return new ResponseEntity<>(message,HttpStatus.CONFLICT);
        }
        if(userService.isUsernameExist(user.getUsername())){
            message.setMessage("Registration account is duplicated");
            return new ResponseEntity<>(message,HttpStatus.FOUND);
        }
        String avatar = "profile.png";
        User user1 = new User(user.getUsername(), user.getPassword());

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        localDate.format(fmt1);
        String userRegisDate = String.valueOf(localDate);
        Set<Blog> blogSet = new HashSet<>();
        UserStatus userStatus = new UserStatus();
        userStatusService.save(userStatus);
        userService.save(user1);
        UserInfo userInfo = new UserInfo(
                user.getName(),
                user.getEmail(),
                avatar,
                user.getPhoneNumber(),
                user.getBirthDay(),
                userRegisDate,
                userStatus,
                user1
        );
        userInfoService.save(userInfo);
        message.setMessage("register complete");
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    @PostMapping("/changePassword/{id}")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody ChangePassword changePassword) {
        Optional<User> user = this.userService.findById(id);
        String newPassword;
        String oldPassword = changePassword.getOldPassword();
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (passwordEncoder.matches(oldPassword, user.get().getPassword())) {
                if (changePassword.getNewPassword().equals(changePassword.getConfirmNewPassword())) {
                    newPassword = changePassword.getNewPassword();
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        user.get().setPassword(newPassword);
        user.get().setId(id);
        this.userService.save(user.get());
        return new ResponseEntity<>(user.get(), HttpStatus.OK);

    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Boolean> usernameExitCheck(@PathVariable String username) {
        Boolean check = false;
        List<User> users = this.userService.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                check = true;
                break;
            }
        }
        return new ResponseEntity<>(check, HttpStatus.OK);
    }

    @GetMapping("/logout/{id}")
    public ResponseEntity<UserStatus> logout(@PathVariable Long id) {
        UserInfo userInfo = userInfoService.findByUserId(id);
        UserStatus userStatus = userInfo.getUserStatus();
        userStatus.setLastLogin(getUpdateAt());
        userStatus.setStatus(Status.OFFLINE);
        userStatusService.save(userStatus);
        return new ResponseEntity<>(userStatus, HttpStatus.OK);
    }
    private String getUpdateAt(){
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDate.format(fmt1);
    }
}
