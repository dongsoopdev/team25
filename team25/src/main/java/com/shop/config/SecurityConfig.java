package com.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    //스프링 datasource 가져와서 알아서 내부에서 인증처리
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder()) //알아서 비번 암호화
                .usersByUsernameQuery ("select userId,pwd,enabled "  //인증처리
                        + "from user "
                        + "where userId = ?")
                .authoritiesByUsernameQuery("select u.userId, r.roleName " //권한처리
                        + "from userRole ur inner join user u on ur.userId =u.id "
                        +  "inner join role r on ur.roleId = r.id "
                        + "where u.userName = ?");
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 권한에 따라 허용하는 url 설정
        // .antMatchers는 /login, /join 페이지는 모두 허용, 다른 페이지는 인증된 사용자만 허용
        // 자원의 경로는 mvcMatchers 로
        http
                .authorizeRequests()
                .antMatchers("/","/**").permitAll()
                .antMatchers("/user/login", "/user/join", "/user/joinpro","/user/idCheck", "/user/emailCheck").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/teacher/**").hasAnyRole("ADMIN","TEACHER")
                .antMatchers("/user/update", "/user/out","/user/updatePro").hasAnyRole("USER","ADMIN","TEACHER")
                .mvcMatchers("/","/templates/**","/ex/**","/resource/**","/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated();

        // login 설정
        http
                .formLogin()
                .loginPage("/user/login")    // GET 요청 (login form을 보여줌)
                .loginProcessingUrl("/user/auth")    // POST 요청 (login 창에 입력한 데이터를 처리)
                .usernameParameter("userId")	// login에 필요한 id 값을 email로 설정 (default는 username)
                .passwordParameter("pwd")	// login에 필요한 password 값을 password(default)로 설정
                .defaultSuccessUrl("/");	// login에 성공하면 /로 redirect

        // logout 설정
        http
                .logout()
                .logoutUrl("/user/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/");	// logout에 성공하면 /로 redirect

        //cors 방지 해제
        http.csrf().disable().cors().disable();

        //중복 로그인 방지
        http.sessionManagement()
                .sessionFixation().changeSessionId()
                .maximumSessions(1)
                .expiredSessionStrategy(new CustomSessionExpiredStrategy())
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry());

        return http.build();

    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
