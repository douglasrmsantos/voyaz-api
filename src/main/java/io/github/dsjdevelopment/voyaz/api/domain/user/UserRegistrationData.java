package io.github.dsjdevelopment.voyaz.api.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationData(@NotBlank String login, @NotBlank String password) {

}
