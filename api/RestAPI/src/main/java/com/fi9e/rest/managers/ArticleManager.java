package com.fi9e.rest.managers;

import java.util.ArrayList;
import java.util.List;

import com.fi9e.rest.dao.ArticleDao;
import com.fi9e.rest.dto.ArticleDTO;
import com.fi9e.rest.entity.Article;
import com.fi9e.rest.mappers.ArticleMapper;

/**
 * 
 * @author Christopher
 *
 */
public class ArticleManager {

	private ArticleDao dao;

	private ArticleDao getDao() {
		if (this.dao == null) {
			this.dao = new ArticleDao();
		}

		return this.dao;
	}

	/**
	 * Create an article in DB and return created object as DTO
	 * 
	 * @param article
	 * @return
	 */
	public ArticleDTO createArticle(ArticleDTO article) {

		int newArticleId = this.getDao().createArticle(article);

		Article newArticle = this.getDao().getArticleById(newArticleId);

		ArticleDTO articleDTO = ArticleMapper.mapArticleToArticleDTO(newArticle);

		return articleDTO;
	}

	/**
	 * Get specific article by id
	 * 
	 * @param id
	 * @return
	 */
	public ArticleDTO getArticleById(final int id) {

		Article article = this.getDao().getArticleById(id);

		ArticleDTO dto = ArticleMapper.mapArticleToArticleDTO(article);

		return dto;
	}

	/**
	 * Get specific article by id
	 * 
	 * @param id
	 * @return
	 */
	public ArticleDTO getArticleById(final String id) {
		return this.getArticleById(Integer.parseInt(id));
	}

	/**
	 * Update Single Article
	 * 
	 * @param articleDTO
	 * @return
	 */
	public ArticleDTO updateArticle(final ArticleDTO articleDTO) {

		Article article = this.getDao().getArticleById(articleDTO.getId());

		this.getDao().updateArticle(article);

		return ArticleMapper.mapArticleToArticleDTO(this.getDao().getArticleById(articleDTO.getId()));
	}

	public List<ArticleDTO> getAllArticles() {
		
		List<?> articles = getDao().getAllArticles();

		List<ArticleDTO> dtoList = new ArrayList<ArticleDTO>();

		for (Object article : articles) {
			dtoList.add(ArticleMapper.mapArticleToArticleDTO((Article) article));
		}

		return dtoList;
	}

}
