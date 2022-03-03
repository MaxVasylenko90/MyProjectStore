package luadjotest.com.ua.controller;

import luadjotest.com.ua.model.CartSmall;
import luadjotest.com.ua.model.User;
import luadjotest.com.ua.service.CartService;
import luadjotest.com.ua.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping()
    public String myCart(Model model, Principal principal) {
        model.addAttribute("smallCarts", cartService.getAllSmallCarts(principal.getName()));
        model.addAttribute("cart", cartService.getUserCart(principal.getName()));
        return "cart";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("cartSmallId") CartSmall cartSmall, Principal principal) {
        cartService.delete(cartSmall, principal);
        return "redirect:/cart";
    }

    @GetMapping("/order")
    public String order(Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("smallCarts", cartService.getAllSmallCarts(principal.getName()));
        return "order";
    }

    @PostMapping("/order")
    public String order(@RequestParam String name,
                        @RequestParam String surname,
                        @RequestParam String phone,
                        @RequestParam String city,
                        @RequestParam("email") String username,
                        @RequestParam("group1") String payment,
                        @RequestParam("group2") String delivery,
                        @RequestParam(name = "comment", required = false) String comment) {
        cartService.newOrder(name, surname, phone, city, username, payment, delivery, comment);
        return "redirect:/cart/thankyou";
    }

    @GetMapping("/thankyou")
    public String thankYouPage(Model model, Principal principal) {
        User user = cartService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("smallCart", user.findPreviousLastCart().getCartSmall());
        model.addAttribute("order", cartService.findLastOrder(user));
        cartService.sendEmail(model, user);
        return "thankyou";
    }

    @GetMapping("/emailOrder")
    public String orderEmail(Model model, Principal principal) {
        User user = cartService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("smallCart", user.findPreviousLastCart().getCartSmall());
        model.addAttribute("order", cartService.findLastOrder(user));
        return "emailOrder";
    }
}