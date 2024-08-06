package ru.tbank.translate.mapper;

import ru.tbank.translate.model.Translation;
import ru.tbank.translate.dto.TranslationDTO;

/**
 * The {@code TranslationMapper} class provides methods for mapping between {@link Translation} and {@link TranslationDTO} objects.
 */
public class TranslationMapper {

    /**
     * Converts a {@link Translation} object to a {@link TranslationDTO} object.
     *
     * @param translation the {@link Translation} object to convert
     * @return the converted {@link TranslationDTO} object
     */
    public static TranslationDTO toDTO(Translation translation) {
        TranslationDTO dto = new TranslationDTO();
        dto.setIp(translation.getIp());
        dto.setOriginalText(translation.getOriginalText());
        dto.setTranslatedText(translation.getTranslatedText());
        return dto;
    }

    /**
     * Converts a {@link TranslationDTO} object to a {@link Translation} object.
     *
     * @param dto the {@link TranslationDTO} object to convert
     * @return the converted {@link Translation} object
     */
    public static Translation toEntity(TranslationDTO dto) {
        Translation translation = new Translation();
        translation.setIp(dto.getIp());
        translation.setOriginalText(dto.getOriginalText());
        translation.setTranslatedText(dto.getTranslatedText());
        return translation;
    }
}
