package com.recipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.config.JWTProvider;
import com.recipe.model.User;
import com.recipe.repository.UserRepository;
import com.recipe.request.LoginRequest;
import com.recipe.response.AuthResponse;
import com.recipe.service.CustomUserDetailService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JWTProvider jwtProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception { 
		
		String email= user.getEmail();
		String pass = user.getPassword();
		String fullName=user.getFullName();
		
		User isExistEmail= this.userRepository.findByEmail(email);
		
		if(isExistEmail!=null) {
			throw new Exception("Email already exist...");
		}
		
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(this.passwordEncoder.encode(pass));
		createdUser.setFullName(fullName);
		
		userRepository.save(createdUser);
		
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, pass);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse response = new AuthResponse();
		
		response.setJwt(token);
		response.setMessage("Signup success");
		
		return response;
	}
	
	@PostMapping("/signin")
	public AuthResponse signInHandler(@RequestBody LoginRequest loginRequest) throws Exception {
		
		
		String userName= loginRequest.getEmail();
		String password= loginRequest.getPassword();
		
		Authentication authentication=authenticate(userName,password);
		System.out.println("hi:1");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
        String token = jwtProvider.generateToken(authentication);
		
		AuthResponse response = new AuthResponse();
		
		response.setJwt(token);
		response.setMessage("Signin success");  
		
		return response; 
		
	}

	private Authentication authenticate(String userName, String password) {
		// TODO Auto-generated method stub
		UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
		
		if(userDetails==null) {
			System.out.println("User not found!");
			throw new BadCredentialsException("User not found!");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			System.out.println("Password Incorrect");
			throw new BadCredentialsException("Invalid password!");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
}
