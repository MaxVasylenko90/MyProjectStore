package luadjotest.com.ua.controller;

import luadjotest.com.ua.model.Role;
import luadjotest.com.ua.model.User;
import luadjotest.com.ua.service.PageService;
import luadjotest.com.ua.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PageService pageService;

    public UserController(UserService userService, PageService pageService) {
        this.userService = userService;
        this.pageService = pageService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String listOfUsers(Model model) {
        return pageService.pageWithPagination(model, 1, User.class, "id", "desc");
    }

    @GetMapping("/page/{pageNo}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String pages(Model model, @PathVariable(name = "pageNo") Integer pageNo,
                        @RequestParam("sortField") String sortField,
                        @RequestParam("sortDir") String sortDirection) {
        return pageService.pageWithPagination(model, pageNo, User.class, sortField, sortDirection);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String search(Model model, @RequestParam(name = "filter") String filter) {
        return pageService.pageWithPagination(model, filter, 1, "id", "desc", true);
    }

    @GetMapping("/search/filter={filter}/page/{pageNo}")
    public String pages(Model model, @PathVariable(name = "filter") String filter, @PathVariable(name = "pageNo") Integer pageNo,
                        @RequestParam("sortField") String sortField,
                        @RequestParam("sortDir") String sortDirection) {
        return pageService.pageWithPagination(model, filter, pageNo, sortField, sortDirection, true);
    }

    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEdit(Model model, @PathVariable User user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEdit(@RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String phone,
                           @RequestParam String username,
                           @RequestParam(required = false) String city,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user) {
        userService.editUser(name, surname, phone, username, form, user, city);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.deleteById(id);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal, @RequestParam(required = false) String message) {
        model.addAttribute("message", message);
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "userProfile";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String phone,
                                @RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String password2,
                                @RequestParam(required = false) String city,
                                @AuthenticationPrincipal User user,
                                RedirectAttributes redirectAttributes) {
        if (!password.equals(password2)) {
            redirectAttributes.addFlashAttribute("alertMessage", "Пароли не совпадают!");
            return "redirect:/user/profile";
        }
        userService.editUserProfile(name, surname, phone, username, password, user, city);
        redirectAttributes.addAttribute("message", "Данные успешно изменены!");
        return "redirect:/user/profile";
    }
}
