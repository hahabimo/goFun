package com.backend.goFun_backend.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:4200")
public interface MemberRepository extends CrudRepository<Member, Integer> {
	 Member findByEmail(String email);
}
