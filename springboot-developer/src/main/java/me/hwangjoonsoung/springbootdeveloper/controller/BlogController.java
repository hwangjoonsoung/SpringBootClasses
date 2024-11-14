package me.hwangjoonsoung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.domain.Article;
import me.hwangjoonsoung.springbootdeveloper.dto.AddArticleRequest;
import me.hwangjoonsoung.springbootdeveloper.dto.ArticleResponse;
import me.hwangjoonsoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.hwangjoonsoung.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticle(){
//        List<Article> allArticles = blogService.getAllArticles();
//        return ResponseEntity.status(HttpStatus.OK).body(allArticles);
        List<ArticleResponse> list = blogService.getAllArticles().stream().map(ArticleResponse::new).toList();

        return ResponseEntity.ok().body(list);

    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal){
        Article save = blogService.save(request,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Long id){
        Article article = blogService.getArticle(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> removeArticleById(@PathVariable Long id){
        blogService.deleteArticleById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> editArticleById(@PathVariable Long id , @RequestBody UpdateArticleRequest request){
        Article article = blogService.editArticle(id, request);
        return ResponseEntity.ok().body(article);
    }
}
