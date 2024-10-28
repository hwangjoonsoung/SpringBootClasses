package me.hwangjoonsoung.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hwangjoonsoung.springbootdeveloper.domain.Article;
import me.hwangjoonsoung.springbootdeveloper.dto.AddArticleRequest;
import me.hwangjoonsoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.hwangjoonsoung.springbootdeveloper.repository.BlogRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetup(){
        MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }

    @Test
    @DisplayName("SAVE NEW ARTICLE")
    void SaveNewArticle() throws Exception {
        //given
        String url = "/api/articles";
        String title = "title";
        String content = "content1";
        AddArticleRequest addArticleRequest = new AddArticleRequest(title, content);

        String requestBody = objectMapper.writeValueAsString(addArticleRequest);
        //when
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));

        //then
        result.andExpect(status().isCreated());
        List<Article> all = blogRepository.findAll();

        Assertions.assertThat(all.size()).isEqualTo(1);
        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("모든 article 가져오기")
    void getAllArticle() throws Exception {
        //given
        String url = "/api/articles";
        String title = "title";
        String content = "content1";

        blogRepository.save(new Article(title, content));
        //when
        ResultActions result = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @Test
    @DisplayName("id로 article 가져오기")
    void getArticleById() throws Exception {
        //given
        String url = "/api/articles/{id}";
        String title = "title1";
        String content = "content1";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());
        //when
        ResultActions result = mockMvc.perform(get(url, savedArticle.getId()));

        //then
        result.andExpect(status().isOk()).andExpect(jsonPath("$.content").value(content)).andExpect(jsonPath("$.title").value(title));
    }

    @Test
    @DisplayName("remove article")
    void removeArticleById() throws Exception {
        //given
        String url = "/api/articles/{id}";
        String title = "title1";
        String content = "content1";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        //when
        ResultActions result = mockMvc.perform(delete(url, savedArticle.getId()));

        //then
        List<Article> all = blogRepository.findAll();
        Assertions.assertThat(all).isEmpty();
    }

    @Test
    @DisplayName("article 수정")
    void editArticle() throws Exception {
        //given
        String url = "/api/articles/{id}";
        String title = "title1";
        String content = "content1";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        //when
        String editContent = "edit content";
        String editTitle = "edit title";
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(editTitle, editContent);
        ResultActions result = mockMvc.perform(patch(url, savedArticle.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateArticleRequest)));

        //then
        result.andExpect(status().isOk());
        Optional<Article> article = blogRepository.findById(savedArticle.getId());

        Assertions.assertThat(editContent).isEqualTo(article.get().getContent());
        Assertions.assertThat(editTitle).isEqualTo(article.get().getTitle());


    }

}