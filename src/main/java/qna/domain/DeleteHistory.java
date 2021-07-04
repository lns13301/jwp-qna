package qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class DeleteHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ContentType contentType;
    private Long contentId;
    private Long deletedById;
    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "delete_by_id")
    private User writer;

    public DeleteHistory(ContentType contentType, Long contentId, User writer, LocalDateTime localDateTime) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.writer = writer;
        this.createDate = localDateTime;
    }

    protected DeleteHistory() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteHistory that = (DeleteHistory) o;
        return Objects.equals(id, that.id) &&
                contentType == that.contentType &&
                Objects.equals(contentId, that.contentId) &&
                Objects.equals(deletedById, that.deletedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contentType, contentId, deletedById);
    }

    @Override
    public String toString() {
        return "DeleteHistory{" +
                "id=" + id +
                ", contentType=" + contentType +
                ", contentId=" + contentId +
                ", deletedById=" + deletedById +
                ", createDate=" + createDate +
                '}';
    }
}
