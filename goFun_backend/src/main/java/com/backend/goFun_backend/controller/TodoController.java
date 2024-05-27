package com.backend.goFun_backend.controller;

import com.backend.goFun_backend.model.Todo;
import com.backend.goFun_backend.model.TodoRepository;
import com.backend.goFun_backend.model.Member;
import com.backend.goFun_backend.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "http://localhost:4200")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody Todo todo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Member member = memberRepository.findByEmail(currentUserName);

        if (member != null) {
            todo.setMember(member);
            todoRepository.save(todo);
            return ResponseEntity.ok(todo);
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }

    @GetMapping
    public ResponseEntity<?> getTodos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Member member = memberRepository.findByEmail(currentUserName);

        if (member != null) {
            List<Todo> todos = todoRepository.findByMember(member);
            return ResponseEntity.ok(todos);
        } else {
            return ResponseEntity.status(401).body("User not authenticated");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        Optional<Todo> todoOptional = todoRepository.findById(id);

        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            todo.setDescription(todoDetails.getDescription());
            todo.setCompleted(todoDetails.isCompleted());
            todoRepository.save(todo);
            return ResponseEntity.ok(todo);
        } else {
            return ResponseEntity.status(404).body("Todo not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);

        if (todoOptional.isPresent()) {
            todoRepository.delete(todoOptional.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).body("Todo not found");
        }
    }
}