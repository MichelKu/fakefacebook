package com.example.fakefacebook.controller;

import com.example.fakefacebook.entity.Post;
import com.example.fakefacebook.entity.User;
import com.example.fakefacebook.service.UserService;
import com.example.fakefacebook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    /******************* FIRST PAGE OF THE APPLICATION - gives the user the ability to sign in or go to the sign up page ***************************/

    @GetMapping("/")
    public String welcome(@ModelAttribute("user") User user) {
        return "signin";
    }

    @GetMapping("/signin")
    public String loginView(@ModelAttribute("user") User user) {
        return "signin";
    }

    @GetMapping("/failedsignin")
    public String failedlogin(@ModelAttribute("user") User user,
                              Model model) {
        model.addAttribute("errorsigninmsg", "Your password or username is incorrect. Please try again!");
        return "signin";
    }

    @PostMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpServletResponse response
    ) {

        if (userService.authUser(username, password)) {
            User user = userService.getUserByUsername(username);
            Long id = user.getId();
            Cookie cookie = new Cookie("currentUserId", id.toString());
            response.addCookie(cookie);

            if (user.username.equals("admin")) {
                return "redirect:/admin";
            } else {
                return "redirect:/userLoggedIn/" + id;
            }
        }

        return "redirect:/failedsignin";
    }

    @GetMapping("/userLoggedIn/{id}")
    public String userLoggedIn(@PathVariable("id") Long id,
                               Model model) {

        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/signup")
    public String signUpView(@ModelAttribute("user") User user) {
        return "signup";
    }

    /***************************** DELETE MY ACCOUNT *********************************/

    @GetMapping("/deleteMyAccount/{id}")
    public String deleteMyAccount(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/signin";
    }

    /***************************** SAVE USER *********************************/

    @GetMapping("/success")
    public String success(@ModelAttribute("user") User user,
                          Model model) {
        model.addAttribute("msg", "Success! You account is now registered!");
        return "signin";
    }

    @GetMapping("/failed")
    public String failed(@ModelAttribute("user") User user,
                         Model model) {
        model.addAttribute("errorsignupmsg", "Your passwords are not matching. Please try again!");
        return "signup";
    }

    @PostMapping("/saveUser")
    public String saveUser(User user,
                           @RequestParam("password") String passwordOne,
                           @RequestParam("passwordTwo") String passwordTwo) {

        if (passwordOne.equals(passwordTwo)) {
            user.setImg("https://via.placeholder.com/150");
            userService.saveUser(user);
            return "redirect:/success";
        }

        return "redirect:/failed";

    }


    /****************************** ADMIN VIEW - gives admin ability to edit and delete users from database ****************************************/
    //Endpoint to handle view which shows a table with
    //all user entries in the database.
    @GetMapping("/admin")
    public ModelAndView adminDashboard(@ModelAttribute("user") User user

    ) {

        if (user.username == "admin") {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin");
            List<User> users = userService.getAllUsers();
            List<Post> posts = postService.getAllPosts();
            mv.addObject("users", users);
            mv.addObject("posts", posts);
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("profile");
        }
        ModelAndView mv = new ModelAndView();
        return mv;
    }



    //Endpoint for editing specific database entries
    //Their id field is used to query them from the database.
    @GetMapping("/edit/{id}")
    public ModelAndView updateUser(@PathVariable long id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("edit");
        User userToUpdate = userService.findUserById(id);
        mv.addObject(userToUpdate);
        return mv;
    }

    //Endpoint that handles updating a specific user
    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    //Endpoint that handles deleting a specific user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    //Endpoint that handles deleting a specific post
    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return "redirect:/admin";
    }

}



