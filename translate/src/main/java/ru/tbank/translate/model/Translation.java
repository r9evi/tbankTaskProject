package ru.tbank.translate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a translation entity.
 * It contains information about the original text, translated text, and other related details.
 *
 * @author Your Name
 * @version 1.0
 * @since 2023-04-01
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Translation {

    /**
     * Unique identifier for the translation.
     */
    private int id;

    /**
     * IP address associated with the translation.
     */
    private String ip;

    /**
     * The original text that was translated.
     */
    private String text;

    /**
     * The original text before translation.
     */
    private String originalText;

    /**
     * The translated text.
     */
    private String translatedText;
}
