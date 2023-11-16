package io.github.dsjdevelopment.voyaz.api.controller;

import io.github.dsjdevelopment.voyaz.api.domain.ExceptionValidation;
import io.github.dsjdevelopment.voyaz.api.domain.user.User;
import io.github.dsjdevelopment.voyaz.api.domain.user.UserRegistrationData;
import io.github.dsjdevelopment.voyaz.api.domain.user.UserRepository;
import io.github.dsjdevelopment.voyaz.api.domain.user.UserUpdateData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationData data, UriComponentsBuilder uriBuilder) {
        if (repository.existsByLoginAndActiveTrue(data.login())) {
            throw new ExceptionValidation("Email already exist in database: " + data.login());
        }
        if (repository.existsByLoginAndActiveFalse(data.login())) {
            var user = repository.getReferenceByLogin(data.login());
            user.dataUpdate(new UserUpdateData(user));
        } else {
            var user = new User(data.login(), passwordEncoder.encode(data.password()));
            repository.save(user);
        }


        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid UserUpdateData data) {
        var user = repository.getReferenceById(data.id());
        user.dataUpdate(data);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        user.delete();

        return ResponseEntity.noContent().build();
    }
}
