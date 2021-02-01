package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;
    
    @Transactional
    public void save(final Item item) {
        repository.save(item);
    }
    
    //변경감지 간단한 예시
    @Transactional
    public void updateBook(Long itemId, Book param) {
        Book book = (Book) repository.findOne(itemId);
        book.setPrice(param.getPrice());
        book.setName(param.getName());
        book.setPrice(param.getPrice());
        book.setStockQuantity(param.getStockQuantity());
        book.setAuthor(param.getAuthor());
        book.setIsbn(param.getIsbn());
    }

    public Item findOne(final Long itemId) {
        return repository.findOne(itemId);
    }
    
    public List<Item> findAll() {
        return repository.findAll();
    }
    
}
