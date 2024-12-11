package com.zosh.Controller;

import com.zosh.config.JwtProvider;
import com.zosh.modal.User;
import com.zosh.repository.UserRepository;
import com.zosh.request.LoginRequest;
import com.zosh.response.AuthResponse;
import com.zosh.service.CustomUserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

@Autowired
    private CustomUserServiceImplementation customUserDetails;

    @Autowired
    private JwtProvider jwtProvider;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws Exception {

    String email = user.getEmail();
    String password = user.getPassword();
    String fullName = user.getFullName();
    String role = user.getRole();

    User IsEmailExist = userRepository.findByEmail(email);

    if (IsEmailExist != null) {
        throw
                new Exception("EMAIL IS ALREADY USED WITH ANOTHER ACCOUNT !!");


    }

//create new user
// }

    User createdUser = new User();
    createdUser.setEmail(email);
    createdUser.setFullName(fullName);
    createdUser.setRole(role);
    createdUser.setPassword(passwordEncoder.encode(password));

    User savedUser = userRepository.save(createdUser);


    Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = JwtProvider.generateToken(authentication);

    AuthResponse authResponse = new AuthResponse();
    authResponse.setJwt(token);

    authResponse.setMessage("REGISTER SUCCESSFULLY !!");

    authResponse.setStatus(true);

    return new ResponseEntity<>(authResponse, HttpStatus.OK);
}





@PostMapping("/signin")
public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest  loginRequest){


    String username = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    System.out.println(username + "-------------"+password);

    Authentication authentication = authenticate(username,password);

  SecurityContextHolder.getContext().setAuthentication(authentication);
  String token  =JwtProvider.generateToken(authentication);
   AuthResponse authResponse  = new AuthResponse();
   authResponse.setJwt(token);
   authResponse.setMessage("LOGIN SUCCESS !!");
   authResponse.setStatus(true);
   return new ResponseEntity<>(authResponse,HttpStatus.OK);

    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("SIGN IN USERDETAILS " + userDetails);

        if(userDetails == null){
            System.out.println("SIGN IN USERDETAIL -NULL "+userDetails);

            throw new BadCredentialsException("INVALID USENRAME OR PASSWORD !!");

        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            System.out.println("SIGN IN USERDETAILS - PASSWORD NOT MATCHES "+userDetails);
            throw new BadCredentialsException("INVALID username or Password !!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }


}
