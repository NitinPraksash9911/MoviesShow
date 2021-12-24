package com.example.moviesshow.network.utils

/**
 * Marks a request for caching
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Cacheable {
}