package jpabook.jpashop.querydsl;

import jpabook.jpashop.domain.item.Item;

import java.util.List;

public interface QuerydslRepository {
    List<Item> findItemsByName(String name);
}
