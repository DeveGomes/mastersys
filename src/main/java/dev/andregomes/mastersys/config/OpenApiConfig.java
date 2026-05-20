package dev.andregomes.mastersys.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Academia API")
                        .description(
        """
                API para gerenciamento de academia, incluindo:
                
                - Cadastro de alunos
                - Matrículas de alunos
                - Controle Financeiro
                - Relatórios gerenciais
                """
    )
                                .version("v1.0.0")
        );

    }


}
