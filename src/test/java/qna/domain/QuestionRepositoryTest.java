package qna.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questions;

    @Autowired
    private AnswerRepository answers;

    @Autowired
    private UserRepository users;

    @DisplayName("질문을 생성한다.")
    @Test
    void save() {
        Question question = questions.save(new Question("첫 번째 질문", "이 테스트는 성공 할까요?"));
        assertThat(question.getTitle()).isEqualTo("첫 번째 질문");
    }

    @DisplayName("질문을 조회한다.")
    @Test
    void findByDeletedFalse() {
        Question question = questions.save(new Question("첫 번째 질문", "이 테스트는 성공 할까요?"));
        assertThat(questions.findByDeletedFalse().get(0)).isEqualTo(question);
    }

    @DisplayName("질문을 삭제하면 연관된 답변도 삭제된다.")
    @Test
    void delete() {
        User writer = users.save(new User("wilder", "123321", "와일더", "wilder@gmail.com"));
        Question question = questions.save(new Question("내일 출근하나요?", "이번주 월요일 출근 합니까?"));
        Answer answer = answers.save(new Answer(writer, question, "코로나 거리두기 격상으로 인해 월요일에 출근하지 않습니다."));
        question.addAnswer(answer);

        Question foundQuestion = questions.findById(question.getId()).get();
        Answer foundAnswer = answers.findById(answer.getId()).get();
        assertThat(foundAnswer.getQuestion()).isEqualTo(foundQuestion);

        foundQuestion.delete(foundAnswer);
        assertThat(foundQuestion.isDeleted()).isTrue();
        assertThat(foundAnswer.isDeleted()).isTrue();
    }


    @AfterEach
    void tearDown() {
        questions.flush();
        answers.flush();
        users.flush();
    }
}
