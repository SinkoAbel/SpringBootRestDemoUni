package ExamJpaApp.ExamJpaApp.Controller;

import ExamJpaApp.ExamJpaApp.Model.Comment;
import ExamJpaApp.ExamJpaApp.Model.Tutorial;
import ExamJpaApp.ExamJpaApp.Repository.CommentRepository;
import ExamJpaApp.ExamJpaApp.Repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByTutorialId (@PathVariable(value = "tutorialId") Long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Comment> comments = commentRepository.findByTutorialId(tutorialId);

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentById (@PathVariable(value = "id") Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @PostMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<Comment> createComment(
            @PathVariable(value = "tutorialId") Long tutorialId,
            @RequestBody Comment commentRequest
    ) {
        Tutorial _tutorial = tutorialRepository.findById(tutorialId).orElse(null);

        if (_tutorial == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Ez jobb!
        //Comment newComment = new Comment(commentRequest.getContent(), _tutorial);
        //commentRepository.save(newComment);
        commentRequest.setTutorial(_tutorial);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentRepository.save(commentRequest));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment (@PathVariable("id") long id, @RequestBody Comment comment) {
        Comment _comment = commentRepository.findById(id).orElse(null);

        if (_comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        _comment.setComment(comment.getComment());

        return ResponseEntity.status(HttpStatus.OK).body(_comment);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<List<Comment>> deleteComment(@PathVariable("id") long id) {
        commentRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(commentRepository.findAll());
    }

    @DeleteMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<Comment>> deleteByTutorialId (@PathVariable("tutorialId") long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        commentRepository.deleteByTutorialId(tutorialId);

        return ResponseEntity.status(HttpStatus.OK).body(commentRepository.findAll());
    }
}
