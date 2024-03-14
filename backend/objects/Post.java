package backend.objects;

import java.time.Instant;
import java.util.Date;

import backend.MasterServer;

public class Post {
    User username;
    Date postdate;
    String message;
    String subject;

    byte[] image;
    public void setImage(byte[] image) {
        this.image = image;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setPostdate(Date postdate) {
        this.postdate = postdate;
    }
    public void setUsername(User username) {
        this.username = username;
    }
    public byte[] getImage() {
        return this.image;
    }
    public String getMessage() {
        return this.message;
    }
    public Date getPostdate() {
        return this.postdate;
    }
    public User getUsername() {
        return this.username;
    }
    public Post(User poster, String subject, String content, byte[] img){
        this.image = img;   
        this.message = content;
        this.subject = subject;
        MasterServer.Update(this);
        poster.OwnPost(this);
        this.postdate = Date.from(Instant.now());
        System.err.println(content + "/" + subject);
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
    public String getSubject() {
        return subject;
        // TODO Auto-generated method stub
    }
}
