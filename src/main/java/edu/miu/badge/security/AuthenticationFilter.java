package edu.miu.badge.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.badge.dto.RequestUserDTO;
import edu.miu.badge.dto.ResponseMemberDTO;
import edu.miu.badge.dto.ResponseUserDTO;
import edu.miu.badge.services.LoginService;

import edu.miu.badge.services.MemberService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private LoginService userService;
    private Environment environment;

    private MemberService memberService;

    public AuthenticationFilter(AuthenticationManager authentication,
                                LoginService userService,
                                Environment environment,
                                MemberService memberService){
        super(authentication);
        this.userService =userService;
        this.environment = environment;
        this.memberService = memberService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

        try {
            RequestUserDTO userDTO = new ObjectMapper().readValue(request.getInputStream(), RequestUserDTO.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword(),
                    new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,Authentication auth) throws IOException {
        String userName = ((User)auth.getPrincipal()).getUsername();
        ResponseUserDTO userDTO = userService.getUserDetailsByUsername(userName);
        ResponseMemberDTO responseMemberDTO = memberService.getMemberById(userDTO.getId());
        String tokenSecret = environment.getProperty("token.secret");
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey secretKey= new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        String token = Jwts.builder()
                .setSubject(userDTO.getUsername())
                .setExpiration(Date.from(Instant.now().plusMillis(Long.parseLong(environment.getProperty("token.expiration_time")))))
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        res.addHeader("token", token);
        res.addHeader("username", userDTO.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        String memberString = objectMapper.writeValueAsString(responseMemberDTO);
        res.getWriter().write(memberString);
    }
}
