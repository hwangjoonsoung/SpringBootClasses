package me.hwangjoonsoung.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.domain.Article;
import me.hwangjoonsoung.springbootdeveloper.dto.AddArticleRequest;
import me.hwangjoonsoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.hwangjoonsoung.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest addArticleRequest , String userName) {

        return blogRepository.save(addArticleRequest.toEntity(userName));

    }

    public List<Article> getAllArticles (){
        List<Article> articles = blogRepository.findAll();
        return articles;
    }

    public Article getArticle(Long id){
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found id : " + id));
        return article;
    }

    public void deleteArticleById(Long id){
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not fount : " + id));
        authorizeArticleAuthor(article);
        blogRepository.deleteById(id);
    }

    private static void authorizeArticleAuthor(Article article) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!name.equals(article.getAuthor())){
            throw new IllegalArgumentException("not authorized");
        }
    }

    @Transactional
    public Article editArticle(Long id , UpdateArticleRequest request){
        Article article = blogRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("not found id : " + id);
        });

        authorizeArticleAuthor(article);
        article.updateArticle(request.getTitle() , request.getContent());

        return article;
    }

}
