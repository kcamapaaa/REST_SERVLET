package com.vladislav.repository.hibernate;

import com.vladislav.model.User;
import com.vladislav.repository.UserRepository;
import com.vladislav.util.HiberUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HiberUserRepository implements UserRepository {
    @Override
    public User getById(Integer id) {
        User user = null;
        try(Session session = HiberUtil.getOpenedSession()) {
            Query<User> query = session.createQuery("FROM User u LEFT JOIN FETCH u.eventList " +
                                                    "WHERE u.id = :id");
            query.setParameter("id", id);
            user = query.getSingleResult();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = null;
        try(Session session = HiberUtil.getOpenedSession()) {
            Query<User> query = session.createQuery("FROM User");
            users = query.getResultList();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User user) {
        try(Session session = HiberUtil.getOpenedSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return user;
    }

    @Override
    public User update(User user) {
        try(Session session = HiberUtil.getOpenedSession()) {
            User updatedUser = session.find(User.class, user.getId());
            if(updatedUser == null) {
                return null;
            }
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            session.update(updatedUser);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public boolean deleteById(Integer id) {
        try(Session session = HiberUtil.getOpenedSession()) {
            session.beginTransaction();
            User deletedUser = session.find(User.class, id);
            if(deletedUser == null) {
                return false;
            }
            session.delete(deletedUser);
            session.getTransaction().commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return true;
    }
}
