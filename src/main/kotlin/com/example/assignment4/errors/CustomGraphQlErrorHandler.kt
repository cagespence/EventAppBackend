package com.example.assignment4.errors

import graphql.GraphQLError
import graphql.servlet.core.GenericGraphQLError
import graphql.servlet.core.GraphQLErrorHandler
import org.springframework.stereotype.Component

@Component
class CustomGraphQlErrorHandler: GraphQLErrorHandler {

    override fun processErrors(errors: MutableList<GraphQLError>?): MutableList<GraphQLError> {
        val errorList = mutableListOf<GraphQLError>()
        for(error in errors!!){
            errorList.add(GenericGraphQLError(error.message))
        }
        return errorList
    }

}