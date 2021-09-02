package com.example.assignment4.errors

// WORK AROUND EXPLAINED BELOW WITH JUSTIFICATION:
// Known error from GraphQL error from GraphQLError interface: https://github.com/graphql-java/graphql-java/blob/master/src/main/java/graphql/GraphQLError.java
// "Accidental override on getMessage() override function", check issue here: https://youtrack.jetbrains.com/issue/KT-6653
// Work around used for the time-being: https://youtrack.jetbrains.com/issue/KT-6653#comment=27-2666539
// Work around also suggested by team @ GraphQL-java: https://github.com/graphql-java/graphql-java/issues/1022#issuecomment-386922550

interface GraphQLError {
    fun getErrorMessage(): String?
}

// Custom exception to use for custom error handling.
class CustomException(private val errorMessage: String) : RuntimeException(errorMessage), GraphQLError {
    override fun getErrorMessage(): String? {
        return this.errorMessage
    }
}

// FULL CODE UNDER WHEN BUG IS SORTED OR A BETTER WORKAROUND IS SUGGESTED!!!

//class CustomException(private val errorCode: String, private val errorMessage: String) : RuntimeException(errorMessage), GraphQLError {
//    override fun getMessage(): String {
//        return this.errorMessage
//    }

//    override fun getExtensions(): Map<String, Any> {
//        val customAttributes = LinkedHashMap<String, Any>()
//
//        customAttributes["errorCode"] = this.errorCode;
//        customAttributes["errorMessage"] = this.getMessage();
//
//        return customAttributes
//    }
//
//    override fun getLocations(): List<SourceLocation>? {
//        return null
//    }
//
//    override fun getErrorType(): ErrorType? {
//        return null
//    }
//}