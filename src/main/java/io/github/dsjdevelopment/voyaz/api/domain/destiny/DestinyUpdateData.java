package io.github.dsjdevelopment.voyaz.api.domain.destiny;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DestinyUpdateData(@NotNull Long id, @NotBlank String photo, @NotBlank String name,
                                @NotNull @Positive BigDecimal price) {
}
