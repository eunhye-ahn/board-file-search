package com.crudstudy.board.repository;

import com.crudstudy.board.domain.Post;
import com.crudstudy.board.domain.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * [WHAT] PostRepositoryCustom 구현체
 *          -> QueryDSL을 사용해서 동적 검색 쿼리를 실행하는 클래스
 * [WHY] JPA 기본 메서드로는 메서드를 조합마다 만들어야함
 *          QueryDSL을 사용하면 조건을 null로 처리하면 자동 제외
 * [흐름] Q클래스() 자동생성 : entity의 필드들을 참조할 수 있게 하는 클래스
 *          -> BooleanBuilder 방식 : if문으로 직접 조건 추가 - and or
 *          -> BooleanExpression 방식 : null이면 자동 무시 - 추가공부필요
 *      -> Repository에 상속 (인터페이스로)
 *      -> Service에 서비스
 *      -> 컨트롤러에 엔드포인트 추가
 */

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> search(String keyword, String type, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        QPost post = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();

        //키워드검색
        if(StringUtils.hasText(keyword)){
            switch (type) {
                case "title" -> builder.and(post.title.containsIgnoreCase(keyword));
                case "content" -> builder.and(post.content.containsIgnoreCase(keyword));
                case "titleContent" ->
                        builder.and(post.title.containsIgnoreCase(keyword)
                        .or(post.content.containsIgnoreCase(keyword))
                        );
                case "user" ->
                        builder.and(post.user.name.containsIgnoreCase(keyword));
            }
        }

        //기간검색
        if(startDate != null){
            builder.and(post.createdAt.goe(startDate)); //>=
        }
        if(endDate != null){
            builder.and(post.createdAt.loe(endDate)); //<=
        }

        //실제쿼리실행
        List<Post> result = queryFactory
                .selectFrom(post)
                .leftJoin(post.user).fetchJoin() //
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdAt.desc())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(result, pageable, total!=null? total:0);
    }
}
