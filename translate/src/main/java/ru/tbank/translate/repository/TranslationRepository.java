package ru.tbank.translate.repository;

import ru.tbank.translate.model.Translation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository for managing {@link Translation} entities.
 */
@Repository
public class TranslationRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor for {@link TranslationRepository}.
     *
     * @param jdbcTemplate the JdbcTemplate to use for database operations.
     */
    public TranslationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all {@link Translation} entities from the database.
     *
     * @return a list of all {@link Translation} entities.
     */
    public List<Translation> getAll() {
        String sql = "SELECT id, ip, original_text, translated_text FROM translations";
        return jdbcTemplate.query(sql, new TranslationRowMapper());
    }

    public Translation findById(int id) {
        String sql = "SELECT id, ip, original_text, translated_text FROM translations WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new TranslationRowMapper(), id);
    }

    /**
     * Saves a new {@link Translation} entity to the database.
     *
     * @param translation the {@link Translation} entity to save.
     * @return the number of rows affected by the insert operation.
     */
    public int save(Translation translation) {
        String sql = "INSERT INTO translations (ip, original_text, translated_text) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, translation.getIp(), translation.getOriginalText(), translation.getTranslatedText());
    }

    /**
     * Updates an existing {@link Translation} entity in the database.
     *
     * @param translation the {@link Translation} entity to update.
     * @return the number of rows affected by the update operation.
     */
    public int update(Translation translation) {
        String sql = "UPDATE translations SET ip = ?, original_text = ?, translated_text = ? WHERE id = ?";
        return jdbcTemplate.update(sql, translation.getIp(), translation.getOriginalText(), translation.getTranslatedText(), translation.getId());
    }

    /**
     * Deletes a {@link Translation} entity from the database by its id.
     *
     * @param id the id of the {@link Translation} entity to delete.
     * @return the number of rows affected by the delete operation.
     */
    public int deleteById(int id) {
        String sql = "DELETE FROM translations WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * Private inner class that maps a {@link ResultSet} to a {@link Translation} entity.
     */
    private static class TranslationRowMapper implements RowMapper<Translation> {
        @Override
        public Translation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Translation translation = new Translation();
            translation.setId(rs.getInt("id"));
            translation.setIp(rs.getString("ip"));
            translation.setOriginalText(rs.getString("original_text"));
            translation.setTranslatedText(rs.getString("translated_text"));
            return translation;
        }
    }
}
