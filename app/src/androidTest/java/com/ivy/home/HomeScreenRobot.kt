package com.ivy.home

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasScrollToNodeAction
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
        runBlocking {
            composeRule.awaitIdle()
            // UI shows "July. 2023" but month.name returns "JULY"
            composeRule
                .onNodeWithText(timeProvider.dateNow().month.name, ignoreCase = true, substring = true)
                .performClick()
            composeRule.awaitIdle()
        }
        return this
    }

    fun selectMonth(monthName: String): HomeScreenRobot {
        runBlocking {
            composeRule.awaitIdle()
            // Scroll LazyRow to find the month, then click it
            composeRule
                .onNode(horizontalScrollableMatcher())
                .performScrollToNode(hasText(monthName))
            composeRule.awaitIdle()
            composeRule.onNodeWithText(monthName).performClick()
        }
        return this
    }

    fun assertDateIsDisplayed(day: Int, month: String): HomeScreenRobot {
        val paddedDay = day.toString().padStart(2, '0')
        composeRule
            .onNodeWithText("${month.take(3)}. $paddedDay", substring = true)
            .assertIsDisplayed()
        return this
    }

    fun clickDone(): HomeScreenRobot {
        runBlocking {
            composeRule.awaitIdle()
            composeRule.onNodeWithText("Done").performClick()
            composeRule.awaitIdle()
        }
        return this
    }

    fun clickUpcoming(): HomeScreenRobot {
        runBlocking {
            composeRule.awaitIdle()
            composeRule.onNodeWithText("Upcoming", substring = true).performClick()
        }
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

    fun openOverdue(): HomeScreenRobot {
        composeRule.onNodeWithText("Overdue").performClick()
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

    private fun horizontalScrollableMatcher() =
        hasScrollToNodeAction() and SemanticsMatcher.keyIsDefined(SemanticsProperties.HorizontalScrollAxisRange)
}