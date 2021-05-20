package com.sciamus.contractanalyzer.conf;

import io.vavr.collection.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import static java.util.Arrays.asList;

@Configuration
public class VavrCustomConverter {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(asList(new VavrListToListConverter(), new ListToVavrListConverter()));
    }

    @WritingConverter
    private static class  VavrListToListConverter<T> implements Converter<List<T>, java.util.List<T>> {
        @Override
        public java.util.List<T> convert(List<T> list) {
            return list.toJavaList();
        }
    }
    @ReadingConverter
    private static class ListToVavrListConverter<T> implements Converter<java.util.List<T>, List<T>> {
        @Override
        public List<T> convert(java.util.List<T> list) {
            return List.ofAll(list);
        }
    }

}