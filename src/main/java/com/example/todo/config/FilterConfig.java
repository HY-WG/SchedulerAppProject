package com.example.todo.config;

import com.example.todo.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 웹 애플리케이션에서 서블릿 필터를 등록하고 설정하는 클래스
 * 스프링 컨텍스트에 필터 등록 Bean 생성
 * LoginFilter를 모든 요청에 대해 동작하도록 설정
 */

@Configuration // 이 클래스가 스프링 설정(설정 정보)을 담고 있는 클래스임을 알려주는 역할을 하는 어노테이션.
// 이 어노테이션이 있어야, “이 클래스 안에 @Bean 메서드가 있으니 실행해서 빈을 만들어야겠다” 라고 인식함.
// 만약 이 애너테이션이 없으면, 단순한 일반 클래스 취급되어 @Bean 메서드가 무시될 수 있음.
public class FilterConfig {


    /**
     * Spring 컨테이너가 필터를 관리하도록 하기 위해서 Bean 으로 등록.
     * @Bean으로 등록된 필터는 스프링 컨테이너의 빈이 되어, 필요하면 의존성 주입도 받고, 라이프사이클도 스프링이 관리.
     * FilterRegistrationBean: 서블릿 필터를 등록하고 적용할 URL 패턴, 순서 등을 설정하는 객체
     *  - 특정 Filter를 등록하고, URL 패턴, 순서 등 세부 설정 가능
     *
     *  - LoginFilter : 인스턴스를 필터로 등록
     *  - 모든 URL("/*")에 대해 필터 적용
     *  - 필터 우선순위를 1로 설정 (숫자가 낮을수록 우선순위 높음)
     *
     * @return FilterRegistrationBean<Filter> 등록된 필터 Bean
     */


    @Bean
    public FilterRegistrationBean<Filter> loginFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        // 직접 구현한 LoginFilter를 필터로 지정
        bean.setFilter(new LoginFilter());
        // 모든 요청 경로에 대해 필터가 실행되도록 설정
        bean.addUrlPatterns("/*");
        // 필터 체인 내 실행 우선순위 지정 (1번으로 가장 먼저 실행)
        bean.setOrder(1);
        return bean;
    }
}