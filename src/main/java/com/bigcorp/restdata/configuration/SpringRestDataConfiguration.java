package com.bigcorp.restdata.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class SpringRestDataConfiguration {


	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {
		return new RepositoryRestConfigurer() {
			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

				// L'URL de base pour accéder aux ressources REST
				// Pour les webservices, mettre une version est toujours
				// une bonne chose
				config.setBasePath("/api/v1");

				// Restreint les repositories exposés en tant que ressources REST
				// à ceux annotés avec @RepositoryRestResource
				config.setRepositoryDetectionStrategy(
						RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
			}
		};
	}

}
