package org.crown.common.modelmapper.jsr310;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

import org.modelmapper.Converter;
import org.modelmapper.internal.Errors;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

/**
 * Converts  {@link Temporal} to {@link Object}
 *
 * @author Chun Han Hsiao
 */
public class FromTemporalConverter implements ConditionalConverter<Temporal, Object> {

    private Jsr310ModuleConfig config;
    private final LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
    private final LocalDateConverter localDateConverter = new LocalDateConverter();
    private final InstantConverter instantConverter = new InstantConverter();

    public FromTemporalConverter(Jsr310ModuleConfig config) {
        this.config = config;
    }

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return Temporal.class.isAssignableFrom(sourceType)
                ? MatchResult.FULL : MatchResult.NONE;
    }

    @Override
    public Object convert(MappingContext<Temporal, Object> mappingContext) {
        Class<?> sourceType = mappingContext.getSourceType();
        if (LocalDateTime.class.equals(sourceType))
            return localDateTimeConverter.convert(mappingContext);
        else if (LocalDate.class.equals(sourceType))
            return localDateConverter.convert(mappingContext);
        else if (Instant.class.equals(sourceType))
            return instantConverter.convert(mappingContext);
        else
            throw new Errors().addMessage("Unsupported mapping types[%s->%s]",
                    LocalDateTime.class.getName(), mappingContext.getDestinationType().getName())
                    .toMappingException();
    }

    private class LocalDateTimeConverter implements Converter<Temporal, Object> {

        @Override
        public Object convert(MappingContext<Temporal, Object> mappingContext) {
            LocalDateTime source = (LocalDateTime) mappingContext.getSource();
            return convertLocalDateTime(source, mappingContext);
        }
    }

    private class LocalDateConverter implements Converter<Temporal, Object> {

        @Override
        public Object convert(MappingContext<Temporal, Object> mappingContext) {
            LocalDate source = (LocalDate) mappingContext.getSource();
            Class<?> destinationType = mappingContext.getDestinationType();
            if (destinationType.equals(String.class))
                return DateTimeFormatter.ofPattern(config.getDatePattern())
                        .format(source);

            LocalDateTime localDateTime = source.atStartOfDay();
            return convertLocalDateTime(localDateTime, mappingContext);
        }
    }

    private class InstantConverter implements Converter<Temporal, Object> {

        @Override
        public Object convert(MappingContext<Temporal, Object> mappingContext) {
            Instant source = (Instant) mappingContext.getSource();
            return convertInstant(source, mappingContext);
        }
    }

    private Object convertLocalDateTime(LocalDateTime source, MappingContext<?, ?> mappingContext) {
        Class<?> destinationType = mappingContext.getDestinationType();
        if (destinationType.equals(String.class))
            return DateTimeFormatter.ofPattern(config.getDateTimePattern())
                    .format(source);

        Instant instant = source.atZone(config.getZoneId()).toInstant();
        return convertInstant(instant, mappingContext);
    }

    private Object convertInstant(Instant source, MappingContext<?, ?> mappingContext) {
        Class<?> destinationType = mappingContext.getDestinationType();
        if (destinationType.equals(String.class))
            return DateTimeFormatter.ofPattern(config.getDateTimePattern())
                    .withZone(config.getZoneId())
                    .format(source);
        else if (Date.class.isAssignableFrom(destinationType))
            return new Date(epochMilliOf(source));
        else if (Calendar.class.isAssignableFrom(destinationType))
            return calendarOf(source);
        else if (Long.class.equals(destinationType) || Long.TYPE.equals(destinationType))
            return epochMilliOf(source);
        else if (BigDecimal.class.equals(destinationType))
            return new BigDecimal(epochMilliOf(source));
        else if (BigInteger.class.equals(destinationType))
            return BigInteger.valueOf(epochMilliOf(source));
        else
            throw new Errors().addMessage("Unsupported mapping types[%s->%s]",
                    mappingContext.getSourceType().getName(), destinationType.getName())
                    .toMappingException();
    }

    private long epochMilliOf(Instant instant) {
        return instant.toEpochMilli();
    }

    private Calendar calendarOf(Instant instant) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epochMilliOf(instant));
        return calendar;
    }
}
