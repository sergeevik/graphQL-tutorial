package com.graphql.tutorial.dto;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.graphql.tutorial.repo.LinkRepository;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;

    public Mutation(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link createLink(String id, String url, String description) {
        Link newLink = new Link(id, url, description);
        linkRepository.saveLink(newLink);
        return newLink;
    }
}
