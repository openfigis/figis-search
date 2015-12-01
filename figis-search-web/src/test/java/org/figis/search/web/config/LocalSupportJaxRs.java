package org.figis.search.web.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.internal.jaxrs.JaxRsExtension;

@AdditionalClasses({ JaxRsExtension.class, LocalJaxRsProducers.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalSupportJaxRs {

}