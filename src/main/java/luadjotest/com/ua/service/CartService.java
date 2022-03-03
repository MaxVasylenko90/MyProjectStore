package luadjotest.com.ua.service;


import luadjotest.com.ua.model.*;
import luadjotest.com.ua.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final CartRepos cartRepos;
    private final UserRepos userRepos;
    private final ProductRepos productRepos;
    private final CartSmRepos cartSmRepos;
    private final OrderRepos orderRepos;
    private final MailService mailService;


    public CartService(CartRepos cartRepos, UserRepos userRepos, ProductRepos productRepos,
                       CartSmRepos cartSmRepos, OrderRepos orderRepos, MailService mailService) {
        this.cartRepos = cartRepos;
        this.userRepos = userRepos;
        this.productRepos = productRepos;
        this.cartSmRepos = cartSmRepos;
        this.orderRepos = orderRepos;
        this.mailService = mailService;
    }

    public void delete(CartSmall cartSmall, Principal principal) {
        cartSmRepos.delete(cartSmall);
        User user = userRepos.findByUsername(principal.getName());
        Cart cart = user.findLastCart();
        cart.setTotal();
        cartRepos.save(cart);
    }

    public List<CartSmall> getAllSmallCarts(String name) {
        User user = userRepos.findByUsername(name);
        return user.findLastCart().getCartSmall();
    }

    public Cart getUserCart(String username) {
        User user = findByUsername(username);
        return user.findLastCart();
    }
    public User findByUsername(String username) {
        return userRepos.findByUsername(username);
    }

    public void newOrder(String name, String surname, String phone, String city, String username, String payment,
                         String delivery, String comment) {
        User user = findByUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setCity(city);
        userRepos.save(user);
        Order order = new Order(user.findLastCart(), user, payment, delivery, comment);
        orderRepos.save(order);
        user.getOrder().add(order);
        userRepos.save(user);
        user.findLastCart().setOrder(order);
        cartRepos.save(user.findLastCart());
        user.getCart().add(new Cart(user));
        cartRepos.save(user.findLastCart());
        userRepos.save(user);
    }

    public void sendEmail(Model model, User user) {
    String text = String.format("Здравствуйте, %s! \n"
            + "Спасибо за Ваш заказ! Он будет зарезервирован, пока мы не подтвердим, что платёж получен. В то же время, напоминаем содержимое вашего заказа:\n", user.getName());
        mailService.sendMailWithTemplate(user.getUsername(), "Заказ №" + user.findPreviousLastCart().getOrder().getId() + " оформлен успешно!", model, text);
    }

    public Order findLastOrder(User user) {
        for(int i = 0; i < user.getOrder().size(); i++)
            return user.getOrder().get(user.getOrder().size() - 1);
        return null;
    }
}