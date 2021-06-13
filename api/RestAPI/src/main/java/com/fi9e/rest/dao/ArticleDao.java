package com.fi9e.rest.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fi9e.rest.dto.ArticleDTO;
import com.fi9e.rest.entity.Article;

public class ArticleDao {

	final String HIBERNATE_CONFIG_PATH = "hibernate.cfg.xml";
	
	public Article getArticleById(int id) {
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.getCurrentSession();

		Article article = null;

		try {
			session.beginTransaction();
			article = session.get(Article.class, id);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
			factory.close();
		}

		return article;
	}

	public int createArticle(String name, String slug, String content, int categoryId) {
		
		System.out.println("starting createArticle");
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		System.out.println("startingSession");

		Article article = new Article(name, slug, content, categoryId, new java.util.Date(), new java.util.Date());
		
		int newArticleId = -1;
		try {
			session.beginTransaction();
			
			System.out.println("began transaction");
			
			newArticleId = (int) session.save(article);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			//@TODO: add throw exception on failure.
		} finally {
			if ( session.isOpen()) {
				session.close();
			}
			factory.close();
		}
		
		return newArticleId;
	}
	
	
	public int createArticle(ArticleDTO dto) {
		return createArticle(dto.getName(), dto.getSlug(), dto.getContent(), dto.getCategory().getId());
	}
	
	public void updateArticle(Article article) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.getCurrentSession();

		Article oldArticle = getArticleById(article.getId());
		
		oldArticle.setName(article.getName());
		oldArticle.setContent(article.getContent());
		oldArticle.setSlug(article.getSlug());
		oldArticle.setUpdatedAt(new java.util.Date());
		oldArticle.setCategoryId(article.getCategory());

		try {
			session.beginTransaction();
			session.update(oldArticle);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( session.isOpen()) {
				session.close();
			}
			factory.close();
		}
	}

	public Boolean deleteArticleById(int id) {

		Article article = getArticleById(id);
		
		if(article == null) {
			return false;
		}
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			session.delete(article);
			session.getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		} finally {
			if ( session.isOpen()) {
				session.close();
			}
			factory.close();
		}
	}
	
	
	public List<?> getAllArticles() {
		List<?> articlesRaw = new ArrayList<>();
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			articlesRaw = session.createSQLQuery("SELECT * FROM article").addEntity(Article.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( session.isOpen()) {
				session.close();
			}
			factory.close();
		}
		
		return articlesRaw;
	}
	
	public List<?> getAllArticlesByCategoryId(final int categoryId) {
		List<?> articlesRaw = new ArrayList<>();
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			articlesRaw = session.createSQLQuery("SELECT * FROM article WHERE category_id = " + categoryId).addEntity(Article.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( session.isOpen()) {
				session.close();
			}
			factory.close();
		}
		
		return articlesRaw;
	}
}
