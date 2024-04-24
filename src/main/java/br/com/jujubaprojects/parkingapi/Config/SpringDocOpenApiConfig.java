package br.com.jujubaprojects.parkingapi.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    // Configuração do Bean para gerar a documentação OpenAPI
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()  // Instancia um objeto OpenAPI para configurar informações da documentação.
        .components(new Components().addSecuritySchemes("security", securityScheme()))
        .info(new Info()  // Configura as informações gerais da API.
            .title("Parking - api ")  // Título da API.
            .version("v1")  // Versão da API.
            .description("Some description about your API")  // Descrição breve da API.
            .termsOfService("https://jujubaprojects.com.br/meus-cursos")  // Termos de serviço da API.
            .license(new License()
              .name("Apache 2.0")
              .url("https://www.apache.org/licenses/LICENSE-2.0"))
            .contact(new Contact()  // Informações de contato.
                .name("Junior Stranner")  // Nome do contato.
                .email("Junior@spring-park.com")));  // Endereço de e-mail do contato.

}

private SecurityScheme securityScheme() {
    return new SecurityScheme()
            .description("Insira um bearer token valido para prosseguir")
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER)
            .scheme("bearer")
            .bearerFormat("JWT")
            .name("security");
   }
}
