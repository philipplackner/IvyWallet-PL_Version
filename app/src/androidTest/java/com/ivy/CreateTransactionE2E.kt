package com.ivy

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ivy.common.androidtest.IvyAndroidTest
import com.ivy.core.data.CategoryType
import com.ivy.home.HomeScreenRobot
import com.ivy.navigation.Navigator
import com.ivy.transaction.NewTransactionRobot
import com.ivy.wallet.ui.RootActivity
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CreateTransactionE2E: IvyAndroidTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<RootActivity>()

    @Inject
    lateinit var navigator: Navigator

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