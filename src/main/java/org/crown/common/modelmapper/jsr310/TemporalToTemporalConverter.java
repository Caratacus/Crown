package org.crown.common.modelmapper.jsr310;

import java.time.temporal.Temporal;

import org.modelmapper.internal.Errors;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

/**
 * Converts  {@link Temporal} to {@link Temporal}
 *
 * @author Chun Han Hsiao
 */
public class TemporalToTemporalConverter implements ConditionalConverter<Temporal, Temporal> {

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return Temporal.class.isAssignableFrom(sourceType)
                && Temporal.class.isAssignableFrom(destinationType)
                ? MatchResult.FULL : MatchResult.NONE;
    }

    @Override
    public Temporal convert(MappingContext<Temporal, Temporal> mappingContext) {
        if (mappingContext.getSource() == null)
            return null;
        else if (mappingContext.getSourceType().equals(mappingContext.getDestinationType()))
            return mappingContext.getSource();
        else
            throw new Errors().addMessage("Unsupported mapping types[%s->%s]",
                    mappingContext.getSourceType().getName(), mappingContext.getDestinationType())
                    .toMappingException();
    }
}
