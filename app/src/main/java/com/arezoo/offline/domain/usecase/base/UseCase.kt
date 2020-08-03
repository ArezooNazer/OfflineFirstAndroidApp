package com.arezoo.offline.domain.usecase.base

abstract class UseCase<in R, out T> {
    abstract fun execute(input: R ): T
}