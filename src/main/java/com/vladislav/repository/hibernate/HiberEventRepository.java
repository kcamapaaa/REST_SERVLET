package com.vladislav.repository.hibernate;

import com.vladislav.model.Event;
import com.vladislav.repository.EventRepository;
import com.vladislav.util.HiberUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HiberEventRepository implements EventRepository {
    @Override
    public Event getById(Integer id) {
        Event event = null;
        try (Session session = HiberUtil.getOpenedSession()) {
            event = session.find(Event.class, id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return event;
    }

    @Override
    public List<Event> getAll() {
        List<Event> eventList = null;
        try (Session session = HiberUtil.getOpenedSession()) {
            Query<Event> query = session.createQuery("FROM Event");
            eventList = query.getResultList();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return eventList;
    }

    @Override
    public Event save(Event event) {
        try(Session session = HiberUtil.getOpenedSession()) {
            session.beginTransaction();
            session.save(event);
            session.getTransaction().commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return event;
    }

    @Override
    public Event update(Event event) {
        try(Session session = HiberUtil.getOpenedSession()) {
            session.beginTransaction();
            Event updatedEvent = session.find(Event.class, event.getId());
            if(updatedEvent == null) {
                return null;
            }
            updatedEvent.setName(event.getName());
            session.update(updatedEvent);
            session.getTransaction().commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return event;
    }

    @Override
    public boolean deleteById(Integer id) {
        try(Session session = HiberUtil.getOpenedSession()) {
            session.beginTransaction();
            Event deletedEvent = session.find(Event.class, id);
            if(deletedEvent == null) {
                return false;
            }
            session.delete(deletedEvent);
            session.getTransaction().commit();
        }
        return true;
    }
}
