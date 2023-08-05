package com.ivy.core.domain.action.transaction

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.ivy.core.domain.action.Action
import com.ivy.core.domain.algorithm.accountcache.InvalidateAccCacheAct
import com.ivy.data.Sync
import com.ivy.data.SyncState
import com.ivy.data.Value
import com.ivy.data.account.Account
import com.ivy.data.account.AccountState
import com.ivy.data.attachment.Attachment
import com.ivy.data.attachment.AttachmentSource
import com.ivy.data.attachment.AttachmentType
import com.ivy.data.tag.Tag
import com.ivy.data.tag.TagState
import com.ivy.data.transaction.Transaction
import com.ivy.data.transaction.TransactionType
import com.ivy.data.transaction.TrnMetadata
import com.ivy.data.transaction.TrnPurpose
import com.ivy.data.transaction.TrnState
import com.ivy.data.transaction.TrnTime
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

internal class WriteTrnsActTest {

    private lateinit var writeTrnsAct: WriteTrnsAct
    private lateinit var transactionDaoFake: TransactionDaoFake
    private lateinit var timeProviderFake: TimeProviderFake
    private lateinit var accountCacheDaoFake: AccountCacheDaoFake

    @BeforeEach
    fun setUp() {
        transactionDaoFake = TransactionDaoFake()
        timeProviderFake = TimeProviderFake()
        accountCacheDaoFake = AccountCacheDaoFake()
        writeTrnsAct = WriteTrnsAct(
            transactionDao = transactionDaoFake,
            trnsSignal = TrnsSignal(),
            timeProvider = timeProviderFake,
            invalidateAccCacheAct = InvalidateAccCacheAct(
                accountCacheDao = accountCacheDaoFake,
                timeProvider = timeProviderFake
            ),
            accountCacheDao = accountCacheDaoFake
        )
    }

    @Test
    fun `Test create new transaction with expense`() = runBlocking<Unit> {
        val account = account()
        val transactionId = UUID.randomUUID()
        val tag = tag()
        val attachment = attachment(associatedId = transactionId.toString())
        val transaction = transaction(account = account).copy(
            id = transactionId,
            tags = listOf(tag),
            attachments = listOf(attachment)
        )

        writeTrnsAct(WriteTrnsAct.Input.CreateNew(transaction))

        val cachedTransaction = transactionDaoFake.transactions.find {
            it.id == transactionId.toString()
        }
        val cachedTag = transactionDaoFake.tags.find { it.tagId == tag.id }
        val cachedAttachement = transactionDaoFake.attachments.find { it.id == attachment.id }

        assertThat(cachedTransaction).isNotNull()
        assertThat(cachedTransaction?.type).isEqualTo(TransactionType.Expense)

        assertThat(cachedTag).isNotNull()
        assertThat(cachedAttachement).isNotNull()
    }
}