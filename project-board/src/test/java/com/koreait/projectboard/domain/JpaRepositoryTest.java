package com.koreait.projectboard.domain;

import com.koreait.projectboard.config.JpaConfig;
import com.koreait.projectboard.repository.ArticleCommentRepository;
import com.koreait.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Disabled("JPA 테스트는 불필요 하므로 제외시킴")
@Import(JpaConfig.class) //jpaConfig은 import필요
@DisplayName("JPA 연결 테스트")
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public  JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                              @Autowired ArticleCommentRepository articleCommentRepository){
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void select(){
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).isNotNull().hasSize(1000);    //junit 단위테스트할때 쓰는 기능을 가진 클래스

    }
    @DisplayName("insert 테스트")
    @Test
    void insert(){
        long prevCount = articleRepository.count();
        Article saveArticle = articleRepository.save(Article.of("새로운 글",
                "새로운 글을 등록합니다!", "#new"));
        assertThat(articleRepository.count()).isEqualTo(prevCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void update(){
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateeHashTag ="#Spring";
        article.setHashtag(updateeHashTag);
        Article saveArticle = articleRepository.saveAndFlush(article);
        assertThat(saveArticle).hasFieldOrPropertyWithValue("hashtag",updateeHashTag);
        //flush는 적용까지 시켜주는 것 -- 근데 data.sql이 더 강한지 안됨 ㅜ
        //ymal에서 h2부분 주석처리
    }

    @DisplayName("delete 테스트")
    @Test
    void delete(){
        Article article = articleRepository.findById(1L).orElseThrow();
        long preArticleCount = articleRepository.count();
        long preArticleCommentCount = articleCommentRepository.count();
        int deletedCommnetsSize = article.getArticleComments().size();//1L글의 comment개수

        articleRepository.delete(article);

        assertThat(articleRepository.count()).isEqualTo(preArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(preArticleCommentCount - deletedCommnetsSize);
    }
}