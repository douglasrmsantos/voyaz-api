package io.github.dsjdevelopment.voyaz.api.controller;

import io.github.dsjdevelopment.voyaz.api.domain.testimony.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/testimonials")
@SecurityRequirement(name = "bearer-key")
public class TestimonyController {

    @Autowired
    private TestimonyService testimonyService;

    @PostMapping
    @Transactional
    public ResponseEntity<TestimonyDetailData> register(@RequestBody @Valid TestimonyRegistrationData data, UriComponentsBuilder uriBuilder) {

        var dto = testimonyService.registerTestimony(data);
        var uri = uriBuilder.path("/testimonials/{id}").buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(uri).body(dto);

    }

    @PutMapping
    @Transactional
    public ResponseEntity<TestimonyDetailData> update(@RequestBody @Valid TestimonyUpdateData data) {

        var dto = testimonyService.updateTestimony(data);
        return ResponseEntity.ok(dto);


    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {

        testimonyService.deleteTestimony(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<TestimonyDetailData> detail(@PathVariable Long id) {

        var dto = testimonyService.detailTestimony(id);
        return ResponseEntity.ok(dto);


    }

    @GetMapping("/testimonials-home")
    public ResponseEntity<Page<TestimonyListData>> list(
            @PageableDefault(size = 3) Pageable pagination) {

        var page = testimonyService.list3Testimony(pagination);
        return ResponseEntity.ok(page);
    }
}
