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

    @Autowired
    private ChatService chatService;

    public DestinyDetailData registerDestiny(DestinyRegistrationData data) {
        String description = data.descriptiveText();
        if(description == null || description.isEmpty()) {
            description = chatService.chat(String.format("""
                    Give a summary of %s, emphasizing why this place is incredible. Use informal language
                    and up to 100 characters maximum in each paragraph. Create 2 paragraphs in this summary.
                    """, data.name()));
        }
        var destiny = new Destiny(data);
        destiny.setDescriptiveText(description);
        repository.save(destiny);
        return new DestinyDetailData(destiny);
    }

    public DestinyDetailData updateDestiny(DestinyUpdateData data) {
        existsDestiny(data.id());
        var destiny = repository.getReferenceById(data.id());
        destiny.dataUpdate(data);
        return new DestinyDetailData(destiny);
    }

    public DestinyDetailData detailDestiny(Long id) {
        existsDestiny(id);
        var destiny = repository.getReferenceById(id);
        return new DestinyDetailData(destiny);
    }

    public void deleteDestiny(Long id) {
        existsDestiny(id);
        repository.deleteById(id);
    }

    public Page<DestinyListData> search(String name, Pageable pagination) {
        if (!repository.existsByName(name)) throw new ExceptionValidation("No destinations found with this name");
        return repository.findAllByName(name ,pagination).<DestinyListData>map(DestinyListData::new);
    }

    private void existsDestiny(Long id) {
        if (!repository.existsById(id)) throw new ExceptionValidation("No destination found");
    }
}
