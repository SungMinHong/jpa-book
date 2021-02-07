package jpabook.jpashop.transaction_test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final OuterService outerService;
    
    @GetMapping("/api/call")
    public void ordersV1() {
        outerService.call1();
    }
}
