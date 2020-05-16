package com.htec.vojinpesalj.dakarrally.service.impl;

import com.htec.vojinpesalj.dakarrally.exception.RoleNotFoundException;
import com.htec.vojinpesalj.dakarrally.exception.UserAlreadyExistsException;
import com.htec.vojinpesalj.dakarrally.repository.RoleRepository;
import com.htec.vojinpesalj.dakarrally.repository.UserRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.Role;
import com.htec.vojinpesalj.dakarrally.repository.domain.User;
import com.htec.vojinpesalj.dakarrally.security.jwt.JwtProvider;
import com.htec.vojinpesalj.dakarrally.service.UserService;
import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.RegisterUserRequest;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            JwtProvider jwtProvider,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticateResponse authenticateUser(AuthenticateRequest authenticateRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticateRequest.getUsername(),
                                authenticateRequest.getPassword()));

        String token = jwtProvider.generateJwtToken(authentication);
        return AuthenticateResponse.builder().type("Bearer").token(token).build();
    }

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        userRepository
                .findByUsername(registerUserRequest.getUsername())
                .ifPresent(
                        (u) -> {
                            throw new UserAlreadyExistsException(registerUserRequest.getUsername());
                        });

        Role userRole =
                roleRepository
                        .findByName("ROLE_USER")
                        .orElseThrow(() -> new RoleNotFoundException("ROLE_USER"));

        User user =
                User.builder()
                        .username(registerUserRequest.getUsername())
                        .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                        .roles(Collections.singletonList(userRole))
                        .email(registerUserRequest.getEmail())
                        .name(registerUserRequest.getName())
                        .build();

        userRepository.save(user);
    }
}
