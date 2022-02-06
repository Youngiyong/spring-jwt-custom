package com.user.auth.service;


import com.user.auth.exception.ErrorCode;
import com.user.auth.exception.UserAuthServerException;
import com.cheese.domain.domain.user.UserRepository;
import com.cheese.domain.domain.userHasRole.UserHasRole;
import com.cheese.domain.domain.userRole.UserRoleRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException {


        com.cheese.domain.domain.user.User checkUser = userRepository.findByHpAndDeleteDateIsNull(username)
                .orElseThrow(() -> new UserAuthServerException(ErrorCode.NOT_FOUND_USER));

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserHasRole role : checkUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().getName().name()));
        }

        return new User(
                String.valueOf(checkUser.getId()),
                checkUser.getPassword(),
                authorities
        );
    }

}
