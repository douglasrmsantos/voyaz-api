package io.github.dsjdevelopment.voyaz.api.domain.testimony;

public record TestimonyListData(Long id, String photo, String textTestimony, String namePersonTestimony) {

    public TestimonyListData(Testimony testimony) {
        this(testimony.getId(), testimony.getPhoto(), testimony.getTextTestimony(), testimony.getNamePersonTestimony());

    }
}
