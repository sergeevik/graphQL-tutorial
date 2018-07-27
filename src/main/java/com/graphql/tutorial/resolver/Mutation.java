package com.graphql.tutorial.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.graphql.tutorial.AuthContext;
import com.graphql.tutorial.dto.*;
import com.graphql.tutorial.repo.LinkRepository;
import com.graphql.tutorial.repo.UserRepository;
import com.graphql.tutorial.repo.VoteRepository;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public Mutation(LinkRepository linkRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.linkRepository = linkRepository;
        this.userRepository =  userRepository;
        this.voteRepository = voteRepository;
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

    public Vote createVote(String linkId, String userId) {
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        return voteRepository.saveVote(new Vote(now, userId, linkId));
    }

}
