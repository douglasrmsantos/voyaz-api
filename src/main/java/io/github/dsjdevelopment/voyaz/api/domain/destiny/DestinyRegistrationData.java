package io.github.dsjdevelopment.voyaz.api.domain.destiny;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DestinyRegistrationData(@NotBlank String photo1, @NotBlank String photo2, @NotBlank String name,
                                      @NotNull @Positive BigDecimal price, @NotBlank String goal, String descriptiveText) {

}