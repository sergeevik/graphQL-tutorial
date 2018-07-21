package com.graphql.tutorial.dto;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.graphql.tutorial.repo.LinkRepository;

public class Mutation implements GraphQLMutationResolver {

    private final LinkRepository linkRepository;

    public Mutation(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link createLink(String url, String description) {
        Link newLink = new Link(url, description);
        linkRepository.saveLink(newLink);
        return newLink;
    }
}
