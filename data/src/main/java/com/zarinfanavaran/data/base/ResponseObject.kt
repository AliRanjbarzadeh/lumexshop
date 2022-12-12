package com.zarinfanavaran.data.base

/**
 * Created by Ali Ranjbarzadeh on 9/29/2022 AD.
 */
interface ResponseObject<out DomainObject : Any?> {
    fun toDomain(): DomainObject
}

