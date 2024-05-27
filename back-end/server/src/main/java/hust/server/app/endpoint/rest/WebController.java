package hust.server.app.endpoint.rest;

import hust.server.domain.authen.entities.User;
import hust.server.domain.authen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("")
public class WebController {
    @Autowired
    private UserService userService;

    @GetMapping(value = {"/public", "/public/home"})
    public String homepage() {
        return "home";
    }

    @GetMapping("/user")
    public String hello() {
        return "hello";
    }

    @GetMapping("/admin")
    public String helloAdmin(){
        return "Welcome to Admin Page";
    }

    @GetMapping("/public/products")
    public ResponseEntity<?> getProducts(HttpSession session) {
        String guestId = (String) session.getAttribute("guestId");

        if (guestId == null) {
            guestId = UUID.randomUUID().toString();
            session.setAttribute("guestId", guestId);
            System.out.println(guestId);
            userService.createGuestUser(guestId);
        }


        // Now you can use guestId to track the user
        // ...

        return ResponseEntity.ok("Products");
    }
}
