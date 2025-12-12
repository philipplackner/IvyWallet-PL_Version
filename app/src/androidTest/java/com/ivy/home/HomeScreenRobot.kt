package com.ivy.home

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.ivy.IvyComposeRule
import com.ivy.common.time.provider.TimeProvider
import com.ivy.data.CurrencyCode
import com.ivy.navigation.Navigator
import com.ivy.navigation.destinations.main.Home
import kotlinx.coroutines.runBlocking

class HomeScreenRobot(
    private val composeRule: IvyComposeRule
) {
    fun navigateTo(navigator: Navigator): HomeScreenRobot {
        runBlocking {
            composeRule.awaitIdle()
            composeRule.runOnUiThread {
                navigator.navigate(Home.route) {
                    popUpTo(Home.route) {
                        inclusive = false
                    }
                }
            }
        }
        return this
    }

    fun openDateRangeSheet(timeProvider: TimeProvider): HomeScreenRobot {
        composeRule
            .onNodeWithText(timeProvider.dateNow().month.name, ignoreCase = true, substring = true)
            .performClick()
        return this
    }

    fun selectMonth(monthName: String): HomeScreenRobot {
        // Scroll to the month in horizontal LazyRow (may be off-screen)
        composeRule
            .onNode(hasHorizontalScrollAction())
            .performScrollToNode(hasText(monthName))

        composeRule
            .onNodeWithText(monthName, ignoreCase = true)
            .assertIsDisplayed()
            .performClick()
        return this
    }

    private fun hasHorizontalScrollAction(): SemanticsMatcher {
        return hasScrollAction() and SemanticsMatcher.keyIsDefined(
            SemanticsProperties.HorizontalScrollAxisRange
        )
    }

    fun assertDateIsDisplayed(day: Int, month: String): HomeScreenRobot {
        val paddedDay = day.toString().padStart(2, '0')
        composeRule
            .onNodeWithText("${month.take(3)}. $paddedDay", substring = true)
            .assertIsDisplayed()
        return this
    }

    fun clickDone(): HomeScreenRobot {
        composeRule.onNodeWithText("Done").performClick()
        return this
    }

    fun clickUpcoming(): HomeScreenRobot {
        composeRule.onNodeWithText("Upcoming").performClick()
        return this
    }

    fun assertTransactionDoesNotExist(transactionTitle: String): HomeScreenRobot {
        composeRule.onNodeWithText(transactionTitle).assertDoesNotExist()
        return this
    }

    fun assertTransactionIsDisplayed(transactionTitle: String): HomeScreenRobot {
        composeRule.onNodeWithText(transactionTitle).assertIsDisplayed()
        return this
    }

    fun assertTransactionIsDisplayed(
        transactionTitle: String,
        accountName: String,
        categoryName: String
    ): HomeScreenRobot {
        composeRule.onNodeWithText(transactionTitle).assertIsDisplayed()
        composeRule.onNodeWithText(accountName).assertIsDisplayed()
        composeRule.onNodeWithText(categoryName).assertIsDisplayed()
        return this
    }

    fun openOverdue(): HomeScreenRobot {
        composeRule
            .onNodeWithText("Overdue")
            .performClick()
        return this
    }

    fun assertBalanceIsDisplayed(amount: Double, currency: CurrencyCode): HomeScreenRobot {
        val formattedAmount = if(amount % 1.0 == 0.0) {
            amount.toInt().toString()
        } else amount.toString()

        composeRule
            .onAllNodes(
                hasText(formattedAmount) and hasAnySibling(hasText(currency)),
                useUnmergedTree = true
            )
            .onFirst()
            .assertIsDisplayed()
        return this
    }

    fun clickGet(): HomeScreenRobot {
        composeRule.onNodeWithText("Get").performClick()
        return this
    }

    fun clickNewTransaction(): HomeScreenRobot {
        composeRule.onNodeWithContentDescription("Add new transaction").performClick()
        return this
    }

    fun clickExpense(): HomeScreenRobot {
        composeRule.onNodeWithContentDescription("Create new expense").performClick()
        return this
    }

    fun assertTotalExpensesIs(amount: Int): HomeScreenRobot {
        composeRule
            .onAllNodesWithTag("amount", useUnmergedTree = true)
            .onLast()
            .assertTextEquals(amount.toString())
        return this
    }

}