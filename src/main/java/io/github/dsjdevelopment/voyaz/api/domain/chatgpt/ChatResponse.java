package io.github.dsjdevelopment.voyaz.api.domain.chatgpt;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatResponse {

    private List<Choice> choices;

    @Getter
    public static class Choice {
        private int index;
        private Message message;
    }
}

