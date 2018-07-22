package com.graphql.tutorial.dto;

public class Link {

    private String id;
    private final String url;
    private final String description;

    public Link(String url, String description) {
        this.url = url;
        this.description = description;
    }

    public Link(String id, String url, String description) {
        this.id = id;
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
