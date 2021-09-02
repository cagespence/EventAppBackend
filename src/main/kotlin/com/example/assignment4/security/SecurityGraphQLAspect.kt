//package com.example.assignment4.security
//
//import graphql.schema.DataFetchingEnvironment
//import graphql.schema.DataFetchingEnvironmentImpl
//import graphql.servlet.GraphQLContext
//import org.aspectj.lang.JoinPoint
//import org.aspectj.lang.annotation.Aspect
//import org.aspectj.lang.annotation.Before
//import org.aspectj.lang.annotation.Pointcut
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.core.annotation.Order
//import org.springframework.security.access.AccessDeniedException
//import org.springframework.stereotype.Component
//
//@Aspect
//@Component
//@Order(1)
//class SecurityGraphQLAspect {
//
//    @Autowired
//    private val jwtTokenUtil: JwtTokenUtil? = null
//
//    /**
//     * All graphQLResolver methods can be called only by authenticated user.
//     * @Unsecured annotated methods are excluded
//     */
//    @Before("allGraphQLResolverMethods() && isDefinedInApplication() && !isMethodAnnotatedAsUnsecured()")
//    fun doSecurityCheck(jp: JoinPoint) {
//        print(jp.args.size)
////
////        jp.args.forEach { e ->
////                if (e is DataFetchingEnvironmentImpl) {
////                    val context = e.getContext() as GraphQLContext
////                    val token = context.request.get().getHeader("Authorization");
////                    val email = context.request.get().getHeader("UserEmail")
////                    if (!(jwtTokenUtil!!.validateToken(token, email)!!)) {
////                                    throw AccessDeniedException("User not authenticated")
////                    }
////            }
////         }
//    }
//
//    /**
//     * Matches all beans that implement [com.coxautodev.graphql.tools.GraphQLResolver]
//     * note: `GraphQLMutationResolver`, `GraphQLQueryResolver` etc
//     * extend base GraphQLResolver interface
//     */
//    @Pointcut("target(com.coxautodev.graphql.tools.GraphQLResolver)")
//    private fun allGraphQLResolverMethods() {
//    }
//    /**
//     * Matches all beans in com.mi3o.springgraphqlsecurity package
//     * resolvers must be in this package (subpackages)
//     */
//    @Pointcut("within(com.example.assignment4..*)")
//    private fun isDefinedInApplication() {
//    }
//
//    /**
//     * Any method annotated with @Unsecured
//     */
//    @Pointcut("@annotation(com.example.assignment4.security.Unsecured)")
//    private fun isMethodAnnotatedAsUnsecured() {
//    }
//}
