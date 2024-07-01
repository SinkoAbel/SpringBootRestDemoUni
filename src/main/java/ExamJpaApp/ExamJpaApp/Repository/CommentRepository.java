package ExamJpaApp.ExamJpaApp.Repository;

import ExamJpaApp.ExamJpaApp.Model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTutorialId(Long tutorialId);

    @Transactional
    void deleteByTutorialId(long tutorialId);

}
