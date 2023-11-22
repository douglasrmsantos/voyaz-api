package io.github.dsjdevelopment.voyaz.api.domain.testimony;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TestimonyService {

    @Autowired
    private TestimonyRepository repository;

    public TestimonyDetailData registerTestimony(TestimonyRegistrationData data) {

        var testimony = new Testimony(data);
        repository.save(testimony);
        return new TestimonyDetailData(testimony);

    }

    public TestimonyDetailData updateTestimony(TestimonyUpdateData data) {

        if (!repository.existsById(data.id())) throw new EntityNotFoundException( "Testimony not found:" + data.id());
        var testimony = repository.getReferenceById(data.id());
        testimony.dataUpdate(data);
        return new TestimonyDetailData(testimony);

    }

    public TestimonyDetailData detailTestimony(Long id) {

        if (!repository.existsById(id)) throw new EntityNotFoundException( "Testimony not found:" + id);
        var testimony = repository.getReferenceById(id);
        return new TestimonyDetailData(testimony);

    }

    public Page<TestimonyListData> list3Testimony(Pageable pagination) {

        return repository.randomTestimony(pagination).<TestimonyListData>map(TestimonyListData::new);

    }

    public Object deleteTestimony(Long id) {

        if (!repository.existsById(id)) throw new EntityNotFoundException( "Testimony not found:" + id);
        repository.deleteById(id);
        return null;

    }
}
