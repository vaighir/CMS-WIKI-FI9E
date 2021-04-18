package com.fi9e.rest.daotests;

import com.fi9e.rest.dao.ArticleDao;
import com.fi9e.rest.entity.Article;

public class ArticleDaoTest {
	public static void main(String[] args) {
		ArticleDao ad = new ArticleDao();

//		ad.createArticle("sluggy", "this is a very nice and interesting article on another important subject. Probably has a lot of maths in it");

		Article a1 = ad.getArticleById(2);

//		System.out.println(u1);

		a1.setContent("Setting back the very interesting content with a lot of maths");

		ad.updateArticle(a1);
		
		ad.deleteArticleById(1);
	}

}