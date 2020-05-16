package com.htec.vojinpesalj.dakarrally.service.impl;

import com.htec.vojinpesalj.dakarrally.repository.UserRepository;
import com.htec.vojinpesalj.dakarrally.repository.domain.User;
import com.htec.vojinpesalj.dakarrally.security.UserPrinciple;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "User Not Found with -> username or email : "
                                                        + username));

        return UserPrinciple.build(user);
    }
}
