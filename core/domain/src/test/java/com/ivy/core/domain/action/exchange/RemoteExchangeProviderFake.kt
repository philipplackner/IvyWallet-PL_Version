package com.ivy.core.domain.action.exchange

import com.ivy.data.CurrencyCode
import com.ivy.data.ExchangeRatesMap
import com.ivy.data.exchange.ExchangeProvider
import com.ivy.exchange.RemoteExchangeProvider

class RemoteExchangeProviderFake: RemoteExchangeProvider {

    var ratesMap = mapOf(
        "USD" to mapOf(
            "EUR" to 0.91,
            "AUD" to 1.49,
            "CAD" to -3.0,
        ),
        "EUR" to mapOf(
            "EUR" to 1.08,
            "AUD" to 1.62,
            "CAD" to 1.43,
        )
    )

    override suspend fun fetchExchangeRates(baseCurrency: CurrencyCode): RemoteExchangeProvider.Result {
        return RemoteExchangeProvider.Result(
            ratesMap = ratesMap[baseCurrency] as ExchangeRatesMap,
            provider = ExchangeProvider.Fawazahmed0
        )
    }
}