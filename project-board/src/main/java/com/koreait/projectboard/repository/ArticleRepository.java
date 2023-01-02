package com.koreait.projectboard.repository;
import com.koreait.projectboard.domain.Article;
import com.koreait.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article,Long>,
        QuerydslPredicateExecutor<Article>, //적용하고자하는 엔티티 이름: 완벽히 =로 검색하는건 가능
        QuerydslBinderCustomizer<QArticle>{//수정하는기능을 쓸때 반영하고자하는 엔티티 이름- qclass :

    @Override   //QuerydslBinderCustomizer<QArticle>할때 필수!
    default void customize(QuerydslBindings bindings, QArticle root){
        //default 1.8이상부터 인터페이스에서 추상메소드 구현 가능
        bindings.excludeUnlistedProperties(true);
        //모든거의 where절이 검색할 수 있지만 막아줌!
        //내가 필요한 애들만 추가할거임!
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);  //like검색 가능
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); //날짜는 like검색 불가   //범위값주는건 다음에
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

//        bindings.bind(root.title).first(StringExpression::eq);  //완벽하게 같은것만
    };
}
