package by.tms.controller;

import by.tms.dto.AuthUserDto;
import by.tms.dto.RegUserDto;
import by.tms.entity.Address;
import by.tms.entity.Telephone;
import by.tms.entity.User;
import by.tms.service.UserService;
import by.tms.service.mapper.UserDtoDoMapperService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/home")
public class HomeController {

    private UserService userService;
    private UserDtoDoMapperService userDtoDoMapperService;

    public HomeController(UserService userService, UserDtoDoMapperService userDtoDoMapperService) {
        this.userService = userService;
        this.userDtoDoMapperService = userDtoDoMapperService;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping(path = "/reg")
    public ModelAndView getRegForm(ModelAndView modelAndView) {
        modelAndView.addObject("regUserDto", new RegUserDto());
        modelAndView.setViewName("reg");
        return modelAndView;
    }

    @PostMapping(path = "/reg")
    public ModelAndView regUser(RegUserDto regUserDto,
                                ModelAndView modelAndView) {
        User user = userDtoDoMapperService.mappingUserDoFromUserRegDto(regUserDto);


        // Mock:...................................
//        User user = new User();
//        user.setName("Bob");
//        user.setLogin("Bobby@org");
//        user.setPassword("123");
//        user.setAge(24);
//
//        Telephone telephone = new Telephone(
//                0L,
//                "(33) 610 54 45",
//                "A1",
//                "work"
//        );
//        user.setTelephone(telephone);
//
//        List<Address> addresses = Arrays.asList(
//                new Address(0L, "New-York", "Baker-street", 15, 42, "flat"),
//                new Address(0L, "Malaga", "Spring", 12, 0, "country house"),
//                new Address(0L, "Bosthon", "Roswud", 25, 324, "ofice")
//        );
//        user.setAddresses(addresses);
        //.........................................


        userService.saveUser(user);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping(path = "/auth")
    public ModelAndView getAuthForm(ModelAndView modelAndView) {
        modelAndView.addObject("authUserDto", new AuthUserDto());
        modelAndView.setViewName("auth");
        return modelAndView;
    }

    @PostMapping(path = "/auth")
    public ModelAndView authUser(AuthUserDto authUserDto,
                                 ModelAndView modelAndView,
                                 HttpSession session) {
        Optional<User> userBox = userService.getUserByLogin(authUserDto.getUserLogin());
        if (userBox.isEmpty()) {
            modelAndView.addObject("errorMassage", "User with such login does not exist!");
            modelAndView.setViewName("auth");
            return modelAndView;
        }
        if (!userBox.get().getPassword().equals(authUserDto.getUserPassword())) {
            modelAndView.addObject("errorMassage", "User with such password does not exist!");
            modelAndView.setViewName("auth");
            return modelAndView;
        }
        session.setAttribute("user", userBox.get());
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping(path = "/out")
    public ModelAndView logOutUser(ModelAndView modelAndView,
                                   HttpSession session) {
        session.invalidate();
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
