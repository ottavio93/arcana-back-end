

  package com.example.arcana.config;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
  import org.springframework.security.authentication.AuthenticationManager;
  import org.springframework.security.config.BeanIds;
  import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
  import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
  import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
  import org.springframework.security.core.userdetails.UserDetailsService;
  import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
  import org.springframework.security.crypto.password.PasswordEncoder;
  import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.arcana.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;
  
  @EnableWebSecurity
  @AllArgsConstructor
  public class SecurityConfig extends WebSecurityConfigurerAdapter {
	  
	    private final UserDetailsService userDetailsService;
	    private final JwtAuthenticationFilter jwtAuthenticationFilter;
	    
	    @Bean(BeanIds.AUTHENTICATION_MANAGER)
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	    
	    
	    
      @Override 
      protected void configure(HttpSecurity httpSecurity) throws Exception {
    	  httpSecurity.csrf().disable()
          .authorizeRequests()
          .antMatchers("/api/auth/**")
          .permitAll()
          .antMatchers(HttpMethod.GET, "/api/subarcana")
          .permitAll()
          .antMatchers(HttpMethod.GET, "/api/posts/")
          .permitAll()
          .antMatchers(HttpMethod.GET, "/api/posts/**")
          .permitAll()
          .antMatchers(HttpMethod.GET, "/data/**")
          .permitAll()
          .antMatchers(HttpMethod.POST, "/api/auth/**")
          .permitAll()
          .antMatchers(HttpMethod.POST, "/api/auth/createPost")
          .permitAll()
          .antMatchers(HttpMethod.POST, "/data/create")
          .permitAll()
          .anyRequest()
          .authenticated();
    	  httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                  UsernamePasswordAuthenticationFilter.class);
    	
      }
      
      @Autowired
      public void configureGlobal (AuthenticationManagerBuilder  authenticationManagerBuilder) throws Exception {
    	  authenticationManagerBuilder.userDetailsService(userDetailsService)
    	  .passwordEncoder(passwordEncoder());
    	  
      }

      
      
      
      
  @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();
  }
  
  }
 