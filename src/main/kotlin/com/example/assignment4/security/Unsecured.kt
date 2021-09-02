package com.example.assignment4.security

/**
 * Marking annotation that will switch off security check for given method.
 * Works only for methods defined in GraphQL Resolvers
 */
//@Target(AnnotationTarget.FUNCTION)
@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Unsecured