package com.example.todoapp;

public class ToDo {

    private String title;
    private String content;
    private boolean isDone;
    private String authorEmail;
    private String uid;

    public ToDo(String title, String content, String authorEmail, boolean isDone, String uid){
        this.title = title;
        this.content = content;
        this.authorEmail = authorEmail;
        this.isDone = isDone;
        this.uid = uid;
    }

    public ToDo(){

    }

    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return  this.content;
    }

    public String getAuthorEmail(){
        return this.authorEmail;
    }

    public boolean isDone(){
        return this.isDone;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setIfDone(boolean isDone){
        this.isDone = isDone;
    }

    public String getUid(){
        return this.uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

}
