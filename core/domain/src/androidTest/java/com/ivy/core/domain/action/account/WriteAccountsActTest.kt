package com.ivy.core.domain.action.account

import assertk.Assert
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.ivy.account
import com.ivy.common.androidtest.IvyAndroidTest
import com.ivy.core.domain.action.data.Modify
import com.ivy.core.persistence.dao.account.AccountDao
import com.ivy.core.persistence.entity.account.AccountEntity
import com.ivy.data.Sync
import com.ivy.data.SyncState
import com.ivy.data.account.Account
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class WriteAccountsActTest: IvyAndroidTest() {

    @Inject
    lateinit var writeAccountsAct: WriteAccountsAct

    @Inject
    lateinit var accountDao: AccountDao

    @Test
    fun testSaveUpdateAccount() = runTest {
        val syncTime = LocalDateTime
            .now()
            .plusHours(3)
            .truncatedTo(ChronoUnit.SECONDS)

        val accountToSave = account().copy(
            sync = Sync(SyncState.Syncing, syncTime)
        )
        writeAccountsAct(Modify.save(accountToSave))

        val createdAccountFromDb = accountDao.findAllBlocking().first()
        assertThat(createdAccountFromDb)
            .transformToAccount()
            .isEqualTo(accountToSave)

        val updatedAccount = accountToSave.copy(
            name = "updated",
            currency = "CAD"
        )
        writeAccountsAct(Modify.save(updatedAccount))

        val accountsFromDb = accountDao.findAllBlocking()
        assertThat(accountsFromDb).hasSize(1)

        val updatedAccountFromDb = accountsFromDb.first()
        assertThat(updatedAccountFromDb)
            .transformToAccount()
            .isEqualTo(updatedAccount)
    }

    private fun Assert<AccountEntity>.transformToAccount(): Assert<Account> {
        return transform {
            Account(
                id = UUID.fromString(it.id),
                name = it.name,
                currency = it.currency,
                color = it.color,
                icon = it.icon,
                excluded = it.excluded,
                folderId = it.folderId?.let { UUID.fromString(it) },
                orderNum = it.orderNum,
                state = it.state,
                sync = Sync(
                    state = it.sync,
                    lastUpdated = LocalDateTime.ofInstant(it.lastUpdated, ZoneId.systemDefault())
                )
            )
        }
    }
}