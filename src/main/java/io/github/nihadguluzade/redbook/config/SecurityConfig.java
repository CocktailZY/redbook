package io.github.nihadguluzade.redbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                //.anyRequest().authenticated()
                .and()
                .formLogin() // enable form based login
                .loginPage("/oauth_login").defaultSuccessUrl("/formLoginSuccess")
                .and()
                .logout() // enable logout
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login() // enable oauth2
                .clientRegistrationRepository(clientRegistrationRepository)
                .loginPage("/oauth_login").defaultSuccessUrl("/oauth2LoginSuccess")
                .tokenEndpoint().accessTokenResponseClient(new AccessTokenResponseClient(restOperations()))
                .and()
                .userInfoEndpoint().userService(new RestOAuth2UserService(restOperations()));
    }

    @Bean
    public static RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, not banned from redbook.users where username=?")
                .authoritiesByUsernameQuery("select u.username, pr.privilege_name " +
                                            "from redbook.users u " +
                                            "inner join redbook.privilegemap pmap " +
                                            "on u.user_id=pmap.user_id " +
                                            "inner join redbook.privilege pr " +
                                            "on pmap.privilege_id=pr.privilege_id " +
                                            "where username=?");
    }
}
