package hust.server.domain.authen.service;

import hust.server.app.exception.ApiException;
import hust.server.domain.authen.dto.request.TokenRequest;
import hust.server.domain.authen.dto.request.UserAccountRequest;
import hust.server.domain.authen.dto.response.AuthResponse;
import hust.server.domain.authen.entities.CustomUserDetails;
import hust.server.domain.authen.entities.User;
import hust.server.domain.authen.repository.UserRepository;
import hust.server.domain.products.entity.Branch;
import hust.server.domain.products.repository.BranchRepository;
import hust.server.infrastructure.enums.MessageCode;
import hust.server.infrastructure.utilies.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Lazy
    private JwtUtil jwtTokenUntil;

    @Autowired
    private BranchRepository branchRepository;

    private final long GUEST_EXPIRATION = 2 * 30 * 24 * 60;
    private final long CASHIER_EXPIRATION = 2 * 30 * 24 * 60;


    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user.toCustomUserDetails();
    }

    public MessageCode register(UserAccountRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user != null) {
            throw new ApiException(MessageCode.USERNAME_ALREADY_EXISTS);
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setIsGuest(0);
        newUser.setId(UUID.randomUUID().toString());
        // Set other fields of newUser as needed

        try {
            userRepository.save(newUser);
        }catch (Exception e) {
            throw new ApiException(MessageCode.FAIL);
        }
        return MessageCode.SUCCESS;
    }

    public User createGuestUser(String guestId) {
        User guestUser = new User();
        guestUser.setId(guestId);
        guestUser.setIsGuest(1);
        // Set other fields of guestUser as needed

        return userRepository.save(guestUser);
    }

    public String genToken(CustomUserDetails customUserDetails) {
        return jwtTokenUntil.generateToken(customUserDetails, CASHIER_EXPIRATION);
    }

    public AuthResponse genGuestToken(String code, Integer tableNumber){
        Branch branch = branchRepository.getByCodeAndActive(code, 1).orElse(null);
        if (branch == null)throw new ApiException(MessageCode.BRANCH_NOT_EXIST);

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setIsGuest(1);
        user.setActive(1);
        user.setRole("GUEST");
        user.setCreatedAt(LocalDateTime.now());
        user.setBranch(branch);
        userRepository.save(user);
        AuthResponse response = new AuthResponse();
        Map<String, Object> payload = new HashMap<>();
        payload.put("branchId", branch.getId());
        payload.put("tableNumber", tableNumber);
        response.setJwt(jwtTokenUntil.generateToken(user.toCustomUserDetails(), GUEST_EXPIRATION, payload));
        response.setRole("GUEST");
        return response;
//        return null;
    }

    public MessageCode checkToken(TokenRequest request) {
        if (jwtTokenUntil.isTokenExpired(request.getJwt()))return MessageCode.TOKEN_EXPIRED;
        String userId = jwtTokenUntil.getClaimByKey(request.getJwt());
        System.out.println(userId);
        User user = userRepository.getByIdAndActive(userId, 1).orElse(null);
        if (user == null)return MessageCode.TOKEN_ERROR;
        return MessageCode.TOKEN_VALIDATE;
    }
}
