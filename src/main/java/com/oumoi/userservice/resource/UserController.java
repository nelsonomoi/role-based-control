package com.oumoi.userservice.resource;


import com.oumoi.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('view_users')")
    public String fetchUsers(){
        return "Users resource";
    }
}
