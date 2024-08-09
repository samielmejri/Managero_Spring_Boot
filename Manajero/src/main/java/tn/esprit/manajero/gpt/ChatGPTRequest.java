package tn.esprit.manajero.gpt;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequest {

    private String model;
    private List<Message> messages;

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system",
                "**Generate a hint for a  question based on the given prompt, ensuring that the hint is clear but not answering the question directly  :**\n" +
                        "\n" +
                        "**PROMPT:** " + prompt + "\n" +
                        "\n"

        ));
        this.messages.add(new Message("user", prompt));
    }
}
