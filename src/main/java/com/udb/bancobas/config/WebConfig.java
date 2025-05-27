package com.udb.bancobas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired private LoginInterceptor loginInterceptor;
    @Autowired private CajeroInterceptor cajeroInterceptor;
    @Autowired private ClienteInterceptor clienteInterceptor;
    @Autowired private DependienteInterceptor dependienteInterceptor;
    @Autowired private GerenteSucursalInterceptor gerenteSucursalInterceptor;
    @Autowired private GerenteGeneralInterceptor gerenteGeneralInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/cliente/**", "/cajero/**", "/dependiente/**", "/gerente-sucursal/**", "/gerent-general/**")
                .excludePathPatterns("/auth/**", "/css/**", "/js/**", "/images/**");

        registry.addInterceptor(clienteInterceptor)
                .addPathPatterns("/cliente/**");

        registry.addInterceptor(cajeroInterceptor)
                .addPathPatterns("/cajero/**");

        registry.addInterceptor(dependienteInterceptor)
                .addPathPatterns("/dependiente/**");

        registry.addInterceptor(gerenteSucursalInterceptor)
                .addPathPatterns("/gerente-sucursal/**");

        registry.addInterceptor(gerenteGeneralInterceptor)
                .addPathPatterns("/gerente-general/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/login").setViewName("login");
        registry.addRedirectViewController("/", "/auth/login");
    }
}
