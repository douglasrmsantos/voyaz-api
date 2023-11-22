package io.github.dsjdevelopment.voyaz.api.domain.testimony;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TestimonyUpdateData(@NotNull Long id, @NotBlank String photo, @NotBlank String textTestimony,
                                  @NotBlank String namePersonTestimony) {
}
