package luadjotest.com.ua.service;

import luadjotest.com.ua.model.Cart;
import luadjotest.com.ua.model.Role;
import luadjotest.com.ua.model.User;
import luadjotest.com.ua.repository.CartRepos;
import luadjotest.com.ua.repository.UserRepos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepos userRepos;
    private final MailService mailService;
    private final CartRepos cartRepos;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepos userRepos, MailService mailService, CartRepos cartRepos, PasswordEncoder passwordEncoder) {
        this.userRepos = userRepos;
        this.mailService = mailService;
        this.cartRepos = cartRepos;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepos.findByUsername(username);
        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepos.findByUsername(user.getUsername());
        if (userFromDb != null)
            return false;
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepos.save(user);
        sendActivationCode(user);
        return true;
    }

    private void sendActivationCode(User user) {
        String message = String.format("Добрый день, %s! \n"
                        + "Для активации Вашего аккаунта, Вам нужно перейти по ссылке http://localhost:8080/activation/%s",
                user.getName(), user.getActivationCode());
        mailService.sendTextMail(user.getUsername(), "Активация аккаунта", message);
    }

    public boolean activateUser(String code) {
        User user = userRepos.findByActivationCode(code);
        if (user == null)
            return false;
        user.setActivationCode(null);
        user.setActive(true);
        Cart cart = new Cart(user);
        cartRepos.save(cart);
        user.getCart().add(cart);
        userRepos.save(user);
        return true;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepos.findAll(pageable);
    }

    public void editUser(String name, String surname, String phone, String username,
                         Map<String, String> form, User user, String city) {
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())   //переводим роли из enum в строковый вид
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key))
                user.getRoles().add(Role.valueOf(key));
        }
        user.setCity(city);
        userRepos.save(user);
    }

    public void deleteById(Long id) {
        userRepos.deleteById(id);
    }

    public void editUserProfile(String name, String surname, String phone, String username, String password, User user, String city) {
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        user.setCity(city);
        userRepos.save(user);
    }

    public String resetPassword(String email, String password, String password2, Model model, RedirectAttributes redirectAttributes) {
        User user = loadUserByUsername(email);
        if (!password.equals(password2)) {
            model.addAttribute("passwordError", "Пароли не совпадают!");
            return "resetPassword";
        } else {
            user.setPassword(passwordEncoder.encode(password));
            userRepos.save(user);
            redirectAttributes.addFlashAttribute("message", "Пароль успешно изменен!");
            return "redirect:/login";
        }
    }
}
