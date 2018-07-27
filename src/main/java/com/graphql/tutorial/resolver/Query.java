package com.graphql.tutorial.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.graphql.tutorial.dto.Link;
import com.graphql.tutorial.repo.LinkRepository;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final LinkRepository linkRepository;

    public Query(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }
}