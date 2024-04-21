package com.example.spotifywrapped;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;

import java.util.concurrent.ExecutionException;

public class LLMChat {
    public static String gemini(String prompt) throws ExecutionException, InterruptedException {
        // For text-only input, use the gemini-pro model
        GenerativeModel gm = new GenerativeModel(/* modelName */ "gemini-pro",
                /* apiKey */ "AIzaSyDnLSCN0ChkE2cRVxrM33f-iv_4v8bVhAs"); // Replace with your actual API key

        GenerativeModelFutures model = GenerativeModelFutures.from(gm);
        Content content = new Content.Builder()
                .addText(prompt)
                .build();

        // Create a synchronous call to get the response instead of Futures
        GenerateContentResponse response = model.generateContent(content).get();

        if (response == null) {
            return "Error: response is null";
        }

        return response.getText();
    }

}
