@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ivy.exchangeRates

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import com.ivy.MainCoroutineExtension
import com.ivy.core.domain.action.settings.basecurrency.BaseCurrencyFlow
import com.ivy.core.persistence.algorithm.calc.Rate
import com.ivy.exchangeRates.data.RateUi
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
internal class RatesStateFlowTest {

    private lateinit var ratesFlow: RatesStateFlow
    private lateinit var baseCurrencyFlow: BaseCurrencyFlow
    private lateinit var ratesDaoFake: RatesDaoFake

    @BeforeEach
    fun setUp() {
        baseCurrencyFlow = mockk()
        every { baseCurrencyFlow.invoke() } returns flowOf("", "EUR")

        ratesDaoFake = RatesDaoFake()

        ratesFlow = RatesStateFlow(
            baseCurrencyFlow = baseCurrencyFlow,
            ratesDao = ratesDaoFake
        )
    }

    @Test
    fun `Test rates flow emissions`() = runTest {
        ratesFlow().test {
            awaitItem() // Initial emission

            val emission1 = awaitItem()

            val overriddenRate = RateUi(from = "EUR", to = "USD", rate = 1.3)
            assertThat(emission1.automatic).doesNotContain(overriddenRate)
            assertThat(emission1.manual).contains(overriddenRate)

            ratesDaoFake.rates.value += Rate(rate = 0.00004, currency = "BTC")

            val emission2 = awaitItem()
            val rate = RateUi(from = "EUR", to = "BTC", rate = 0.00004)
            assertThat(emission2.automatic).contains(rate)
            assertThat(emission2.manual).doesNotContain(rate)
        }
    }
}