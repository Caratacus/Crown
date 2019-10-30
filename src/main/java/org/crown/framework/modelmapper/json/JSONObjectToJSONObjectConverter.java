package org.crown.framework.modelmapper.json;

import org.modelmapper.internal.Errors;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

import com.alibaba.fastjson.JSONObject;

/**
 * Converts  {@link JSONObject} to {@link JSONObject}
 *
 * @author Caratacus
 */
public class JSONObjectToJSONObjectConverter implements ConditionalConverter<JSONObject, JSONObject> {

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return JSONObject.class.isAssignableFrom(sourceType)
                && JSONObject.class.isAssignableFrom(destinationType)
                ? MatchResult.FULL : MatchResult.NONE;
    }

    @Override
    public JSONObject convert(MappingContext<JSONObject, JSONObject> mappingContext) {
        if (mappingContext.getSource() == null) {
            return null;

        } else if (mappingContext.getSourceType().equals(mappingContext.getDestinationType())) {
            JSONObject source = mappingContext.getSource();
            return (JSONObject) source.clone();
        } else {
            throw new Errors().addMessage("Unsupported mapping types[%s->%s]",
                    mappingContext.getSourceType().getName(), mappingContext.getDestinationType())
                    .toMappingException();
        }

    }
}
