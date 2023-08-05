package com.ivy.core.domain.action.transaction

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
import java.time.LocalDateTime
import java.util.UUID

fun account(): Account {
    return Account(
        id = UUID.randomUUID(),
        name = "Test account",
        currency = "EUR",
        color = 0x00f15e,
        icon = null,
        excluded = false,
        folderId = null,
        orderNum = 1.0,
        state = AccountState.Default,
        sync = Sync(
            state = SyncState.Syncing,
            lastUpdated = LocalDateTime.now()
        )
    )
}

fun tag(): Tag {
    return Tag(
        id = UUID.randomUUID().toString(),
        color = 0x00f15e,
        name = "Test tag",
        orderNum = 1.0,
        state = TagState.Default,
        sync = Sync(SyncState.Syncing, LocalDateTime.now())
    )
}

fun attachment(associatedId: String): Attachment {
    return Attachment(
        id = UUID.randomUUID().toString(),
        associatedId = associatedId,
        uri = "test",
        source = AttachmentSource.Local,
        filename = null,
        type = AttachmentType.Image,
        sync = Sync(SyncState.Syncing, LocalDateTime.now())
    )
}

fun transaction(
    account: Account
): Transaction {
    return Transaction(
        id = UUID.randomUUID(),
        account = account,
        type = TransactionType.Expense,
        value = Value(
            amount = 50.0,
            currency = "EUR"
        ),
        category = null,
        time = TrnTime.Actual(LocalDateTime.now()),
        title = "Test transaction",
        description = null,
        state = TrnState.Default,
        purpose = TrnPurpose.Fee,
        tags = listOf(),
        attachments = listOf(),
        metadata = TrnMetadata(
            recurringRuleId = null,
            loanId = null,
            loanRecordId = null
        ),
        sync = Sync(
            state = SyncState.Syncing,
            lastUpdated = LocalDateTime.now()
        )
    )
}