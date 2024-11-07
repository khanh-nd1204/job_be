package com.project.job.controller;

import com.project.job.dto.ResponseObject;
import com.project.job.dto.AuthDTO;
import com.project.job.service.SecurityService;
import com.project.job.util.UnauthorizedException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthCtrl {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@Valid @RequestBody AuthDTO authDTO) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
            Authentication authentication
                    = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            String access_token = securityService.createToken(authentication);
            ResponseObject res = new ResponseObject(HttpStatus.OK.value(),
                    "Login successfully", access_token, null
            );
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }
}
