/**
 * 
 */
package com.cmb.model.neft;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.AttributeConverter;

/**
 * @author waliu.faleye
 *
 */
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	  public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
	    return Optional.ofNullable(localDateTime)
	        .map(Timestamp::valueOf)
	        .orElse(null);
	  }
	  @Override
	  public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
	    return Optional.ofNullable(timestamp)
	        .map(Timestamp::toLocalDateTime)
	        .orElse(null);
	  }
}
