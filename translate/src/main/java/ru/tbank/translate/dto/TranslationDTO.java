package ru.tbank.translate.dto;

import lombok.*;

/**
 * Data Transfer Object for {@link ru.tbank.translate.model.Translation} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TranslationDTO {
    /**
     * IP address of the client that made the translation request.
     */
    private String ip;
    /**
     * Original text of the translation request.
     */
    private String originalText;
    /**
     * Translated text of the translation request.
     */
    private String translatedText;
}

