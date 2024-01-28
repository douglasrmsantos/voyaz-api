package io.github.dsjdevelopment.voyaz.api.service;

import io.github.dsjdevelopment.voyaz.api.domain.testimony.*;
import io.github.dsjdevelopment.voyaz.api.infra.exception.ExceptionValidation;
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
        existsTestimony(data.id());
        var testimony = repository.getReferenceById(data.id());
        testimony.dataUpdate(data);
        return new TestimonyDetailData(testimony);
    }

    public TestimonyDetailData detailTestimony(Long id) {
        existsTestimony(id);
        var testimony = repository.getReferenceById(id);
        return new TestimonyDetailData(testimony);
    }

    public void deleteTestimony(Long id) {
        existsTestimony(id);
        repository.deleteById(id);
    }

    public Page<TestimonyListData> list3Testimony(Pageable pagination) {
        return repository.randomTestimony(pagination).<TestimonyListData>map(TestimonyListData::new);
    }

    private void existsTestimony(Long id) {
        if (!repository.existsById(id)) throw new ExceptionValidation("No testimony found");
    }

}
