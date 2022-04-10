package com.hongdatchy.bikeshare.security;



import com.hongdatchy.bikeshare.entities.model.User;
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
public class JWTFilterUser implements Filter {
    private final UserRepoJpa userRepo;
    private final JWTService jwtService;
    private final BlackListRepoJpa blackListRepo;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("token");

        if (token != null) {
            String phone = jwtService.decode(token);
            if (phone != null){
                List<User> users = userRepo.findByEmail(phone);
                if(users.size() == 1 && blackListRepo.findByToken(token).size() ==0){
                    request.setAttribute("userId", users.get(0).getId());
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
