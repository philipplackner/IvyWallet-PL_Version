package com.ivy.exchangeRates

import com.ivy.core.persistence.algorithm.calc.Rate
import com.ivy.core.persistence.algorithm.calc.RatesDao
import com.ivy.data.CurrencyCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class RatesDaoFake: RatesDao {

    var rates = MutableStateFlow(
        listOf(
            Rate(1.0, "EUR"),
            Rate(1.2, "USD"),
            Rate(1.8, "CAD"),
        )
    )

    var overrides = MutableStateFlow(
        listOf(
            Rate(1.3, "USD"),
        )
    )

    override fun findAll(baseCurrency: CurrencyCode): Flow<List<Rate>> {
        return rates
    }

    override fun findAllOverrides(baseCurrency: CurrencyCode): Flow<List<Rate>> {
        return overrides
    }
}