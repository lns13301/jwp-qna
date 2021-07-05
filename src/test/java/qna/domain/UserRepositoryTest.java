package qna.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository users;

    @Autowired
    private TestEntityManager tem;

    @DisplayName("유저를 생성한다.")
    @Test
    void save() {
        User user = users.save(new User("wilder", "123123", "와일더", "wilder@gmail.com"));

        assertThat(users.findByUserId(user.getUserId()).get()).isEqualTo(user);
    }

    @DisplayName("유저를 조회한다.")
    @Test
    void find() {
        User user = users.save(new User("wilder", "123123", "와일더", "wilder@gmail.com"));
        User user1 = users.save(new User("wilder1", "123123", "와일더1", "wilder1@gmail.com"));
        User user2 = users.save(new User("wilder2", "123123", "와일더2", "wilder2@gmail.com"));

        assertThat(users.count()).isEqualTo(3);
        assertThat(users.findByUserId(user1.getUserId()).get()).isEqualTo(user1);
    }

    @DisplayName("유저를 삭제한다.")
    @Test
    void delete() {
        User user = users.save(new User("wilder", "123123", "와일더", "wilder@gmail.com"));
        users.delete(user);

        assertThatThrownBy(() -> users.findByUserId(user.getUserId()).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @AfterEach
    void tearDown() {
        users.flush();
    }
}
