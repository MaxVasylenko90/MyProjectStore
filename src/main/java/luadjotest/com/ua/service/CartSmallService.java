package luadjotest.com.ua.service;

import luadjotest.com.ua.model.Cart;
import luadjotest.com.ua.model.CartSmall;
import luadjotest.com.ua.model.Product;
import luadjotest.com.ua.model.User;
import luadjotest.com.ua.repository.CartRepos;
import luadjotest.com.ua.repository.CartSmRepos;
import luadjotest.com.ua.repository.ProductRepos;
import luadjotest.com.ua.repository.UserRepos;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class CartSmallService {
    private final CartRepos cartRepos;
    private final UserRepos userRepos;
    private final ProductRepos productRepos;
    private final CartSmRepos cartSmRepos;


    public CartSmallService(CartRepos cartRepos, UserRepos userRepos, ProductRepos productRepos, CartSmRepos cartSmRepos) {
        this.cartRepos = cartRepos;
        this.userRepos = userRepos;
        this.productRepos = productRepos;
        this.cartSmRepos = cartSmRepos;
    }

    public void add(String name, Product product) {
        User user = userRepos.findByUsername(name);
        Cart cart = user.findLastCart();
        if (cart.getCartSmall().size() == 0) {
            addNewProductToCart(product, user, cart);
        } else {
            if (!findSameProductAtCart(cart, product)) {
                addNewProductToCart(product, user, cart);
            }
        }
    }

    public boolean findSameProductAtCart(Cart cart, Product product) {
        for (CartSmall cartSmall : cart.getCartSmall()) {
            if (cartSmall.getProduct().equals(product)) {
                cartSmall.setQuantity(cartSmall.getQuantity() + 1);
                cart.setTotal();
                cartSmRepos.save(cartSmall);
                return true;
            }
        }
        return false;
    }

    private void addNewProductToCart(Product product, User user, Cart cart) {
        CartSmall cartSm = new CartSmall(product, cart);
        cart.getCartSmall().add(cartSm);
        cartSmRepos.save(cartSm);
        productRepos.save(product);
        cart.setTotal();
        cartRepos.save(cart);
        userRepos.save(user);
    }

    public void refresh(Product product, Integer quantity, Principal principal) {
        User user = userRepos.findByUsername(principal.getName());
        Cart cart = user.findLastCart();
        CartSmall cartSmall = cartSmRepos.findByProductAndCart(product, cart);
        cartSmall.setQuantity(quantity);
        cartSmall.setSubtotal();
        cartSmRepos.save(cartSmall);
        cart.setTotal();
        cartRepos.save(cart);
    }
}
