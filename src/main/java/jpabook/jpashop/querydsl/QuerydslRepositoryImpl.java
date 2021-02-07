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

    @Override
    public List<Item> findItemsByName(final String name) {
        return queryFactory
                .select(item)
                .from(item)
                .where(item.name.eq(name))
                .fetch();
    }

}
