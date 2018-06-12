package com.infosystem.springmvc.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JavaUtilDateToStringConverter implements Converter<Date, String> {

    @Override
    public String convert(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
    }
}
