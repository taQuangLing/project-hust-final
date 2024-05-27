package hust.server.app.endpoint.rest.auth;

import hust.server.app.exception.ApiException;
import hust.server.app.service.ResponseFactory;
import hust.server.domain.authen.dto.request.GuestTokenRequest;
import hust.server.domain.authen.dto.request.UserAccountRequest;
import hust.server.domain.authen.dto.response.AuthResponse;
import hust.server.domain.authen.entities.CustomUserDetails;
import hust.server.domain.authen.service.UserService;
import hust.server.infrastructure.enums.MessageCode;
import hust.server.infrastructure.utilies.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UserAccountRequest request){
        Authentication authenticate;

        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            //Từ authentication => get ra được đối tượng user detail mà đã load từ DB => Get được id, username, => Nhét id và username vào claim => gen ra token
        }catch(BadCredentialsException e){
            throw new ApiException(MessageCode.ACCOUNT_INCORRECT);
        }
        return ResponseFactory.response(new AuthResponse(userService.genToken((CustomUserDetails) authenticate.getPrincipal())));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserAccountRequest request) {
        return ResponseFactory.response(userService.register(request));
    }

    @GetMapping("/guest-token")
    public ResponseEntity<?> genGuestToken(@RequestBody GuestTokenRequest request){
        return ResponseFactory.response(userService.genGuestToken(request));
    }
}
