package io.github.dsjdevelopment.voyaz.api.service;

import io.github.dsjdevelopment.voyaz.api.domain.destiny.*;
import io.github.dsjdevelopment.voyaz.api.infra.exception.ExceptionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DestinyService {

    @Autowired
    private DestinyRepository repository;

    public DestinyDetailData registerDestiny(DestinyRegistrationData data) {

        var destiny = new Destiny(data);
        repository.save(destiny);
        return new DestinyDetailData(destiny);

    }

    public DestinyDetailData updateDestiny(DestinyUpdateData data) {

        var destiny = repository.getReferenceById(data.id());
        destiny.dataUpdate(data);
        return new DestinyDetailData(destiny);

    }

    public DestinyDetailData detailDestiny(Long id) {

        var destiny = repository.getReferenceById(id);
        return new DestinyDetailData(destiny);

    }

    public void deleteDestiny(Long id) {

        repository.deleteById(id);

    }

    public Page<DestinyListData> search(String name, Pageable pagination) {

        if (!repository.existsByName(name)) throw new ExceptionValidation("No destinations found");
        return repository.findAllByName(name ,pagination).<DestinyListData>map(DestinyListData::new);

    }
}
