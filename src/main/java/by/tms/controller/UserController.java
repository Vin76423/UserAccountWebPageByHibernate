package by.tms.controller;

import by.tms.entity.Address;
import by.tms.entity.Telephone;
import by.tms.entity.User;
import by.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping(path = "/account")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getUserAccount() { return "account"; }

    // Delete user account:............................................

    @GetMapping(path = "/delete-account")
    public ModelAndView deleteUserAccount(ModelAndView modelAndView,
                                          @SessionAttribute("user") User user) {
        userService.deleteUserAccount(user.getId());
        modelAndView.setViewName("redirect:/home/out");
        return modelAndView;
    }

    // Set/update/delete address:.....................................

    @GetMapping(path = "/set-address")
    public ModelAndView getSetAddressForm(ModelAndView modelAndView) {
        modelAndView.addObject("address", new Address());
        modelAndView.setViewName("set-address");
        return modelAndView;
    }

    @PostMapping(path = "/set-address")
    public ModelAndView setAddress(Address address,
                                   @SessionAttribute("user") User user,
                                   ModelAndView modelAndView,
                                   HttpSession session) {
        Optional<User> userBox = userService.setAddress(address, user.getId());
        userBox.ifPresent(value -> session.setAttribute("user", value));
        modelAndView.setViewName("redirect:/account");
        return modelAndView;
    }

    @GetMapping(path = "/update-address")
    public ModelAndView getUpdateAddressForm(@RequestParam("addressId") long addressId,
                                             @SessionAttribute("user") User user,
                                             ModelAndView modelAndView) {
        Address address = new Address();
        for (Address item : user.getAddresses()) {
            if (item.getId() == addressId) {
                address = item;
                break;
            }
        }
        modelAndView.addObject("address", address);
        modelAndView.setViewName("update-address");
        return modelAndView;
    }

    @PostMapping(path = "/update-address")
    public ModelAndView updateAddress(Address address,
                                   @SessionAttribute("user") User user,
                                   ModelAndView modelAndView,
                                   HttpSession session) {
        Optional<User> userBox = userService.updateAddress(address, user.getId());
        userBox.ifPresent(value -> session.setAttribute("user", value));
        modelAndView.setViewName("redirect:/account");
        return modelAndView;
    }

    @GetMapping(path = "/delete-address")
    public ModelAndView deleteAddress(@RequestParam("addressId") long addressId,
                                      @SessionAttribute("user") User user,
                                      ModelAndView modelAndView,
                                      HttpSession session) {
        Optional<User> userBox = userService.deleteAddress(addressId, user.getId());
        userBox.ifPresent(value -> session.setAttribute("user", value));
        modelAndView.setViewName("redirect:/account");
        return modelAndView;
    }


    // Set/update/delete telephone:.............................

    @GetMapping(path = "/set-telephone")
    public ModelAndView getSetTelephoneForm(ModelAndView modelAndView) {
        modelAndView.addObject("telephone", new Telephone());
        modelAndView.setViewName("set-telephone");
        return modelAndView;
    }

    @PostMapping(path = "/set-telephone")
    public ModelAndView setAddress(Telephone telephone,
                                   @SessionAttribute("user") User user,
                                   ModelAndView modelAndView,
                                   HttpSession session) {
        Optional<User> userBox = userService.setTelephone(telephone, user.getId());
        userBox.ifPresent(value -> session.setAttribute("user", value));
        modelAndView.setViewName("redirect:/account");
        return modelAndView;
    }

    @GetMapping(path = "/update-telephone")
    public ModelAndView getUpdateTelephoneForm(@SessionAttribute("user") User user,
                                               ModelAndView modelAndView) {
        Telephone telephone = user.getTelephone();
        modelAndView.addObject("telephone", telephone);
        modelAndView.setViewName("update-telephone");
        return modelAndView;
    }

    @PostMapping(path = "/update-telephone")
    public ModelAndView updateAddress( Telephone telephone,
                                       @SessionAttribute("user") User user,
                                       ModelAndView modelAndView,
                                       HttpSession session) {
        Optional<User> userBox = userService.updateTelephone(telephone, user.getId());
        userBox.ifPresent(value -> session.setAttribute("user", value));
        modelAndView.setViewName("redirect:/account");
        return modelAndView;
    }

    @GetMapping(path = "/delete-telephone")
    public ModelAndView deleteAddress(@SessionAttribute("user") User user,
                                      ModelAndView modelAndView,
                                      HttpSession session) {
        Optional<User> userBox = userService.deleteTelephone(user.getId());
        userBox.ifPresent(value -> session.setAttribute("user", value));
        modelAndView.setViewName("redirect:/account");
        return modelAndView;
    }
}
