package com.ivy.core.domain.action.exchange

import com.ivy.core.persistence.dao.exchange.ExchangeRateOverrideDao
import com.ivy.core.persistence.entity.exchange.ExchangeRateOverrideEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class ExchangeRateOverrideDaoFake: ExchangeRateOverrideDao {

    private val rates = MutableStateFlow<List<ExchangeRateOverrideEntity>>(emptyList())

    override suspend fun save(values: List<ExchangeRateOverrideEntity>) {
        rates.value = values
    }

    override suspend fun findAllBlocking(): List<ExchangeRateOverrideEntity> {
        return rates.value
    }

    override fun findAllByBaseCurrency(baseCurrency: String): Flow<List<ExchangeRateOverrideEntity>> {
        return rates.map { overrides ->
            overrides.filter { it.baseCurrency.uppercase() == baseCurrency.uppercase() }
        }
    }

    override suspend fun deleteByBaseCurrencyAndCurrency(baseCurrency: String, currency: String) {
        rates.update {
            it.filter { entity ->
                entity.baseCurrency != baseCurrency && entity.currency != currency
            }
        }
    }
}