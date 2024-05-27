package com.backend.goFun_backend.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByMember(Member member);
}