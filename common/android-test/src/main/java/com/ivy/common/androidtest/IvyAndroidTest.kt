package com.ivy.common.androidtest

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import com.ivy.core.persistence.IvyWalletCoreDb
import com.ivy.core.persistence.datastore.dataStore
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class IvyAndroidTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: IvyWalletCoreDb

    protected lateinit var context: Context

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
        db.clearAllTables()
        clearDataStore()
    }

    @After
    open fun tearDown() {
        db.close()
    }

    private fun clearDataStore() = runBlocking {
        context.dataStore.edit {
            it.clear()
        }
    }
}