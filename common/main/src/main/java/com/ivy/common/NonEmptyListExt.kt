package com.ivy.common

import arrow.core.NonEmptyList

fun <T> List<T>.toNonEmptyList(): NonEmptyList<T> = NonEmptyList.fromListUnsafe(this)

fun <T> List<T>.toNonEmptyListOrNull(): NonEmptyList<T>? = NonEmptyList.fromList(this).orNull()
fun <T> Set<T>.toNonEmptyList(): NonEmptyList<T> = NonEmptyList.fromListUnsafe(this.toList())