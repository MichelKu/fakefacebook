package com.example.fakefacebook.controller;

import com.example.fakefacebook.entity.Post;
import com.example.fakefacebook.entity.User;
import com.example.fakefacebook.service.PostService;
import com.example.fakefacebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Controller
public class PostController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;



    @GetMapping("/submitPost")
        public String getPost(@ModelAttribute("post") Post post,@ModelAttribute("user") User user, Model model,
                              @CookieValue("currentUserId") String currentUserId) {
            model.addAttribute("posts", postService.getAllPosts());
            model.addAttribute("user", userService.getUserById(Long.parseLong(currentUserId)));
            return "posts";
        }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post")Post post, Model model,
                           @CookieValue("currentUserId") String currentUserId){
        User currentUser = userService.getUserById(Long.parseLong(currentUserId));
        model.addAttribute("currentUser", currentUser);
        post.setCreator(currentUser);
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        post.setCreatedAt(timestamp);
        postService.savePost(post);
        return "redirect:/submitPost";
    }


    @GetMapping("/deleteMyPost/{id}")
    public String deleteMyPost(@PathVariable long id) {
        postService.deletePost(id);
        return "redirect:/submitPost";
    }
}
