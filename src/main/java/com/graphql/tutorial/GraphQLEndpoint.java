package com.graphql.tutorial;

import com.coxautodev.graphql.tools.SchemaParser;
import com.graphql.tutorial.dto.Mutation;
import com.graphql.tutorial.dto.Query;
import com.graphql.tutorial.repo.LinkRepository;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends HttpServlet {
    private SimpleGraphQLServlet graph;

    public GraphQLEndpoint() {
        graph = SimpleGraphQLServlet.builder(buildSchema()).build();
    }

    private static GraphQLSchema buildSchema() {
        LinkRepository linkRepository = new LinkRepository();
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(linkRepository), new Mutation(linkRepository))
                .build()
                .makeExecutableSchema();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        graph.service(req, resp);
    }
}
