package edu.miu.badge;

import edu.miu.badge.domains.PlanType;
import edu.miu.badge.domains.Role;
import edu.miu.badge.enumeration.PlanTypeEnum;
import edu.miu.badge.enumeration.RoleType;
import edu.miu.badge.repositories.PlanTypeRepository;
import edu.miu.badge.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner{

    @Autowired
    private PlanTypeRepository planTypeRepository;
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Server is running..." );


        planTypeRepository.save(new PlanType(1, PlanTypeEnum.LIMITED));
        planTypeRepository.save(new PlanType(2, PlanTypeEnum.UNLIMITED));
        planTypeRepository.save(new PlanType(3, PlanTypeEnum.CHECKER));

    }
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
