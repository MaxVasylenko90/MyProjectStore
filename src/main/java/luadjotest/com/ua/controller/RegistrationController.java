package luadjotest.com.ua.controller;

import luadjotest.com.ua.model.User;
import luadjotest.com.ua.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(Model model, User user, @RequestParam("password") String password, @RequestParam("password2") String password2, RedirectAttributes redirectAttributes) {
        if (!password.equals(password2)) {
            model.addAttribute("alertMessage", "Пароли не совпадают!");
            return "registration";
        } else if (!userService.addUser(user)) {
            model.addAttribute("alertMessage", "Пользователь с таким e-mail уже существует!");
            return "registration";
        }
        redirectAttributes.addFlashAttribute("message", "Для активации аккаунта пройдти по ссылке в письме на Вашей электронной почте!");
        return "redirect:/registration/temp";
    }

    @GetMapping("/activation/{code}")
    public String activate(@PathVariable String code, RedirectAttributes redirectAttributes) {
        boolean isActivated = userService.activateUser(code);
        if (!isActivated) {
            redirectAttributes.addFlashAttribute("message", "Ошибка активации аккаунта!");
            return "redirect:/registration";
        } else {
            redirectAttributes.addFlashAttribute("message", "Ваш аккаунт успешно активирован! Введите логин и пароль");
            return "redirect:/login";
        }
    }

    @GetMapping("/registration/temp")
    public String temp() {
        return "temp";
    }
}
