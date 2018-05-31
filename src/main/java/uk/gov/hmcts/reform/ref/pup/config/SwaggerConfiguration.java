package uk.gov.hmcts.reform.ref.pup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2
@ComponentScan("uk.gov.hmcts.reform.ref.pup.controller")
public class SwaggerConfiguration {

    private final static String apiVersion = "0.0.1";

    private static final String MODEL_REF_TYPE = "string";
    private static final String PARAMETER_TYPE = "header";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/pup(.*)"))
                .build()
//                .globalOperationParameters(
//                    Stream.of(new ParameterBuilder()
//                            .name("ServiceAuthorization")
//                            .description("Service Auth (S2S). Use it when accessing the API on App Tier level.")
//                            .modelRef(new ModelRef(MODEL_REF_TYPE))
//                            .parameterType(PARAMETER_TYPE)
//                            .required(true)
//                            .build()).collect(Collectors.toList()))
//                .globalOperationParameters(
//                    Stream.of(new ParameterBuilder()
//                        .name("Authorization")
//                        .description("JWT of the currently authenticated user. If provided will be used to used for authorisation.")
//                        .modelRef(new ModelRef(MODEL_REF_TYPE))
//                        .parameterType(PARAMETER_TYPE)
//                        .required(true)
//                        .build()).collect(Collectors.toList()))
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Professional User Profile Reference Data API")
                .description("Documented API for the tactical professional user profile reference data solution."
                    + "To use the API calls generate an Authorization JWT Tokens (user and service) which is required in the header.")
                .version(apiVersion)
                .build();
    }

}
