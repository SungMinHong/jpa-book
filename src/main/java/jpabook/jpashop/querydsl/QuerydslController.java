package jpabook.jpashop.querydsl;

import jpabook.jpashop.domain.item.Item;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/querydsl")
public class QuerydslController {
    @NonNull
    private final QuerydslRepository repository;

    @GetMapping("/item")
    public List<Item> getItemsByName(@RequestParam("name") final String name) {
        return repository.findItemsByName(name);
    }
}
