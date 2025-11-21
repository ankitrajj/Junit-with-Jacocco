package in.ankit.service.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import in.ankit.entities.UserEntity;
import in.ankit.props.AppProperties;
import in.ankit.repositories.CityRepository;
import in.ankit.repositories.CountryRepository;
import in.ankit.repositories.StateRepository;
import in.ankit.repositories.UserRepository;
import in.ankit.service.RegistrationServiceImpl;
import in.ankit.util.EmailUtils;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {

	@MockBean
	private UserRepository userRepo;

	@MockBean
	private CountryRepository countryRepo;

	@MockBean
	private StateRepository stateRepo;

	@MockBean
	private CityRepository cityRepo;

	@MockBean
	private EmailUtils emailUtils;

	@MockBean
	private AppProperties appProps;

	@InjectMocks
	private RegistrationServiceImpl service;

	@Test
	public void uniqueEmailTest1() {
		when(userRepo.findByUserEmail("ankitrajj95@gmail.com")).thenReturn(new UserEntity());
		boolean uniqueEmail = service.uniqueEmail("ankitrajj95@gmail.com");
		assertFalse(uniqueEmail);
	}
	
	@Test
	public void uniqueEmailTest2() {
		when(userRepo.findByUserEmail("ankitrajj95@gmail.com")).thenReturn(null);
		boolean uniqueEmail = service.uniqueEmail("ankitrajj95@gmail.com");
		assertTrue(uniqueEmail);
	}

}
