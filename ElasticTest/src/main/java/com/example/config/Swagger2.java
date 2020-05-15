package com.example.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.RestController;
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
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger2",value = {"enable"},havingValue = "true")
public class Swagger2 {

	  public static final String SWAGGER2 = "swagger";
    /**
     * 接口地址
     */
    public static final String url = "com.example.controller";
   
//    private List<Parameter> setHeaderToken() {
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        tokenPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
//
//        ParameterBuilder tokenPar2 = new ParameterBuilder();
//        tokenPar2.name("SIGN").description("1.pc,2.wap,3.ios,4.Android ").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar2.build());
//
//        ParameterBuilder tokenPar3 = new ParameterBuilder();
//        tokenPar3.name("plant").description("设备编号14位，Base64加密").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar3.build());
//
//        ParameterBuilder tokenPar4 = new ParameterBuilder();
//        tokenPar4.name("diffuse").description("数据签名").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar4.build());
//
//
//        ParameterBuilder tokenPar5 = new ParameterBuilder();
//        tokenPar5.name("version").description("app当前版本号").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar5.build());
//        return pars;
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("es swagger测试")
                .description("基本操作接口测试")
                .version("1.0")
                .build();
    }


//    @Bean
//    public Docket adminApi() {
//        String title = "Admin管理";
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName(title)
//                .select()
//                .paths(PathSelectors.regex("/admin/.*"))
//                .paths(PathSelectors.any())
//                .build().globalOperationParameters(setHeaderToken())
//                .apiInfo(apiInfo(title));
//    }


    @Bean
    public Docket webApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName(title)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .paths(PathSelectors.regex("/web/.*"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(new ArrayList<Parameter>());
    }



//    @Bean
//    public Docket createRestApi() {
////    	  String title = "ALL";
//        return new Docket(DocumentationType.SWAGGER_2)
////        		 .groupName(title)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(url))
//                .paths(PathSelectors.any())
//                .build().globalOperationParameters(new ArrayList<Parameter>())
//                .apiInfo(apiInfo());
//    }

}