package com.ivy.core.domain.action.transaction

import androidx.sqlite.db.SupportSQLiteQuery
import com.ivy.core.persistence.dao.trn.AccountIdAndTrnTime
import com.ivy.core.persistence.dao.trn.TransactionDao
import com.ivy.core.persistence.entity.attachment.AttachmentEntity
import com.ivy.core.persistence.entity.trn.TransactionEntity
import com.ivy.core.persistence.entity.trn.TrnMetadataEntity
import com.ivy.core.persistence.entity.trn.TrnTagEntity
import com.ivy.data.SyncState

class TransactionDaoFake: TransactionDao() {

    val transactions = mutableListOf<TransactionEntity>()
    val tags = mutableListOf<TrnTagEntity>()
    val attachments = mutableListOf<AttachmentEntity>()
    val metadatas = mutableListOf<TrnMetadataEntity>()

    override suspend fun saveTrnEntity(entity: TransactionEntity) {
        transactions.add(entity)
    }

    override suspend fun updateTrnTagsSyncByTrnId(trnId: String, sync: SyncState) {
        val transaction = transactions.find { it.id == trnId } ?: return
        val index = transactions.indexOf(transaction)
        transactions[index] = transaction.copy(sync = sync)
    }

    override suspend fun saveTags(entity: List<TrnTagEntity>) {
        tags.addAll(entity)
    }

    override suspend fun updateAttachmentsSyncByAssociatedId(
        associatedId: String,
        sync: SyncState
    ) {
        val attachment = attachments.find { it.associatedId == associatedId } ?: return
        val index = attachments.indexOf(attachment)
        attachments[index] = attachment.copy(sync = sync)
    }

    override suspend fun saveAttachments(entity: List<AttachmentEntity>) {
        attachments.addAll(entity)
    }

    override suspend fun updateMetadataSyncByTrnId(trnId: String, sync: SyncState) {
        val metadata = metadatas.find { it.trnId == trnId } ?: return
        val index = metadatas.indexOf(metadata)
        metadatas[index] = metadata.copy(sync = sync)
    }

    override suspend fun saveMetadata(entity: List<TrnMetadataEntity>) {
        metadatas.addAll(entity)
    }

    override suspend fun findAllBlocking(): List<TransactionEntity> {
        return transactions
    }

    // Not supported for fake
    override suspend fun findBySQL(query: SupportSQLiteQuery): List<TransactionEntity> {
        return transactions
    }

    override suspend fun findAccountIdAndTimeById(trnId: String): AccountIdAndTrnTime? {
        val transaction = transactions.find {
            it.id == trnId && it.sync == SyncState.Deleting
        } ?: return null

        return AccountIdAndTrnTime(
            accountId = transaction.accountId,
            time = transaction.time,
            timeType = transaction.timeType
        )
    }

    override suspend fun updateTrnEntitySyncById(trnId: String, sync: SyncState) {
        val transaction = transactions.find { it.id == trnId } ?: return
        val index = transactions.indexOf(transaction)
        transactions[index] = transaction.copy(sync = sync)
    }
}