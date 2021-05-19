package com.example.fakefacebook.entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity

public class Post extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(columnDefinition="TEXT")
    private String message;

    @OneToOne
    private User creator;
    private java.sql.Timestamp createdAt;






    public Timestamp getCreatedAt() {
        return createdAt;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }



    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getCreator() {
        return creator;
    }




    public Post() {
        super();
    }




    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
