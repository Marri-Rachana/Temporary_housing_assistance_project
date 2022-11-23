package com.housebooking.app.test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.housebooking.app.dao.HomeRepo;
import com.housebooking.app.model.UserModel;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class HomeControllerTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HomeRepo homeRepo;

    @Test
    public void shouldStoreUser() {

        UserModel user = new UserModel();

        user.setFirstname("Yasoda");
        user.setLastname("Krishna");
        user.setEmail("yasoda1206@gmail.com");
        user.setUsername("ykrishna");
        user.setPassword("user1234");
        user.setUsertype("student");
        user.setSecurityQuestion("q1");
        user.setSecurityAnswer("a1");

        List<UserModel> userdata = homeRepo.findAll();

        assertThat(userdata).isEmpty();

        homeRepo.save(user);

        List<UserModel> users = homeRepo.findAll();

        assertThat(users).hasSize(1);

    }
}
