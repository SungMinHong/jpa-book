package jpabook.jpashop.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.item.Item;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jpabook.jpashop.domain.item.QItem.*;

@Repository
@RequiredArgsConstructor
public class QuerydslRepositoryImpl implements QuerydslRepository{
    @NonNull
    private final JPAQueryFactory queryFactory;

    // 인덱스가 있는 경우 like 검색은 빨라 지는가
    @Override
    public List<Item> findItemsByName(final String name) {
        return queryFactory
                .select(item)
                .from(item)
                .where(item.name.like(name).and(item.price.gt(9999)))
                .offset(0)
                .limit(1)
                .fetch();
    }

}
