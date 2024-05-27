package com.backend.goFun_backend.service;

import com.backend.goFun_backend.dto.RegisterRequest;
import com.backend.goFun_backend.model.Member;
import com.backend.goFun_backend.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(member.getEmail(), member.getPassword(), new ArrayList<>());
    }
    
    public Member register(RegisterRequest request) {
        Member existingMember = memberRepository.findByEmail(request.getEmail());
        if (existingMember != null) {
            throw new RuntimeException("Email already registered");
        }

        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));

        return memberRepository.save(member);
    }
}