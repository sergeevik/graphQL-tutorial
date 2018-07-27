package com.graphql.tutorial.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.tutorial.dto.SigninPayload;
import com.graphql.tutorial.dto.User;

public class SigninResolver implements GraphQLResolver<SigninPayload> {
    public User user(SigninPayload payload){
        return payload.getUser();
    }
}
