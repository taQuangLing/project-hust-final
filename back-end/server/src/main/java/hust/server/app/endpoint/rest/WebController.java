package hust.server.app.endpoint.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class WebController {
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
}
