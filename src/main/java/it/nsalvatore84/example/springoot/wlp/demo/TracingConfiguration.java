package ch.corner.example.springoot.wlp.demo;

import brave.Tracing;
import brave.context.slf4j.MDCCurrentTraceContext;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.spring.webmvc.SpanCustomizingAsyncHandlerInterceptor;
import brave.spring.webmvc.SpanCustomizingHandlerInterceptor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

@Configuration
// Importing these classes is effectively the same as declaring bean methods
@Import({
        SpanCustomizingHandlerInterceptor.class
})
public class TracingConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    SpanCustomizingHandlerInterceptor serverInterceptor;

    /** adds tracing to the application-defined web controller */
    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serverInterceptor);
    }

    /** Controls aspects of tracing such as the name that shows up in the UI */
    @Bean
    Tracing tracing() {
        return Tracing.newBuilder()
                .localServiceName("app")
                .propagationFactory(ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, "user-name"))
                .currentTraceContext(MDCCurrentTraceContext.create()) // puts trace IDs into logs
                .spanReporter(new Reporter<Span>() {
                    @Override
                    public void report(Span span) {
                        LoggerFactory.getLogger(getClass()).info(span.toString());
                    }
                }).build();
    }
}
