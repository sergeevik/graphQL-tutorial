package com.graphql.tutorial;

import com.coxautodev.graphql.tools.SchemaParser;
import com.graphql.tutorial.dto.GraphQlQuery;
import com.graphql.tutorial.dto.Mutation;
import com.graphql.tutorial.dto.Query;
import com.graphql.tutorial.repo.LinkRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import java.util.Map;

@RequestMapping("/graphql")
@RestController
public class GraphQLEndpoint extends HttpServlet {
    private final LinkRepository linkRepository;
    private GraphQL graph;

    @Autowired
    public GraphQLEndpoint(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
        graph = GraphQL.newGraphQL(buildSchema()).build();
    }

    private GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(linkRepository), new Mutation(linkRepository))
                .build()
                .makeExecutableSchema();
    }

    @PostMapping
    public Map<String, Object> doPost(@RequestBody GraphQlQuery query) {
        return graph.execute(query.getQuery()).toSpecification();
    }
}
