package qna.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class AnswerRepositoryTest {
    @Autowired
    private AnswerRepository answers;

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;

    private User writer;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        writer = users.save(new User("wilder", "123123", "와일더", "wilder@gmail.com"));
        question = questions.save(new Question("제목", "내용"));
        answer = answers.save(new Answer(writer, question, "내용을 좀 더 길게 써주세요."));
    }

    @DisplayName("답변을 저장한다.")
    @Test
    void save() {
        assertThat(answers.findById(answer.getId()).get()).isEqualTo(answer);
    }

    @DisplayName("답변의 질문을 조회한다.")
    @Test
    void findQuestion() {
        Optional<Answer> findById = answers.findById(answer.getId());
        assertThat(findById.get().getQuestion()).isEqualTo(question);
    }

    @DisplayName("답변의 글쓴이를 조회한다.")
    @Test
    void findWriter() {
        Optional<Answer> findById = answers.findById(answer.getId());
        assertThat(findById.get().getWriter()).isEqualTo(writer);
    }

    @DisplayName("답변을 제거한다.")
    @Test
    void delete() {
        answers.delete(answer);
        assertThatThrownBy(() -> answers.findById(answer.getId()).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @AfterEach
    void tearDown() {
        answers.flush();
        questions.flush();
        users.flush();
    }
}
