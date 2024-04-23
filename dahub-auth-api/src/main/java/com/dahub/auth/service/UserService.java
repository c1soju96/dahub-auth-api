package com.dahub.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dahub.auth.dto.UserResponseDto;
import com.dahub.auth.exception.MemberNotFoundException;
import com.dahub.auth.repository.UserRepository;
import com.dahub.auth.utils.ModelMapperUtil;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(String id){
        return ModelMapperUtil.map(userRepository.findById(id).orElseThrow(MemberNotFoundException::new), UserResponseDto.class);
    }

}