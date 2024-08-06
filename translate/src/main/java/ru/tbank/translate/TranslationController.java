package ru.tbank.translate;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tbank.translate.dto.TranslationDTO;
import ru.tbank.translate.service.TranslationService;

/**
 * Controller for translation requests.
 */
@RestController
@RequestMapping("/api/translate")
public class TranslationController {

    /**
     * Autowired instance of the TranslationService.
     */
    @Autowired
    private TranslationService translationService;

    /**
     * Translates a request to a specified target language.
     *
     * @param originalText The text to be translated.
     * @param sourceLang   The source language of the text.
     * @param targetLang   The target language to translate the text to.
     * @param request      The HTTP request.
     * @return A ResponseEntity with the translated text.
     */
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Переведенный текст"),
            @ApiResponse(responseCode = "400", description = "Не найден язык исходного сообщения"),
            @ApiResponse(responseCode = "404", description = "Не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка доступа к ресурсу перевода")
    })
    public ResponseEntity<String> translateRequest(@RequestParam String originalText, @RequestParam String sourceLang, @RequestParam String targetLang, HttpServletRequest request) {
        // Translate the text using the TranslationService.
        String translatedText = translationService.translateText(originalText, sourceLang, targetLang, request.getRemoteAddr());

        // Process the translation request using the TranslationService.
        translationService.processTranslationRequest(request.getRemoteAddr(), originalText, translatedText);

        // Return the translated text as a ResponseEntity.
        return ResponseEntity.ok("http 200 " + translatedText);
    }

    /**
     * Retrieves a translation record by its ID.
     *
     * @param id The ID of the translation record.
     * @return A ResponseEntity with the translation record details.
     */
    @GetMapping("/record")
    public ResponseEntity<String> getRecordRequest(@RequestParam int id) {
        TranslationDTO translation = translationService.getTranslation(id);
        return ResponseEntity.ok("Ip: " + translation.getIp() + "\nOriginal text: " + translation.getOriginalText() + "\nTranslated text: " + translation.getTranslatedText());
    }
}