package luadjotest.com.ua.controller;

import luadjotest.com.ua.model.Product;
import luadjotest.com.ua.service.CartSmallService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("smallcart")
public class CartSmallController {
    private final CartSmallService cartSmallService;

    public CartSmallController(CartSmallService cartSmallService) {
        this.cartSmallService = cartSmallService;
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Product product, Principal principal) {
        cartSmallService.add(principal.getName(), product);
        return "redirect:/cart";
    }

    @PostMapping("/refresh")
    public String refresh(@RequestParam("productId") Product product,
                          @RequestParam("userQuantity") Integer quantity,
                          Principal principal) {
        cartSmallService.refresh(product, quantity, principal);
        return "redirect:/cart";
    }
}
