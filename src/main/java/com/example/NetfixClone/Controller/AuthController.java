package com.example.NetfixClone.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.NetfixClone.Dto.AuthRequest;
import com.example.NetfixClone.Entity.Users;
import com.example.NetfixClone.Service.AuthService;
import com.example.NetfixClone.Util.JwtUtil;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
	
	@Autowired
	private AuthService authservice;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody Users user) {
		authservice.signup(user);
		
		return ResponseEntity.ok("Successful Registered");	
		
	}
	
	@PostMapping("/login")
	public String login(@RequestBody AuthRequest user) {
		try {
			Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			
			UserDetails userDetails=(UserDetails) authentication.getPrincipal();
			
		   return jwtUtil.createToken(userDetails);
		}catch (Exception e) {
			// TODO: handle exception
			return "ERROR";
		}
		
		
		
		
		
		
	}

}
