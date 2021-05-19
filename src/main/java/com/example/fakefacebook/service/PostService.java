package com.example.fakefacebook.service;
import com.example.fakefacebook.entity.Post;
import com.example.fakefacebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    public void savePost(Post post) { postRepository.save(post); }

    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }







}
