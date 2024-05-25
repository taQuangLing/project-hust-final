package hust.server.domain.authen.service;

import hust.server.app.exception.ApiException;
import hust.server.domain.authen.dto.request.UserAccountRequest;
import hust.server.domain.authen.dto.response.UserResponse;
import hust.server.domain.authen.entities.User;
import hust.server.domain.authen.repository.UserRepository;
import hust.server.infrastructure.dto.response.Response;
import hust.server.infrastructure.enums.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
}
