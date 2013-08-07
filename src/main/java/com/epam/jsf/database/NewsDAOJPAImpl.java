package com.epam.jsf.database;

import java.util.List;

import com.epam.jsf.model.News;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class NewsDAOJPAImpl implements INewsDAO {

    final static Logger logger = Logger.getLogger("com.epam.jsf.database");
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<News> getList() {
        List<News> list = entityManager.createNamedQuery("getList").getResultList();
        return list;
    }

    @Override
    public int save(News news) {
        entityManager.persist(news);
        return news.getId();
    }

    @Override
    public boolean update(News news) {
        entityManager.merge(news);
        return true;
    }

    @Override
    public boolean remove(News news) {
        entityManager.remove(news);
        return true;
    }

    @Override
    public boolean remove(int id) {
        News news = entityManager.find(News.class, id);
        entityManager.remove(news);
        return true;
    }

    @Override
    public boolean remove(Integer[] ids) {
        List<Integer> list = new ArrayList<Integer>(ids.length);
        for (Integer i : ids) {
            if (i != null) {
                list.add(i);
            }
        }
        entityManager.createNamedQuery("deleteList")
                .setParameter("list", list).executeUpdate();
        return true;
    }

    @Override
    public News fetchById(int id) {
        News answer = entityManager.find(News.class, id);
        return answer;
    }
}