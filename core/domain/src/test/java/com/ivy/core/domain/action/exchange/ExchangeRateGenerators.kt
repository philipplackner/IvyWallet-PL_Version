package com.ivy.core.domain.action.exchange

import com.ivy.core.persistence.entity.exchange.ExchangeRateEntity
import com.ivy.core.persistence.entity.exchange.ExchangeRateOverrideEntity
import com.ivy.data.SyncState
import java.time.Instant

fun exchangeRateEntity(
    currency: String,
    rate: Double
): ExchangeRateEntity {
    return ExchangeRateEntity(
        baseCurrency = "EUR",
        currency = currency,
        rate = rate,
        provider = null
    )
}

fun exchangeRateOverrideEntity(
    currency: String,
    rate: Double
): ExchangeRateOverrideEntity {
    return ExchangeRateOverrideEntity(
        baseCurrency = "EUR",
        currency = currency,
        rate = rate,
        sync = SyncState.Syncing,
        lastUpdated = Instant.now()
    )
}