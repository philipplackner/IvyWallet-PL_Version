package com.ivy.common.androidtest

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import com.ivy.common.time.provider.TimeProvider
import com.ivy.core.domain.action.period.SetSelectedPeriodAct
import com.ivy.core.domain.pure.time.currentMonthlyPeriod
import com.ivy.core.persistence.IvyWalletCoreDb
import com.ivy.core.persistence.datastore.dataStore
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import java.time.LocalDate
import javax.inject.Inject

abstract class IvyAndroidTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: IvyWalletCoreDb

    @Inject
    lateinit var timeProvider: TimeProvider

    @Inject
    lateinit var setSelectedPeriodAct: SetSelectedPeriodAct

    protected lateinit var context: Context

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
        db.clearAllTables()
        clearDataStore()
    }
    //After method was removed since Room connection pooler once closed can't be reused.
    //This change was made on Room 2.6

    private fun clearDataStore() = runBlocking {
        context.dataStore.edit { it.clear() }
    }

    protected fun setDate(date: LocalDate) {
        (timeProvider as TimeProviderFake).apply {
            timeNow = date.atTime(12, 0)
            dateNow = date
        }
    }

    protected suspend fun refreshPeriod() {
        val period = currentMonthlyPeriod(
            startDayOfMonth = 1,
            timeProvider = timeProvider
        )
        setSelectedPeriodAct(period)
    }
}