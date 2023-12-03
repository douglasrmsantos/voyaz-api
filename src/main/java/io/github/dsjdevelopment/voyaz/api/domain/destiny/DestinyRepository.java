package io.github.dsjdevelopment.voyaz.api.domain.destiny;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinyRepository extends JpaRepository<Destiny, Long> {

    Page<Destiny> findAllByName(String name,Pageable pagination);

    Boolean existsByName(String name);
}
