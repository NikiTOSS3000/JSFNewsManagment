package com.epam.jsf.database;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.jsf.model.News;
import com.epam.jsf.util.SQLQueryUtil;

public class NewsDAOJDBCImpl extends AbstractDAO implements INewsDAO {

    @Autowired
    protected NewsDAOJDBCImpl(DataSource s) {
        super(s);
    }

    @Override
    public List<News> getList() {
        final String GET_LIST = "SELECT id,title,data,brief,content FROM News order by data desc";
        List<News> newsList = new ArrayList<News>();
        try {
            if (createStatement()) {
                if (executeSQL(GET_LIST)) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String title = resultSet.getString(2);
                        java.util.Date date = new java.util.Date(resultSet.getDate(3).getTime());
                        String brief = resultSet.getString(4);
                        String content = resultSet.getString(5);
                        News news = new News(id, title, date, brief, content);
                        newsList.add(news);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            closeAll();
        }
        return newsList;
    }

    @Override
    public int save(News news) {
        String NEXT_VAL = "select auto_incr.nextval from dual";
        String SAVE = "INSERT INTO NEWS (TITLE, DATA, BRIEF, CONTENT, ID) VALUES (?,?,?,?,?)";
        int id = news.getId();
        boolean success = false;
        try {
            if (createStatement()) {
                if (executeSQL(NEXT_VAL)) {
                    if (resultSet.next()) {
                        id = resultSet.getInt(1);
                    }
                }
            }
            closeAll();
            if (prepareStatement(SAVE)) {
                preparedStatement.setString(1, news.getTitle());
                preparedStatement.setDate(2, new Date(news.getDate().getTime()));
                preparedStatement.setString(3, news.getBrief());
                preparedStatement.setString(4, news.getContent());
                preparedStatement.setInt(5, id);
                success = executeUpdate();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            closeAll();
        }
        if (success) {
            return id;
        } else {
            return 0;
        }
    }

    @Override
    public boolean update(News news) {
        String UPDATE = "UPDATE News SET TITLE=?, DATA=?, BRIEF=?, CONTENT=? WHERE ID=?";
        boolean success = false;
        try {
            if (prepareStatement(UPDATE)) {
                preparedStatement.setString(1, news.getTitle());
                preparedStatement.setDate(2, new Date(news.getDate().getTime()));
                preparedStatement.setString(3, news.getBrief());
                preparedStatement.setString(4, news.getContent());
                preparedStatement.setInt(5, news.getId());
                success = executeUpdate();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            closeAll();
        }
        return success;
    }

    @Override
    public boolean remove(int id) {
        final String REMOVE = "DELETE FROM NEWS WHERE ID=?";
        boolean success = false;
        try {
            if (prepareStatement(REMOVE)) {
                preparedStatement.setInt(1, id);
                success = executeUpdate();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return success;
    }

    @Override
    public boolean remove(News news) {
        return remove(news.getId());
    }

    @Override
    public boolean remove(Integer[] ids) {
        int length = 0;
        for (Integer id : ids) {
            if (id != null) {
                length++;
            }
        }
        String qs = SQLQueryUtil.generateQsForIn(length);
        String REMOVE = String.format("DELETE FROM NEWS WHERE ID IN (%s)", qs);
        boolean success = false;
        try {
            if (prepareStatement(REMOVE)) {
                int i = 1;
                for (Integer id : ids) {
                    if (id != null) {
                        preparedStatement.setInt(i++, id);
                    }
                }
                success = executeUpdate();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return success;
    }

    @Override
    public News fetchById(int id) {
        final String BY_ID = "SELECT title,data,brief,content FROM News WHERE id=?";
        News news = null;
        try {
            if (prepareStatement(BY_ID)) {

                preparedStatement.setInt(1, id);
                if (executeQuery()) {
                    if (resultSet.next()) {
                        String title = resultSet.getString(1);
                        Date date = resultSet.getDate(2);
                        String brief = resultSet.getString(3);
                        String content = resultSet.getString(4);
                        news = new News(id, title, date, brief, content);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            closeAll();
        }
        return news;
    }
}