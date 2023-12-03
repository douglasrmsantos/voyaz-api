package io.github.dsjdevelopment.voyaz.api.controller;

import io.github.dsjdevelopment.voyaz.api.domain.user.UserRegistrationData;
import io.github.dsjdevelopment.voyaz.api.service.UserService;
import io.github.dsjdevelopment.voyaz.api.domain.user.UserUpdateData;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationData data, UriComponentsBuilder uriBuilder) {

        var dto = userService.registerUser(data);
        var uri = uriBuilder.path("/testimonials/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid UserUpdateData data) {

        var dto = userService.updateUser(data);
        return ResponseEntity.ok(dto);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {

        var dto = userService.detailUser(id);
        return ResponseEntity.ok(dto);

    }
}
