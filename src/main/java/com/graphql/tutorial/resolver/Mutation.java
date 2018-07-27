package com.graphql.tutorial.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.graphql.tutorial.AuthContext;
import com.graphql.tutorial.dto.AuthData;
import com.graphql.tutorial.dto.Link;
import com.graphql.tutorial.dto.SigninPayload;
import com.graphql.tutorial.dto.User;
import com.graphql.tutorial.repo.LinkRepository;
import com.graphql.tutorial.repo.UserRepository;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public Mutation(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository =  userRepository;
    }

    public Link createLink(String url, String description, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        Link newLink = new Link(url, description, context.getUser().getId());
        linkRepository.saveLink(newLink);
        return newLink;
    }

    public User createUser(String name, AuthData data){
        User user = new User(name, data.getEmail(), data.getPassword());
        return userRepository.saveUser(user);
    }

    public SigninPayload signinUser(AuthData data){
        User user = userRepository.findByEmail(data.getEmail());
        if (user.getPassword().equals(data.getPassword())){
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("invalid credentials");
    }


}
