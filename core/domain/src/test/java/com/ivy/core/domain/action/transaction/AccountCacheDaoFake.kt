package com.ivy.core.domain.action.transaction

import com.ivy.core.persistence.algorithm.accountcache.AccountCacheDao
import com.ivy.core.persistence.algorithm.accountcache.AccountCacheEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.time.Instant

class AccountCacheDaoFake: AccountCacheDao {

    private val accounts = MutableStateFlow<List<AccountCacheEntity>>(emptyList())

    override fun findAccountCache(accountId: String): Flow<AccountCacheEntity?> {
        return accounts
            .map { entities ->
                entities.find { it.accountId == accountId }
            }
    }

    override suspend fun findTimestampById(accountId: String): Instant? {
        return accounts.value.find { it.accountId == accountId }?.timestamp
    }

    override suspend fun save(cache: AccountCacheEntity) {
        accounts.value += cache
    }

    override suspend fun delete(accountId: String) {
        val account = accounts.value.find { it.accountId == accountId } ?: return
        accounts.value -= account
    }

    override suspend fun deleteAll() {
        accounts.value = emptyList()
    }
}