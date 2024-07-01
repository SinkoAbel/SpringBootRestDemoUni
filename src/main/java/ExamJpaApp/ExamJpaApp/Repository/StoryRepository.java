package ExamJpaApp.ExamJpaApp.Repository;

import ExamJpaApp.ExamJpaApp.Model.Story;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// Implement db methods
@Repository
public interface StoryRepository extends JpaRepository<Story, Long> { }
