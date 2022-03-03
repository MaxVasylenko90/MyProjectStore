package luadjotest.com.ua.controller;

import luadjotest.com.ua.model.Product;
import luadjotest.com.ua.model.User;
import luadjotest.com.ua.repository.UserRepos;
import luadjotest.com.ua.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    private final ProductService productService;
    private final CartService cartService;
    private final PageService pageService;
    private final MailService mailService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepos userRepos;

    public MainController(ProductService productService, CartService cartService, PageService pageService,
                          MailService mailService, UserService userService, PasswordEncoder passwordEncoder,
                          UserRepos userRepos) {
        this.productService = productService;
        this.cartService = cartService;
        this.pageService = pageService;
        this.mailService = mailService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepos = userRepos;
    }

    @GetMapping("/login?error")
    public String loginPage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("alertMessage", "Вы ввели неверный логин или пароль!");
        return "redirect:/login";
    }

    @GetMapping("/search")
    public String find(Model model, @RequestParam(name = "filter") String filter) {
        return pageService.pageWithPagination(model, filter, 1, "id", "desc", false);
    }

    @GetMapping("/search/filter={filter}/page/{pageNo}")
    public String pages(Model model, @PathVariable(name = "filter") String filter, @PathVariable(name = "pageNo") Integer pageNo,
                        @RequestParam("sortField") String sortField,
                        @RequestParam("sortDir") String sortDirection) {
        return pageService.pageWithPagination(model, filter, pageNo, sortField, sortDirection, false);
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        return pageService.mainPage(model, 1, Product.class, "id", "desc");
    }

    @GetMapping("/page/{pageNo}")
    public String pages(Model model, @PathVariable(name = "pageNo") Integer pageNo,
                        @RequestParam("sortField") String sortField,
                        @RequestParam("sortDir") String sortDirection) {
        return pageService.mainPage(model, pageNo, Product.class, sortField, sortDirection);
    }

    @GetMapping("/forgetPassword")
    public String forgetPasswordPage() {
        return "forgetPassword";
    }

    @PostMapping("/forgetPassword")
    public String forgetPasswordForm(Model model, @RequestParam("email") String email) {
        if (userService.loadUserByUsername(email) == null) {
            model.addAttribute("message", "Пользователя с таким e-mail не существует!");
            return "forgetPassword";
        }
        String text = String.format("Добрый день! Вы запросили восстановление пароля на сайте www.luadjo.com. Если Вы этого не делали просто проигнорируйте данное письмо.\n" +
                        "Для восстановления пароля перейдите по ссылке:\n"
                        + "Для активации Вашего аккаунта, Вам нужно перейти по ссылке http://localhost:8080/resetPassword/user=%s",
                email);
        mailService.sendTextMail(email, "Восстановление пароля", text);
        model.addAttribute("message", "Для восставновления пароля перейдите по ссылке на Вашем e-mail.");
        return "temp";
    }

    @GetMapping("/resetPassword/user={email}")
    public String resetPassword(Model model, @PathVariable("email") String email, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("user", userService.loadUserByUsername(email));
        return "resetPassword";
    }

    @PostMapping("/resetPassword/user={email}")
    public String setNewPassword(Model model, @PathVariable("email") String email, @RequestParam("password") String password,
                                 @RequestParam("password2") String password2, RedirectAttributes redirectAttributes) {
        return userService.resetPassword(email, password, password2, model, redirectAttributes);
    }
}
