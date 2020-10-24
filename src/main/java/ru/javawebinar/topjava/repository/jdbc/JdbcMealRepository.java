package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {
//https://alexkosarev.name/2016/06/13/spring-framework-jdbctemplate/

    RowMapper<Meal> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        int id = resultSet.getInt("id");
        LocalDateTime dateTime = LocalDateTime.parse(resultSet.getString("datetime").substring(0, 19), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int calories = resultSet.getInt("calories");
        int userId = resultSet.getInt("user_id");
        String description = resultSet.getString("description");
        return new Meal(id, dateTime, description, calories);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        jdbcTemplate.update("INSERT INTO meals(user_id, description, datetime, calories) " +
                "VALUES (?,?,?,?)", userId, meal.getDescription(), meal.getDateTime(), meal.getCalories());
        int seq = Integer.parseInt(
                jdbcTemplate.query("SELECT * FROM global_seq", (rse, rowNum) -> rse.getString("last_value")).get(0));
        return get(seq, userId);
    }

    @Override
    public boolean delete(int id, int userId) {
        int result = jdbcTemplate.update("DELETE FROM meals WHERE id =? AND user_id = ?", id, userId);
        return result > 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result = null;
        try {
            result = jdbcTemplate.queryForObject("SElECT * FROM meals WHERE id=?", new Object[]{id}, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            //LOG not found
            return null;
        }
        int userIdFromDb = jdbcTemplate.query("SELECT user_id FROM meals WHERE id=?"
                , new Object[]{id}
                , (ResultSet rs, int rowInt) -> rs.getInt("user_id")).get(0);
        return userId == userIdFromDb ? result : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ?", new Object[]{userId}, ROW_MAPPER);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        List<Meal> result = jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? AND datetime >= ? AND datetime < ?", new Object[]{userId, startDateTime, endDateTime}, ROW_MAPPER);
        return null;
    }
}
