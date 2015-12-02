package org.figis.search.service.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * For some stupid reason, the default FactsheetWebServiceClient counts also as valid, therefore we need to point to the
 * produced one explicitly.
 * 
 * 
 * 
 * @author Erik van Ingen
 *
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
@Inherited
public @interface FigisSearchServiceQualifier {
}