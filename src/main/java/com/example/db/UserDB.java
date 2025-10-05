package com.example.db;

import com.example.model.User;
import com.example.util.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.logging.Logger;

public class UserDB {
    private static final Logger LOGGER = Logger.getLogger(UserDB.class.getName());

    public static void insert(User user) {
        if (user == null) {
            LOGGER.severe("User object is null, cannot insert.");
            return;
        }
        EntityManager em = DBUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            LOGGER.info("Persisting user: " + user.getEmail());
            em.persist(user);
            em.flush(); // Ép ghi dữ liệu ngay
            trans.commit();
            LOGGER.info("User inserted successfully: " + user.getEmail());
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            LOGGER.severe("Failed to insert user: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void update(User user) {
        if (user == null || user.getId() == null) {
            LOGGER.severe("User object or ID is null, cannot update.");
            return;
        }
        EntityManager em = DBUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            LOGGER.info("Updating user: " + user.getEmail());
            User existingUser = em.find(User.class, user.getId());
            if (existingUser != null) {
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());
                em.merge(existingUser);
                em.flush(); // Ép ghi dữ liệu ngay
                trans.commit();
                LOGGER.info("User updated successfully: " + user.getEmail());
            } else {
                LOGGER.warning("User with ID " + user.getId() + " not found.");
                trans.rollback();
            }
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            LOGGER.severe("Failed to update user: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void delete(Long id) {
        if (id == null) {
            LOGGER.severe("ID is null, cannot delete.");
            return;
        }
        EntityManager em = DBUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            LOGGER.info("Deleting user with ID: " + id);
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
                em.flush(); // Ép ghi dữ liệu ngay
                trans.commit();
                LOGGER.info("User deleted successfully: ID " + id);
            } else {
                LOGGER.warning("User with ID " + id + " not found.");
                trans.rollback();
            }
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            LOGGER.severe("Failed to delete user: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}