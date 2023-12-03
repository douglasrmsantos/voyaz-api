package io.github.dsjdevelopment.voyaz.api.controller;

import io.github.dsjdevelopment.voyaz.api.domain.destiny.DestinyDetailData;
import io.github.dsjdevelopment.voyaz.api.domain.destiny.DestinyListData;
import io.github.dsjdevelopment.voyaz.api.domain.destiny.DestinyRegistrationData;
import io.github.dsjdevelopment.voyaz.api.domain.destiny.DestinyUpdateData;
import io.github.dsjdevelopment.voyaz.api.service.DestinyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/destinations")
@SecurityRequirement(name = "bearer-key")
public class DestinyController {

    @Autowired
    private DestinyService destinyService;

    @PostMapping
    @Transactional
    public ResponseEntity<DestinyDetailData> register(@RequestBody @Valid DestinyRegistrationData data, UriComponentsBuilder uriBuilder) {

        var dto = destinyService.registerDestiny(data);
        var uri = uriBuilder.path("/destinations/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }

    @PutMapping
    @Transactional
    public ResponseEntity<DestinyDetailData> update(@RequestBody @Valid DestinyUpdateData data) {

        var dto = destinyService.updateDestiny(data);
        return ResponseEntity.ok(dto);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {

        destinyService.deleteDestiny(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinyDetailData> detail(@PathVariable Long id) {

        var dto = destinyService.detailDestiny(id);
        return ResponseEntity.ok(dto);

    }

    @GetMapping
    public ResponseEntity<Page<DestinyListData>> search(@RequestParam String name, Pageable pagination) {

            var page = destinyService.search(name, pagination);
            return ResponseEntity.ok(page);

    }

}
