package com.epam.jsf.database;

import java.util.List;

import com.epam.jsf.model.News;

public interface INewsDAO {

    public List<News> getList();
    public int save(News news);
    public boolean update(News news);
    public boolean remove(News news);
    public boolean remove(int id);
    public boolean remove(Integer[] ids);
    public News fetchById(int id);
}
