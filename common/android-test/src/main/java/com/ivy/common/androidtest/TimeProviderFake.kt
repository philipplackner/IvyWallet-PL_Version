package com.ivy.common.androidtest

import com.ivy.common.time.provider.TimeProvider
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeProviderFake @Inject constructor(): TimeProvider {

    var timeNow = LocalDateTime.now()
    var dateNow = LocalDate.now()
    var zoneId = ZoneId.systemDefault()

    override fun timeNow(): LocalDateTime {
        return timeNow
    }

    override fun dateNow(): LocalDate {
        return dateNow
    }

    override fun zoneId(): ZoneId {
        return zoneId
    }
}