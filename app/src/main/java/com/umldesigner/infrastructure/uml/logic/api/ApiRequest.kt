package com.umldesigner.infrastructure.uml.logic.api

/**
 * enum for getting specific method codes so that we know whether we called get, set etc on a method
 * call on the server
 */
enum class ApiRequest {
    GetAll, GetUuid, Put, Post
}