package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceTest {

    @Autowired
    private MealService service;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void get() {
        Meal actual = service.get(100002, 100000);
        Meal except = new Meal(100002, LocalDateTime.of(2015, 06, 01, 14, 00), "Админ ланч", 510);

        assertEquals(except.getId(), actual.getId());
        assertEquals(except.getDateTime().toString(), actual.getDateTime().toString());
        assertEquals(except.getDescription(), actual.getDescription());
        assertEquals(except.getCalories(), actual.getCalories());
    }

    @Test
    public void delete() {
        service.delete(100002, 100000);
        List<Meal> list = service.getAll(100000);
        assertEquals(list.size(), 1);
//        assertThrows(EmptyResultDataAccessException.class, () -> service.get(100002, 100000));
        assertThrows(NotFoundException.class, () -> service.get(100002, 100000));

    }

    @Test
    public void getBetweenInclusive() {
    }

    @Test
    public void getAll() {
        List<Meal> expect = new ArrayList<>(Arrays.asList(
                new Meal(100002, LocalDateTime.of(2015, 06, 01, 14, 00), "Админ ланч", 510),
                new Meal(100003, LocalDateTime.of(2015, 06, 01, 21, 00), "Админ ужин", 1500)
        ));


        List<Meal> actual = service.getAll(100000);
        assertEquals(2, actual.size());
        assertEquals(expect.get(0).getId(), actual.get(0).getId());
        assertEquals(expect.get(0).getCalories(), actual.get(0).getCalories());
        assertEquals(expect.get(0).getDescription(), actual.get(0).getDescription());
        assertEquals(expect.get(1).getId(), actual.get(1).getId());


    }

    @Test
    public void update() {
        Meal expect = new Meal(100002, LocalDateTime.of(2020, 10, 06, 19, 30), "Update Meal", 1488);
        service.update(expect, 100000);
        Meal actual = service.get(100002, 100000);
        assertEquals(expect.getId(), actual.getId());
        assertEquals(expect.getDateTime().toString(), actual.getDateTime().toString());
        assertEquals(expect.getDescription(), actual.getDescription());
        assertEquals(expect.getCalories(), actual.getCalories());
    }

    @Test
    public void create() {
        Meal toWrite = new Meal(LocalDateTime.now(), "Еда Junit", 1488);
        System.out.println(toWrite.getId() + "Expect NOT NULL");
        service.create(toWrite, 100000);
    }
}