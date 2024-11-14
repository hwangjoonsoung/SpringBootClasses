package me.hwangjoonsoung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.domain.Article;
import me.hwangjoonsoung.springbootdeveloper.dto.ArticleListViewResponse;
import me.hwangjoonsoung.springbootdeveloper.dto.ArticleResponse;
import me.hwangjoonsoung.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model){
        List<ArticleListViewResponse> list = blogService.getAllArticles().stream().map(ArticleListViewResponse::new).toList();
        model.addAttribute("articles", list);
        return "articleList";

    }



}
