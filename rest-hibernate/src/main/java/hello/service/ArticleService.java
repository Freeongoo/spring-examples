package hello.service;

import java.util.List;
import hello.dao.IArticleDAO;
import hello.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService implements IArticleService {
	@Autowired
	private IArticleDAO articleDAO;

    @Transactional
	@Override
	public Article getArticleById(int articleId) {
		Article obj = articleDAO.getArticleById(articleId);
		return obj;
	}

    @Transactional
	@Override
	public List<Article> getAllArticles(){
		return articleDAO.getAllArticles();
	}

    @Transactional
	@Override
	public boolean addArticle(Article article){
       if (articleDAO.articleExists(article.getTitle(), article.getCategory())) {
    	   return false;
       } else {
    	   articleDAO.addArticle(article);
    	   return true;
       }
	}

    @Transactional
	@Override
	public void updateArticle(Article article) {
		articleDAO.updateArticle(article);
	}

    @Transactional
	@Override
	public void deleteArticle(int articleId) {
		articleDAO.deleteArticle(articleId);
	}
}
