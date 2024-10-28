package me.hwangjoonsoung.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.domain.Article;
import me.hwangjoonsoung.springbootdeveloper.dto.AddArticleRequest;
import me.hwangjoonsoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.hwangjoonsoung.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest addArticleRequest) {

        return blogRepository.save(addArticleRequest.toEntity());

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
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article editArticle(Long id , UpdateArticleRequest request){
        Article article = blogRepository.findById(id).orElseThrow(() -> {
            System.out.println("id = " + id);
            return new IllegalArgumentException("not found id : " + id);
        });
        article.updateArticle(request.getTitle() , request.getContent());

        return article;
    }

}
