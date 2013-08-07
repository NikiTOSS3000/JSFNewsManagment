package com.epam.jsf.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.jsf.model.News;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class NewsDAOHibernateImpl implements INewsDAO {

    final static Logger logger = Logger.getLogger("com.epam.jsf.database");
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<News> getList() {
        Session session = sessionFactory.getCurrentSession();
        List<News> answer = session.getNamedQuery("getList").list();
        return answer;
    }

    @Override
    public int save(News news) {
        int answer = 0;
        Session session = sessionFactory.getCurrentSession();
        session.save(news);
        answer = news.getId();
        return answer;
    }

    @Override
    public boolean update(News news) {
        Session session = sessionFactory.getCurrentSession();
        session.update(news);
        return true;
    }

    @Override
    public boolean remove(News news) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(news);
        return true;
    }

    @Override
    public boolean remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        News answer = (News) session.createCriteria(News.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        session.delete(answer);
        return true;
    }

    @Override
    public boolean remove(Integer[] ids) {
        Session session = sessionFactory.getCurrentSession();
        session.getNamedQuery("deleteList").setParameterList("list", ids).executeUpdate();
        return true;
    }

    @Override
    public News fetchById(int id) {
        Session session = sessionFactory.getCurrentSession();
        News answer = (News) session.get(News.class, id);
        return answer;
    }
}