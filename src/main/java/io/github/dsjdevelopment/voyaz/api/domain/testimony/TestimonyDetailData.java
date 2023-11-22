package io.github.dsjdevelopment.voyaz.api.domain.testimony;


public record TestimonyDetailData(Long id, String photo, String textTestimony, String namePersonTestimony) {

    public TestimonyDetailData(Testimony testimony) {
        this(testimony.getId(), testimony.getPhoto(), testimony.getTextTestimony(), testimony.getNamePersonTestimony());
    }

}
