package com.jb.Couponsystem.data;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;

@javax.persistence.Converter
public class Converter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
