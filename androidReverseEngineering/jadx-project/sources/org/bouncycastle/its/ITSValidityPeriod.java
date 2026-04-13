package org.bouncycastle.its;

import java.util.Date;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.oer.its.Duration;
import org.bouncycastle.oer.its.ValidityPeriod;

/* loaded from: classes.dex */
public class ITSValidityPeriod {
    private final int duration;
    private final long startDate;
    private final Unit timeUnit;

    public static class Builder {
        private final long startDate;

        public Builder(Date date) {
            this.startDate = date.getTime();
        }

        public ITSValidityPeriod plusSixtyHours(int i2) {
            return new ITSValidityPeriod(this.startDate, i2, Unit.sixtyHours);
        }

        public ITSValidityPeriod plusYears(int i2) {
            return new ITSValidityPeriod(this.startDate, i2, Unit.years);
        }
    }

    public enum Unit {
        microseconds(0),
        milliseconds(1),
        seconds(2),
        minutes(3),
        hours(4),
        sixtyHours(5),
        years(6);

        private final int unitTag;

        Unit(int i2) {
            this.unitTag = i2;
        }
    }

    public ITSValidityPeriod(long j2, int i2, Unit unit) {
        this.startDate = j2;
        this.duration = i2;
        this.timeUnit = unit;
    }

    public static Builder from(Date date) {
        return new Builder(date);
    }

    public Date getStartDate() {
        return new Date(this.startDate);
    }

    public ValidityPeriod toASN1Structure() {
        return ValidityPeriod.builder().setTime32(new ASN1Integer(this.startDate / 1000)).setDuration(new Duration(this.duration, this.timeUnit.unitTag)).createValidityPeriod();
    }

    public ITSValidityPeriod(ValidityPeriod validityPeriod) {
        this.startDate = validityPeriod.getTime32().longValueExact();
        Duration duration = validityPeriod.getDuration();
        this.duration = duration.getValue();
        this.timeUnit = Unit.values()[duration.getTag()];
    }
}
