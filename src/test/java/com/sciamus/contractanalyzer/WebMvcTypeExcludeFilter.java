package com.sciamus.contractanalyzer;

import org.springframework.core.annotation.AnnotatedElementUtils;

public class WebMvcTypeExcludeFilter {

    private final DataMongoWebMvcTest annotation;

    WebMvcTypeExcludeFilter(Class<?> testClass) {
        this.annotation = AnnotatedElementUtils.getMergedAnnotation(testClass,
                DataMongoWebMvcTest.class);
    }
}
