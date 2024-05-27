package hust.server.domain.authen.service;

import hust.server.app.exception.ApiException;
import hust.server.domain.authen.dto.request.GuestTokenRequest;
import hust.server.domain.authen.dto.request.UserAccountRequest;
import hust.server.domain.authen.entities.CustomUserDetails;
import hust.server.domain.authen.entities.User;
import hust.server.domain.authen.repository.UserRepository;
import hust.server.domain.products.entity.Branch;
import hust.server.domain.products.repository.BranchRepository;
import hust.server.infrastructure.enums.MessageCode;
import hust.server.infrastructure.utilies.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private final long GUEST_EXPIRATION = 30;
    private final long CASHIER_EXPIRATION = 8 * 60;


    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user.toCustomUserDetails();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public MessageCode register(UserAccountRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user != null) {
            throw new ApiException(MessageCode.USERNAME_ALREADY_EXISTS);
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
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
        guestUser.setIsGuest(true);
        // Set other fields of guestUser as needed

        return userRepository.save(guestUser);
    }

    public String genToken(CustomUserDetails customUserDetails) {
        return jwtTokenUntil.generateToken(customUserDetails, CASHIER_EXPIRATION);
    }

    public String genGuestToken(GuestTokenRequest request){
        Branch branch = branchRepository.getByIdAndActive(request.getBranchId(), 1).orElse(null);
        if (branch == null)throw new ApiException(MessageCode.BRANCH_NOT_EXIST);

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setIsGuest(true);
        user.setActive(1);
        user.setRole("GUEST");
        user.setCreatedAt(LocalDateTime.now());
        user.setBranch(branch);
        userRepository.save(user);
        return jwtTokenUntil.generateToken(user.toCustomUserDetails(), GUEST_EXPIRATION);
//        return null;
    }
}
