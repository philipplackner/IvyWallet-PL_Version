package com.ivy.core.domain.algorithm.calc

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.ivy.core.persistence.algorithm.calc.CalcTrn
import com.ivy.data.transaction.TransactionType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

internal class RawStatsTest {

    @Test
    fun `Test creating raw stats from transactions`() {
        val tenSecondsAgo = Instant.now().minusSeconds(10)
        val fiveSecondsAgo = Instant.now().minusSeconds(5)
        val threeSecondsAgo = Instant.now().minusSeconds(5)
        val stats = rawStats(
            listOf(
                CalcTrn(
                    amount = 5.0,
                    currency = "EUR",
                    type = TransactionType.Income,
                    time = tenSecondsAgo
                ),
                CalcTrn(
                    amount = 3.0,
                    currency = "USD",
                    type = TransactionType.Expense,
                    time = threeSecondsAgo
                ),
                CalcTrn(
                    amount = 10.0,
                    currency = "USD",
                    type = TransactionType.Expense,
                    time = fiveSecondsAgo
                ),
            )
        )

        assertThat(stats.expensesCount).isEqualTo(2)
        assertThat(stats.newestTrnTime).isEqualTo(threeSecondsAgo)
        assertThat(stats.expenses).isEqualTo(mapOf("USD" to 13.0))

        assertThat(stats.incomesCount).isEqualTo(1)
        assertThat(stats.incomes).isEqualTo(mapOf("EUR" to 5.0))
    }
}