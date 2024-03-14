package backend.sites;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Perma {
    public static String loginForum;
    public static String registerForm;
    public static String postForm;
    public static String navbarLoggedOut;
    public static String navbarLoggedIn;
    public static String header;
    public static String footer;
    public static String style;


    static{
        try {
            Perma.loginForum = Files.readString(Path.of("web/loginForm.html"));
            Perma.navbarLoggedOut = Files.readString(Path.of("web/navbarLoggedOut.html"));
            Perma.navbarLoggedIn = Files.readString(Path.of("web/navbarLoggedIn.html"));
            Perma.header = Files.readString(Path.of("web/header.html"));
            Perma.footer = Files.readString(Path.of("web/footer.html"));
            Perma.style = "<style>"+Files.readString(Path.of("web/style.css"))+"</style>";
            Perma.registerForm = Files.readString(Path.of("web/registerForm.html"));
            Perma.postForm = Files.readString(Path.of("web/postForm.html"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }
}
