package backend.objects;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import backend.MasterServer;

public class User {
    String username;
    String password;
    ArrayList<Post> posts;
    int karma;
    String profile;
    byte[] pfp;
    LocalDate registered;
    public User(String name, String pass){
        System.out.println("User.User()");
        if (!MasterServer.hasUser(name)) {
            posts = new ArrayList<>();
            username = name;
            password = pass;
            MasterServer.Update(this);
            registered = LocalDate.now();
        }
    }
    public void MakePost(String subject, String content, byte[] img){
        posts.add(new Post(this,subject,content,img));
    }
    public int getKarma() {
        return karma;
    }
    public String getProfile(){
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
    public void setPfp(byte[] pfp) {
        this.pfp = pfp;
    }
    public byte[] getPfp() {
        return pfp;
    }
    public void setPassword(String pass) {
        // only do this locally this is really shitty coding i dont know what i could use but this works for now but dont let this be permanent and USE THE MASTERSERVER FUNCTION
        this.password = pass;

    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public Post[] posts() {
        // TODO Auto-generated method stub
        Post[] p = new Post[10];
        return posts.toArray(p);
    }
    public ArrayList<Post> getPostArray() {
        // TODO Auto-generated method stub
        return posts;
    }
    public void makePost(){

    }
    public void OwnPost(Post post) {
        // TODO Auto-generated method stub
        this.posts.add(post);
        post.setUsername(this);
    }
}