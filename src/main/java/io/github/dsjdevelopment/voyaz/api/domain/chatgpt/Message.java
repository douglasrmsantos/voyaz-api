package io.github.dsjdevelopment.voyaz.api.domain.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Message {

    private String role;
    private String content;

}
