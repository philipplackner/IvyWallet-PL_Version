package com.ivy.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ivy.common.androidtest.IvyAndroidTest
import com.ivy.common.androidtest.test_data.saveAccountWithTransactions
import com.ivy.common.androidtest.test_data.transactionWithTime
import com.ivy.navigation.Navigator
import com.ivy.navigation.destinations.main.Home
import com.ivy.wallet.ui.RootActivity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenTest: IvyAndroidTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<RootActivity>()

    @Inject
    lateinit var navigator: Navigator

    @Test
    fun testSelectingDateRange() = runBlocking<Unit> {
        val date = LocalDate.of(2023, 7, 23)
        setDate(date)

        val transaction1 = transactionWithTime(Instant.parse("2023-07-24T09:00:00Z")).copy(
            title = "Transaction1"
        )
        val transaction2 = transactionWithTime(Instant.parse("2023-08-01T09:00:00Z")).copy(
            title = "Transaction2"
        )
        val transaction3 = transactionWithTime(Instant.parse("2023-08-31T09:00:00Z")).copy(
            title = "Transaction3"
        )
        db.saveAccountWithTransactions(
            transactions = listOf(transaction1, transaction2, transaction3)
        )

        composeRule.awaitIdle()
        composeRule.runOnUiThread {
            navigator.navigate(Home.route)
        }

        composeRule.onNodeWithText(date.month.name, ignoreCase = true).performClick()

        composeRule
            .onNodeWithText("August")
            .assertIsDisplayed()
            .performClick()
        composeRule.onNodeWithText("Aug. 01").assertIsDisplayed()
        composeRule.onNodeWithText("Aug. 31").assertIsDisplayed()

        composeRule.onNodeWithText("Done").performClick()

        composeRule.onNodeWithText("Upcoming").performClick()

        composeRule.onNodeWithText("Transaction1").assertDoesNotExist()
        composeRule.onNodeWithText("Transaction2").assertIsDisplayed()
        composeRule.onNodeWithText("Transaction3").assertIsDisplayed()
    }

}