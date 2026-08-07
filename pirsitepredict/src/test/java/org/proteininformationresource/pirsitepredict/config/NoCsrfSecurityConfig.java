package org.proteininformationresource.pirsitepredict.config;

import org.springframework.context.annotation.Configuration;

/**
 * Test configuration that reuses the main {@link SecurityConfig} (which already
 * disables CSRF) and allows the {@link SecurityConfig#securityFilterChain} bean
 * to be picked up in the test context.
 */
@Configuration
public class NoCsrfSecurityConfig extends SecurityConfig {
}
