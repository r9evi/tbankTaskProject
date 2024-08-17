package ru.tbank.translate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tbank.translate.model.Translation;
import ru.tbank.translate.dto.TranslationDTO;

/**
 * The {@code TranslationMapper} class provides methods for mapping between {@link Translation} and {@link TranslationDTO} objects.
 */
@Mapper
public interface TranslationMapper {

    TranslationMapper INSTANCE = Mappers.getMapper(TranslationMapper.class);
    /**
     * Converts a {@link Translation} object to a {@link TranslationDTO} object.
     *
     * @param translation the {@link Translation} object to convert
     * @return the converted {@link TranslationDTO} object
     */
    TranslationDTO toDTO(Translation translation);

    /**
     * Converts a {@link TranslationDTO} object to a {@link Translation} object.
     *
     * @param dto the {@link TranslationDTO} object to convert
     * @return the converted {@link Translation} object
     */
    Translation toEntity(TranslationDTO dto);
}
