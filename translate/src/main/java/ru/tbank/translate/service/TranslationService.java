package ru.tbank.translate.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.tbank.translate.dto.TranslationDTO;
import ru.tbank.translate.mapper.TranslationMapper;
import ru.tbank.translate.model.Translation;
import ru.tbank.translate.repository.TranslationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Service for managing translations.
 */
@Service
public class TranslationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String iamToken = System.getenv("IAM_TOKEN");
    private final String folderId = System.getenv("FOLDER_ID");
    private final String apiUrl = "https://translate.api.cloud.yandex.net/translate/v2/translate";

    private final TranslationRepository translationRepository;

    public TranslationService(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    /**
     * Retrieves all translations from the repository.
     *
     * @return List of all translations.
     */
    public List<TranslationDTO> getAllTranslations() {
        return translationRepository.getAll().stream()
                .map(TranslationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new translation to the repository.
     *
     * @param dto TranslationDTO to be saved.
     */
    public void saveTranslation(TranslationDTO dto) {
        Translation translation = TranslationMapper.toEntity(dto);
        translationRepository.save(translation);
    }

    /**
     * Updates a translation in the repository.
     *
     * @param id  Index of the translation to be updated.
     * @param dto Updated TranslationDTO.
     */
    public void updateTranslation(int id, TranslationDTO dto) {
        Translation translation = TranslationMapper.toEntity(dto);
        translation.setId(id);
        translationRepository.update(translation);
    }

    /**
     * Deletes a translation from the repository.
     *
     * @param id Index of the translation to be deleted.
     */
    public void deleteTranslation(int id) {
        translationRepository.deleteById(id);
    }

    public TranslationDTO getTranslation(int id) {
        return TranslationMapper.toDTO(translationRepository.findById(id));
    }

    /**
     * Translates a given text from one language to another.
     *
     * @param text       Text to be translated.
     * @param sourceLang Source language code.
     * @param targetLang Target language code.
     * @param ipAddress  IP address of the request.
     * @return Translated text.
     */
    public String translateText(String text, String sourceLang, String targetLang, String ipAddress) {
        String[] words = text.split("\\s+");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> futures = new ArrayList<>();
        try {
            for (String word : words) {
                futures.add(executor.submit(() -> translateWord(word, sourceLang, targetLang)));
            }

            StringBuilder translatedText = new StringBuilder();
            for (Future<String> future : futures) {
                try {
                    translatedText.append(future.get()).append(" ");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return translatedText.toString().trim();
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Translates a single word from one language to another.
     *
     * @param word       Word to be translated.
     * @param sourceLang Source language code.
     * @param targetLang Target language code.
     * @return Translated word.
     * @throws JSONException If there is an error parsing the JSON response.
     */
    private String translateWord(String word, String sourceLang, String targetLang) throws JSONException {
        JSONObject body = new JSONObject();
        body.put("sourceLanguageCode", sourceLang);
        body.put("targetLanguageCode", targetLang);
        body.put("texts", new JSONArray().put(word));
        body.put("folderId", folderId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + iamToken);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray translationsArray = jsonResponse.getJSONArray("translations");

        StringBuilder translatedText = new StringBuilder();
        for (int i = 0; i < translationsArray.length(); i++) {
            JSONObject translationObject = translationsArray.getJSONObject(i);
            translatedText.append(translationObject.getString("text")).append(" ");
        }

        return translatedText.toString().trim();
    }


    /**
     * Asynchronously saves a translation request to the repository.
     *
     * @param ipAddress      IP address of the request.
     * @param originalText   Original text.
     * @param translatedText Translated text.
     */
    @Async
    public void processTranslationRequest(String ipAddress, String originalText, String translatedText) {
        TranslationDTO translationDTO = new TranslationDTO(ipAddress, originalText, translatedText);
        saveTranslation(translationDTO);
    }
}
