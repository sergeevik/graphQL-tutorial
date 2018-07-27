package com.graphql.tutorial.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.tutorial.dto.Link;
import com.graphql.tutorial.dto.User;
import com.graphql.tutorial.dto.Vote;
import com.graphql.tutorial.repo.LinkRepository;
import com.graphql.tutorial.repo.UserRepository;

public class VoteResolver implements GraphQLResolver<Vote> {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public User user(Vote vote) {
        return userRepository.findById(vote.getUserId());
    }

    public Link link(Vote vote) {
        return linkRepository.findById(vote.getLinkId());
    }
}