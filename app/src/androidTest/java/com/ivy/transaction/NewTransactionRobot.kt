package com.ivy.transaction

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.ivy.IvyComposeRule
import com.ivy.core.data.CategoryType

class NewTransactionRobot(
    private val composeRule: IvyComposeRule
) {
    fun addAccount(name: String): NewTransactionRobot {
        composeRule.onNodeWithText("Add account").performClick()
        composeRule.onNodeWithContentDescription("New account").performTextInput(name)
        composeRule.onAllNodesWithText("Add account").onLast().performClick()
        return this
    }

    fun selectAccount(name: String): NewTransactionRobot {
        composeRule.onNodeWithText(name).performClick()
        return this
    }

    fun enterTransactionAmount(amount: Int): NewTransactionRobot {
        val digits = amount.toString().map { it.digitToInt() }
        digits.forEach { digit ->
            composeRule.onNode(
                hasText(digit.toString()) and hasClickAction()
            ).performClick()
        }
        composeRule.onNodeWithText("Enter").performClick()
        return this
    }

    fun addCategory(name: String, type: CategoryType, parentName: String?): NewTransactionRobot {
        clickAddCategoryOnNewCategoryModal()
            .enterCategoryName(name)
            .selectCategoryType(type)
            .apply {
                if(parentName != null) {
                    chooseParent(parentName)
                }
            }
            .clickAddCategoryOnNewCategoryModal()
        return this
    }

    private fun clickAddCategoryOnNewCategoryModal(): NewTransactionRobot {
        composeRule.onAllNodesWithText("Add category").onLast().performClick()
        return this
    }

    private fun enterCategoryName(name: String): NewTransactionRobot {
        composeRule.onNodeWithContentDescription("New Category").performTextInput(name)
        return this
    }

    private fun selectCategoryType(type: CategoryType): NewTransactionRobot {
        composeRule.onNode(
            hasText(type.toString()) and hasTestTag("category_type_button")
        ).performClick()
        return this
    }

    private fun chooseParent(parentName: String): NewTransactionRobot {
        composeRule.onNodeWithText("Choose parent").performClick()
        composeRule.onAllNodesWithText(parentName).onLast().performClick()
        return this
    }

    fun chooseSubCategory(parentName: String, subName: String): NewTransactionRobot {
        composeRule.onNodeWithText(parentName).performClick()
        composeRule.onNodeWithText(subName).performClick()
        return this
    }

    fun enterTransactionTitle(title: String): NewTransactionRobot {
        composeRule.onNodeWithContentDescription("Title").performTextInput(title)
        return this
    }

    fun enterTransactionDescription(description: String): NewTransactionRobot {
        composeRule.onNodeWithText("Add description").performClick()
        composeRule
            .onNodeWithContentDescription("Enter any details here")
            .performTextInput(description)
        composeRule.onAllNodesWithText("Add").onLast().performClick()
        return this
    }

    fun clickAddTransaction(): NewTransactionRobot {
        composeRule.onNodeWithText("Add").performClick()
        return this
    }
}