package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()) { //new
            em.persist(meal);
            return meal;
        } else { // update
            if (em.getReference(Meal.class, meal.getId()).getUser().getId().equals(userId)) {
                return em.merge(meal);
            } else {
                return null;
            }
        }
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        Meal mealRef = em.find(Meal.class, id);
        if (mealRef != null && mealRef.getUser().getId().equals(userId)) {
            em.remove(em.getReference(Meal.class, id));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result = em.find(Meal.class, id);
        return computingOwnerId(result, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> result = em.createQuery("select m from Meal m where m.user.id=:usr").setParameter("usr",userId).getResultList();
        Collections.reverse(result);
        return result;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return null;
    }

    private Meal computingOwnerId(Meal result, int userId) {
        if (result != null && result.getUser().getId().equals(userId)) {
            return result;
        } else {
            return null;
        }
    }
}