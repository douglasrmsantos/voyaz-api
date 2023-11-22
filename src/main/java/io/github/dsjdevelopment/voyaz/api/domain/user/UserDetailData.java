package io.github.dsjdevelopment.voyaz.api.domain.user;

public record UserDetailData(Long id, String email, Boolean active) {

        public UserDetailData(User user) {
            this(user.getId(), user.getUsername(), user.getActive());
        }
}
