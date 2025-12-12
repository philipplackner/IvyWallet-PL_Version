package com.ivy

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import com.ivy.common.androidtest.IvyAndroidTest
import com.ivy.core.data.CategoryType
import com.ivy.core.persistence.datastore.dataStore
import com.ivy.home.HomeScreenRobot
import com.ivy.navigation.Navigator
import com.ivy.transaction.NewTransactionRobot
import com.ivy.wallet.ui.RootActivity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CreateTransactionE2E: IvyAndroidTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<RootActivity>()

    override fun setUp() {
        super.setUp()
        skipOnboarding()
    }

    @Inject
    lateinit var navigator: Navigator

    private fun skipOnboarding() {
        val context: Context = ApplicationProvider.getApplicationContext()
        runBlocking {
            val onboardingFinishedKey = booleanPreferencesKey("onboarding_finished")
            context.dataStore.edit { prefs ->
                prefs[onboardingFinishedKey] = true
            }
        }
    }

    @Test
    fun testCreatingExpenseWithCategoriesAndAccount() {
        val homeScreenRobot = HomeScreenRobot(composeRule)

        homeScreenRobot
            .navigateTo(navigator)
            .clickNewTransaction()
            .clickExpense()

        NewTransactionRobot(composeRule)
            .addAccount("PayPal")
            .selectAccount("PayPal")
            .enterTransactionAmount(65)
            .addCategory("Transport", CategoryType.Expense, null)
            .addCategory("Car", CategoryType.Expense, "Transport")
            .chooseSubCategory("Transport", "Car")
            .enterTransactionTitle("Fuel")
            .enterTransactionDescription("For my Ford")
            .clickAddTransaction()

        homeScreenRobot
            .assertTotalExpensesIs(65)
            .assertTransactionIsDisplayed(
                transactionTitle = "Fuel",
                accountName = "PayPal",
                categoryName = "Car"
            )
    }
}