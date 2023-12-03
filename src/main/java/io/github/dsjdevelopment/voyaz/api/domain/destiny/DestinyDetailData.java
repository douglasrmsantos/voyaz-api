package io.github.dsjdevelopment.voyaz.api.domain.destiny;


import java.math.BigDecimal;

public record DestinyDetailData(Long id, String photo, String name, BigDecimal price) {

    public DestinyDetailData(Destiny destiny) {
        this(destiny.getId(), destiny.getPhoto(), destiny.getName(), destiny.getPrice());
    }

}
