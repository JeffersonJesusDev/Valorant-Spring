package br.com.JDev.ValorantApi;

import br.com.JDev.ValorantApi.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ValorantApiApplication {

    @Autowired
    private Principal principal;

    public static void main(String[] args) {
        SpringApplication.run(ValorantApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> principal.executarPrograma();
    }
}