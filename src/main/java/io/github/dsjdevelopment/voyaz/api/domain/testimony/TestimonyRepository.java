package io.github.dsjdevelopment.voyaz.api.domain.testimony;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestimonyRepository extends JpaRepository<Testimony, Long> {

    @Query("""
            SELECT t FROM Testimony t
            ORDER BY RAND()
            """)
    Page<Testimony> randomTestimony(Pageable pagination);

}
