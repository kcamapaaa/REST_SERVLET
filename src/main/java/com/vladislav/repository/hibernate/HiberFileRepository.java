package com.vladislav.repository.hibernate;

import com.vladislav.model.File;
import com.vladislav.repository.FileRepository;
import com.vladislav.util.HiberUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HiberFileRepository implements FileRepository {
    @Override
    public File getById(Integer id) {
        File file = null;
        try (Session session = HiberUtil.getOpenedSession()) {
            Query<File> query = session.createQuery("FROM File WHERE id = :id");
            query.setParameter("id", id);
            file = query.getSingleResult();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return file;
    }

    @Override
    public List<File> getAll() {
        List<File> files = null;
        try (Session session = HiberUtil.getOpenedSession()) {
            Query<File> query = session.createQuery("FROM File");
            files = query.getResultList();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return files;
    }

    @Override
    public File save(File file) {
        try (Session session = HiberUtil.getOpenedSession()) {
            session.beginTransaction();
            session.save(file);
            session.getTransaction().commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return file;
    }

    @Override
    public File update(File file) {
        try (Session session = HiberUtil.getOpenedSession()) {
            session.beginTransaction();
            File updatedFile = session.find(File.class, file.getId());
            if (updatedFile == null) {
                return null;
            }
            updatedFile.setFileName(file.getFileName());
            session.update(updatedFile);
            session.getTransaction().commit();
        }
        return file;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (Session session = HiberUtil.getOpenedSession()) {
            session.beginTransaction();
            File deletedFile = session.find(File.class, id);
            if (deletedFile == null) {
                return false;
            }
            session.delete(deletedFile);
            session.getTransaction().commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return true;
    }
}
