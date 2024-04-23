package com.dahub.auth.service;

import com.dahub.auth.entity.UserEntity;
import com.dahub.auth.repository.UserRepository;
import com.dahub.auth.utils.UserPrincipal;
import com.dahub.auth.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserEntity getUser(String id){
        return userRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = getUser(userId);
        return UserPrincipal.create(user.getUserId(), user.getPassword(), user.getRoleType());
    }

}
