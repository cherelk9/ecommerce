package cm.backend.ecommerce.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cm.backend.ecommerce.utils.OpenAiUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI().info(
                new Info().title("gestion de quincaillerie API")
                        .version("1.0")
                        .description(OpenAiUtils.DESCRIPTION));
    }

}
