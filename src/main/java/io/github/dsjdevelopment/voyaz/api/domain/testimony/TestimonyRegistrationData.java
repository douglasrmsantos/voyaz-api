package io.github.dsjdevelopment.voyaz.api.domain.testimony;

import jakarta.validation.constraints.NotBlank;

public record TestimonyRegistrationData(@NotBlank String photo, @NotBlank String textTestimony,
                                        @NotBlank String namePersonTestimony) {

}
