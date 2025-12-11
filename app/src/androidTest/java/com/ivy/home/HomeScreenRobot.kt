package com.ivy.home

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.ivy.IvyComposeRule
import com.ivy.common.time.provider.TimeProvider
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
        composeRule.waitForIdle()
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
}