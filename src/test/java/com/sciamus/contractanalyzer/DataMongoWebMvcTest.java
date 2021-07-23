package com.sciamus.contractanalyzer;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.env.Environment;
import org.springframework.test.context.BootstrapWith;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@OverrideAutoConfiguration(enabled = false)
@AutoConfigureDataMongo
//@TypeExcludeFilters({WebMvcTypeExcludeFilter.class, DataMongoTypeExcludeFilter.class})
@BootstrapWith(WebMvcTestContextBootstrapper.class)
@AutoConfigureCache
@ImportAutoConfiguration
public @interface DataMongoWebMvcTest {

    /**
     * Properties in form {@literal key=value} that should be added to the Spring
     * {@link Environment} before the test runs.
     * @return the properties to add
     * @since 2.1.0
     */
    String[] properties() default {};

    /**
     * Specifies the controllers to test. This is an alias of {@link #controllers()} which
     * can be used for brevity if no other attributes are defined. See
     * {@link #controllers()} for details.
     * @see #controllers()
     * @return the controllers to test
     */
    @AliasFor("controllers")
    Class<?>[] value() default {};

    /**
     * Specifies the controllers to test. May be left blank if all {@code @Controller}
     * beans should be added to the application context.
     * @see #value()
     * @return the controllers to test
     */
    @AliasFor("value")
    Class<?>[] controllers() default {};

    /**
     * Determines if default filtering should be used with
     * {@link SpringBootApplication @SpringBootApplication}. By default only
     * {@code @Controller} (when no explicit {@link #controllers() controllers} are
     * defined), {@code @ControllerAdvice} and {@code WebMvcConfigurer} beans are
     * included.
     * @see #includeFilters()
     * @see #excludeFilters()
     * @return if default filters should be used
     */
    boolean useDefaultFilters() default true;

    /**
     * A set of include filters which can be used to add otherwise filtered beans to the
     * application context.
     * @return include filters to apply
     */
    ComponentScan.Filter[] includeFilters() default {};

    /**
     * A set of exclude filters which can be used to filter beans that would otherwise be
     * added to the application context.
     * @return exclude filters to apply
     */
    ComponentScan.Filter[] excludeFilters() default {};

    /**
     * Auto-configuration exclusions that should be applied for this test.
     * @return auto-configuration exclusions to apply
     */
    @AliasFor(annotation = ImportAutoConfiguration.class, attribute = "exclude")
    Class<?>[] excludeAutoConfiguration() default {};

}

