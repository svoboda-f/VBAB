package cz.osu.vbab.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.osu.vbab.model.rest.PrivateUserInfo;
import cz.osu.vbab.model.rest.PublicUserInfo;
import cz.osu.vbab.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private Logger logger;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getInfoById(@PathVariable long id) {
        try {
            PublicUserInfo userInfo = this.userService.getInfo(id);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping() 
    public ResponseEntity<Object> updateInfo(@RequestBody @Valid PrivateUserInfo userInfo) {
        logger.info(userInfo.firstName());
        logger.info(userInfo.lastName());
        logger.info(userInfo.dateOfBirth().toString());
        this.userService.updateInfo(userInfo);
        return ResponseEntity.accepted().build();
    }
}
