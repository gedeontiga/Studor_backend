package com.studor.orientation_student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.studor.orientation_student.entities.Role;
import com.studor.orientation_student.entities.RoleType;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@EnableScheduling
@SpringBootApplication
public class OrientationStudentApplication implements CommandLineRunner {
	
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrientationStudentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User admin = User.builder()
						.nom("admin")
						.email("gedeon.tiga@studor.com")
						.motDePasse(this.passwordEncoder.encode("admin"))
						.actif(true)
						.role(
							Role.builder()
								.type(RoleType.ADMIN)
								.build()
						)
						.build();
		if(!userRepository.findByEmail(admin.getEmail()).isPresent())
			userRepository.save(admin);

		User manager = User.builder()
							.nom("manager")
							.email("manager@studor.com")
							.motDePasse(this.passwordEncoder.encode("manager"))
							.actif(true)
							.role(
									Role.builder()
											.type(RoleType.MANAGER)
											.build())
							.build();
		if(!userRepository.findByEmail(manager.getEmail()).isPresent())
			userRepository.save(manager);
	}
}
