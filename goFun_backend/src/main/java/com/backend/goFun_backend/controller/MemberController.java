package com.backend.goFun_backend.controller;

import com.backend.goFun_backend.dto.RegisterRequest;
import com.backend.goFun_backend.model.AuthResponse;
import com.backend.goFun_backend.model.Member;
import com.backend.goFun_backend.model.MemberRepository;
import com.backend.goFun_backend.service.UserDetailsServiceImpl;
import com.backend.goFun_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/members")
@CrossOrigin(origins = "http://localhost:4200")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            Member member = userDetailsService.register(request);
            return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    // 創建新的成員
    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    // 獲取所有成員
    @GetMapping
    public Iterable<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 根據 ID 獲取成員
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return ResponseEntity.ok(member.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 更新成員信息
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable int id, @RequestBody Member memberDetails) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            Member existingMember = member.get();
            existingMember.setName(memberDetails.getName());
            existingMember.setEmail(memberDetails.getEmail());
            existingMember.setPassword(memberDetails.getPassword());
            Member updatedMember = memberRepository.save(existingMember);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 刪除成員
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable int id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
