package me.hwangjoonsoung.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.domain.Article;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {

    private String title;
    private String content;
    private String author;

    public Article toEntity(String author) {
        return Article.builder().title(title).content(content).author(author).build();
    }
}
