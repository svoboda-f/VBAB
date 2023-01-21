package cz.osu.vbap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.osu.vbap.exception.NotFoundException;
import cz.osu.vbap.model.rest.PrivateUserInfo;
import cz.osu.vbap.model.rest.PublicUserInfo;
import cz.osu.vbap.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getInfoById(@PathVariable long userId) {
        try {
            PublicUserInfo userInfo = this.userService.getInfo(userId);
            return ResponseEntity.ok(userInfo);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping() 
    public ResponseEntity<Object> updateInfo(@RequestBody @Valid PrivateUserInfo userInfo) {
        this.userService.updateInfo(userInfo);
        return ResponseEntity.accepted().build();
    }
}
