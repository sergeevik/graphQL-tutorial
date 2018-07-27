package com.graphql.tutorial;

import com.graphql.tutorial.dto.User;
import com.graphql.tutorial.repo.UserRepository;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLContextBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthContextBuilder implements GraphQLContextBuilder {
    private final UserRepository userRepository;

    public AuthContextBuilder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public GraphQLContext build(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ",""))
                .map(userRepository::findById)
                .orElse(null);

        return new AuthContext(user, request, response);
    }
}
