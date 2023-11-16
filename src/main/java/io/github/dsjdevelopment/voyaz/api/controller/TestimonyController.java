package io.github.dsjdevelopment.voyaz.api.controller;

import io.github.dsjdevelopment.voyaz.api.domain.testimony.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/testimonials")
public class TestimonyController {

    @Autowired
    private TestimonyRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<TestimonyDetailData> register(@RequestBody @Valid TestimonyRegistrationData data, UriComponentsBuilder uriBuilder) {
        var testimony = new Testimony(data);
        repository.save(testimony);

        var uri = uriBuilder.path("/testimonials/{id}").buildAndExpand(testimony.getId()).toUri();

        return ResponseEntity.created(uri).body(new TestimonyDetailData(testimony));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TestimonyDetailData> update(@RequestBody @Valid TestimonyUpdateData data) {
        var testimony = repository.getReferenceById(data.id());
        testimony.dataUpdate(data);

        return ResponseEntity.ok(new TestimonyDetailData(testimony));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestimonyDetailData> detail(@PathVariable Long id) {
        var testimony = repository.getReferenceById(id);

        return ResponseEntity.ok(new TestimonyDetailData(testimony));
    }
}