package com.ivy.old

import android.content.Context
import android.net.Uri
import arrow.core.Either
import arrow.core.computations.either
import com.ivy.backup.base.ImportBackupError
import com.ivy.backup.base.OnImportProgress
import com.ivy.backup.base.WriteBackupDataAct
import com.ivy.backup.base.data.ImportResult
import com.ivy.backup.base.extractBackupJson
import com.ivy.core.domain.action.Action
import com.ivy.old.parse.ParseOldJsonAct
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ImportOldJsonBackupAct @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val parseOldJsonAct: ParseOldJsonAct,
    private val writeBackupDataAct: WriteBackupDataAct,
) : Action<ImportOldJsonBackupAct.Input, Either<ImportBackupError, ImportResult>>() {
    data class Input(
        val backupZipPath: Uri,
        val onProgress: OnImportProgress,
    )

    override suspend fun action(input: Input): Either<ImportBackupError, ImportResult> =
        either {
            val progress = { percent: Float, message: String ->
                input.onProgress.onProgress(percent, message)
            }

            progress(1f, "Unzipping backup JSON...")
            val backupJson = extractBackupJson(context, input.backupZipPath).bind()
            val backupData = parseOldJsonAct(backupJson).bind()
            // endregion

            progress(10f, "Backup JSON parsed. Saving to database...")
            writeBackupDataAct(
                WriteBackupDataAct.Input(
                    backup = backupData,
                    onProgress = object : OnImportProgress {
                        override fun onProgress(percent: Float, message: String) {
                            // Adjust from 13% to 100%
                            val adjustedPercent = 0.13f + (0.87f * percent)
                            progress(adjustedPercent, message)
                        }
                    }
                )
            )

            ImportResult(
                faultyTransfers = backupData.transfers.faulty
            )
        }
}

