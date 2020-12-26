package com.ymw.love.system.config;

import java.util.ArrayList;
import java.util.List;

import io.swagger.models.Contact;
import org.apache.http.Consts;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConfigurationProperties(prefix = Swagger2.SWAGGER2)
@ComponentScan(Swagger2.url)
public class Swagger2 {

	  public static final String SWAGGER2 = "swagger";
    /**
     * 接口地址
     */
    public static final String url = "com.ymw.love.system.controller";
   
    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        
        ParameterBuilder tokenPar2 = new ParameterBuilder();
        tokenPar2.name("SIGN").description("1.pc,2.wap,3.ios,4.Android ").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar2.build());
        return pars;
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("类风湿")
                .description("类风湿")
                /*   .license("")
                   .licenseUrl("")
                   .contact(new Contact("ebuyhouse", "", "ebuyhouse"))*/
                .version("1.0")
                .build();

        return apiInfo;
    }

    private ApiInfo apiInfo2(String title) {
        return new ApiInfoBuilder().title(title)
                .description("[" + title + "]" + "是一个 REST服务平台")// 详细描述
                .version("1.0").build();
    }
//
//    @Bean
//    public Docket adminApi() {
//        String title = "Admin管理";
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName(title)
//                .select()
//                .paths(PathSelectors.regex("/admin/.*"))
//                .paths(PathSelectors.any())
//                .build().globalOperationParameters(setHeaderToken())
//                .apiInfo(apiInfo1(title));
//    }
//
//
//    @Bean
//    public Docket webApi() {
//        String title = "WEB";
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName(title)
//                .select()
//                .paths(PathSelectors.regex("/web/.*"))
//                .paths(PathSelectors.any())
//                .build().globalOperationParameters(setHeaderToken())
//                .apiInfo(apiInfo1(title));
//    }

    @Bean
    public Docket myTest() {
        String title = "demo";
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(title)
                .select()
                .paths(PathSelectors.regex("/test/.*"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo2(title));
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(url))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo());
    }

}
