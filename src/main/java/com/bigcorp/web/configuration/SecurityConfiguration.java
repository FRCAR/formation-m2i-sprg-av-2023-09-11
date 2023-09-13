package com.bigcorp.web.configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, // Enables @PreAuthorize and @PostAuthorize
		securedEnabled = true, // Enables @Secured
		jsr250Enabled = true // Enables @RolesAllowed (Ensures JSR-250 annotations are enabled)
)
public class SecurityConfiguration {

	/**
	 * Cette clé devrait être externalisée dans un fichier de properties, ou mieux,
	 * il vaut mieux utiliser une clé assymétrique plutôt qu'une clé symétrique
	 * (mais cela se discute).
	 */
	private static final String KEY = "os9eB1DXbGrS637mK5sESWN7mAwmNE57McstI2YV/TE=";

	/**
	 * Renvoie une SecurityFilterChain pour une application Web renvoyant des pages
	 * HTML. L'authentification se fait via une page de login.
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	public SecurityFilterChain securityFilterChainLoginForm(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(authorize -> authorize                                  
						.requestMatchers("/resources/**", "/signup", "/about").permitAll()         
						.requestMatchers("/admin/**").hasRole("ADMIN")                             
						.requestMatchers("/db/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') and hasRole('DBA')"))   
						// .requestMatchers("/db/**").access(AuthorizationManagers.allOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("DBA")))   
						.anyRequest().denyAll()                                                
				)
				// la page de login est accessible via /login
				// et est accessible par tout le monde
				.formLogin(form -> form
						.loginPage("/login")
						.permitAll())
				// La page de logout est aussi accessible
				// par tout le monde
				.logout(logout -> logout.permitAll())
				.build();
	}

	/**
	 * Renvoie une SecurityFilterChain pour une application Web renvoyant des pages
	 * HTML. L'authentification se fait grâce au Header Authorization avec un type
	 * Basic
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	public SecurityFilterChain securityFilterChainBasic(HttpSecurity http) throws Exception {
		return http
				// Active l'authentification basic
				.httpBasic()
				.and()
				// Active les filtres sur les requêtes
				.authorizeHttpRequests(requests -> requests
						// / et /login et /basic-protected peuvent être requêtées par tout le monde
						.requestMatchers("/", "/login", "/basic-protected").permitAll()
						// Toute autre requête ne peut être émise que par une personne
						// authentifiée
						// Ceci inclut les requêtes aux webservices cars par exemple
						.anyRequest().authenticated())
				.build();
	}

	/**
	 * Renvoie une SecurityFilterChain pour une application Web renvoyant des pages
	 * HTML. L'authentification se fait grâce au Header Authorization avec un jeton
	 * JWT
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChainJwt(HttpSecurity http) throws Exception {
		http
				// seules les utilisateurs avec l'authority ROLE_admin peuvent accéder
				// aux URLs /cars/**
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/cars/**").hasAuthority("ROLE_USER")
						.anyRequest().authenticated())
				// Notre serveur sera considéré comme un serveur de ressource
				// utilisant jwt pour authentifier les requêtes
				.oauth2ResourceServer((oauth2) -> oauth2.jwt()
						// configuration du décodeur de JWT (le mot de passe sera utilisé ici)
						.decoder(jwtDecoder())
						// configuration du convertisseur de JWT
						// ou comment transférer les données du JWT
						// à Spring Security
						.jwtAuthenticationConverter(jwtAuthenticationConverter()));
		return http.build();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKey secretKey = new SecretKeySpec(
				KEY.getBytes(), "HS256");
		NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey).build();
		return decoder;
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

		// Les deux lignes qui suivent ne sont pas obligatoires, Spring
		// Security a un authorityPrefix par défaut (SCOPE_) et
		// un authorityClaimName par défaut (scopes).
		// Elles sont ici écrites, car elles représentent bien la configuration
		// à apporter pour que les mondes 'Spring Security' et 'JWT' soient câblés

		// Les authorities dans le contexte Spring seront préfixées par ROLE_
		grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

		// Les authorities du JWT sont des chaînes de caractères, séparées par des
		// espaces,
		// faisant partie du claim 'authorities'
		grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		// La méthode ci-dessous est dépréciée : il n'est pas conseillé
		// de mettre en dur un login et un mot de passe, mais de
		// récupérer un utilisateur d'une base de données, ou d'un
		// référentiel d'utilisateurs (annuaire LDAP)
		@SuppressWarnings("deprecation")
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.authorities("USER")
				.build();
		// Renvoie une implémentation de UserDetailsService
		// qui stocke les utilisateurs en mémoire (ici, un seul utilisateur)
		return new InMemoryUserDetailsManager(user);

	}
}