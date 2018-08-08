package ch.corner.example.springoot.wlp.demo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

public class SecretSupportPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    public static final String SECRET = "secret:";

    private Environment environment;

    public SecretSupportPropertySourcesPlaceholderConfigurer(Environment environment){
        this.environment = environment;
        LoggerFactory.getLogger(getClass()).info("using SecretSupportPropertySourcesPlaceholderConfigurer to resolve ");
    }


    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
        LoggerFactory.getLogger(getClass()).info(environment.getProperty("secret.prop"));
        propertyResolver.setPlaceholderPrefix(this.placeholderPrefix);
        propertyResolver.setPlaceholderSuffix(this.placeholderSuffix);
        propertyResolver.setValueSeparator(this.valueSeparator);
        StringValueResolver valueResolver = new StringValueResolver() {
            public String resolveStringValue(String strVal) {
                return replaceSecret(strVal, propertyResolver);
            }
        };
        this.doProcessProperties(beanFactoryToProcess, valueResolver);
    }

    private String replaceSecret(String strVal, ConfigurablePropertyResolver propertyResolver) {

        String resolvedValue = nullableValueResolver(strVal, propertyResolver);
        if(resolvedValue != null && resolvedValue.startsWith(SECRET)){
            String replaceFirst = resolvedValue.replaceFirst(SECRET, "").toUpperCase();
            LoggerFactory.getLogger(getClass()).info("resolved is "+replaceFirst);
            return String.valueOf(replaceFirst);
        }

        return resolvedValue;
    }

    private String nullableValueResolver(String strVal, ConfigurablePropertyResolver propertyResolver) {
        String resolved = ignoreUnresolvablePlaceholders ? propertyResolver.resolvePlaceholders(strVal) : propertyResolver.resolveRequiredPlaceholders(strVal);
        if (trimValues) {
            resolved = resolved.trim();
        }

        return resolved.equals(nullValue) ? null : resolved;
    }
}
