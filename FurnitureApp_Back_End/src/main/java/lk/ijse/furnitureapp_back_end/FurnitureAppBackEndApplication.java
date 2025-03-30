package lk.ijse.furnitureapp_back_end;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FurnitureAppBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurnitureAppBackEndApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
