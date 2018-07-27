package com.graphql.tutorial.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.tutorial.dto.Link;
import com.graphql.tutorial.dto.User;
import com.graphql.tutorial.repo.UserRepository;

public class LinkResolver implements GraphQLResolver<Link> {
    private final UserRepository repository;

    public LinkResolver(UserRepository repository) {
        this.repository = repository;
    }

    public User postedBy(Link link){
        if (link.getUserId() == null){
            return null;
        }else {
            return repository.findById(link.getUserId());
        }
    }
}
