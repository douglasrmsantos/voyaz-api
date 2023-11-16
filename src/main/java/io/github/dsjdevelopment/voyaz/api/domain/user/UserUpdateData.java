package io.github.dsjdevelopment.voyaz.api.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateData(@NotNull Long id, @NotBlank String login, @NotBlank String password) {

    public UserUpdateData(User user) {
        this(user.getId(), user.getLogin(), user.getPassword());

    }
}
