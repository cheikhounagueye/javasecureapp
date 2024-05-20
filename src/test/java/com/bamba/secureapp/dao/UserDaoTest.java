package com.bamba.secureapp.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bamba.secureapp.dao.UserDao;  // Assurez-vous que ce chemin est correct
import com.bamba.secureapp.entities.UserEntity;

public class UserDaoTest {

    private UserDao userDao;
    private boolean result;

    @BeforeEach
    void init() {
        userDao = new UserDao();
    }

    @Test
    void testUserCrudOperations() {
        // Création d'un nouvel utilisateur
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("samane@seck.com");
        userEntity.setPassword("password123");

        // Ajouter un utilisateur
        result = userDao.add(userEntity);  // Assurez-vous que la méthode 'add' existe dans UserDao
        Assertions.assertTrue(result);

        // Lire l'utilisateur ajouté
        UserEntity retrievedUser = userDao.get(userEntity.getId());
        Assertions.assertEquals("samane@seck.com", retrievedUser.getEmail());
        Assertions.assertEquals("password123", retrievedUser.getPassword());

        // Mise à jour de l'utilisateur
        retrievedUser.setPassword("newpassword123");
        result = userDao.update(retrievedUser);  // Assurez-vous que la méthode 'update' existe dans UserDao
        Assertions.assertTrue(result);

        // Lire l'utilisateur mis à jour
        UserEntity updatedUser = userDao.get(retrievedUser.getId());
        Assertions.assertEquals("newpassword123", updatedUser.getPassword());

        // Supprimer l'utilisateur
        result = userDao.delete(updatedUser.getId());  // Assurez-vous que la méthode 'delete' existe dans UserDao
        Assertions.assertTrue(result);

        // Vérifier que l'utilisateur a été supprimé
        UserEntity deletedUser = userDao.get(updatedUser.getId());
        Assertions.assertNull(deletedUser);
    }
}
