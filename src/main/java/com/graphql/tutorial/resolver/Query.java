package com.graphql.tutorial.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.graphql.tutorial.dto.Link;
import com.graphql.tutorial.dto.User;
import com.graphql.tutorial.dto.Vote;
import com.graphql.tutorial.repo.LinkRepository;
import com.graphql.tutorial.repo.UserRepository;
import com.graphql.tutorial.repo.VoteRepository;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public Query(LinkRepository linkRepository, VoteRepository voteRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }


    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }

    public List<User> allUsers() {
        return userRepository.getAll();
    }
    public List<Vote> allVotes() {
        return voteRepository.getAll();
    }
}