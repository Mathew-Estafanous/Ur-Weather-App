package com.urweather.app.helpers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import net.iakovlev.timeshape.TimeZoneEngine;

public class TimezoneConvertorHelper {

    private final static TimeZoneEngine engine = TimeZoneEngine.initialize();

    public final static ZonedDateTime convertDateToLocalTimezone(double lat, double lon, Date originalDate) {
        Optional<ZoneId> zoneId = engine.query(lat, lon);
        Instant pointInTime = originalDate.toInstant();

        return zoneId.map(zone -> pointInTime.atZone(zone))
                .orElse(null);
    }
 }