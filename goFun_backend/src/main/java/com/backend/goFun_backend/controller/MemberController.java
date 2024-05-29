package com.backend.goFun_backend.controller;

import com.backend.goFun_backend.dto.MemberDTO;
import com.backend.goFun_backend.dto.RegisterRequest;
import com.backend.goFun_backend.model.AuthResponse;
import com.backend.goFun_backend.model.Itinerary;
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
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/members")
@CrossOrigin(origins = "http://localhost:4200")
public class MemberController {

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

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable int id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return ResponseEntity.ok(memberToDTO(member.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable int id, @RequestBody MemberDTO memberDetails) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            Member existingMember = member.get();
            existingMember.setName(memberDetails.getName());
            existingMember.setEmail(memberDetails.getEmail());
            existingMember.setPassword(passwordEncoder.encode(memberDetails.getPassword()));
            Member updatedMember = memberRepository.save(existingMember);
            return ResponseEntity.ok(memberToDTO(updatedMember));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private MemberDTO memberToDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        dto.setItineraryIds(member.getItineraries().stream().map(Itinerary::getId).collect(Collectors.toSet()));
        return dto;
    }
}