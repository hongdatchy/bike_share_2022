package com.hongdatchy.bikeshare.security;

import com.hongdatchy.bikeshare.entities.model.Admin;
import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.repo.AdminRepoJpa;
import com.hongdatchy.bikeshare.repo.BlackListRepoJpa;
import com.hongdatchy.bikeshare.repo.UserRepoJpa;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Order(1)
@AllArgsConstructor
public class JWTFilterAdmin implements Filter {
    private final AdminRepoJpa adminRepo;
    private final JWTService jwtService;
    private final BlackListRepoJpa blackListRepo;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("token");
        if (token != null) {
            String email = jwtService.decode(token);
            if (email != null){
                List<Admin> admins = adminRepo.findByEmail(email);
                if(admins.size() == 1 && blackListRepo.findByToken(token).size() ==0){
                    filterChain.doFilter(request, response);
                }else {
                    response.setStatus(401);
                }
            } else {
                response.setStatus(401);
            }
        }else {
            response.setStatus(401);
        }
    }
}