package ExamJpaApp.ExamJpaApp.Controller;

import ExamJpaApp.ExamJpaApp.Model.Tutorial;
import ExamJpaApp.ExamJpaApp.Repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TutorialController {
    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials
            (@RequestParam(required = false) String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if(title == null) {
            tutorials = tutorialRepository.findAll();
        } else {
            tutorials = tutorialRepository.findByTitleContaining(title);
        }

        if (tutorials.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tutorials);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Tutorial tutorial = tutorialRepository.findById(id).orElse(null);

        if (tutorial == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


        return ResponseEntity.status(HttpStatus.OK).body(tutorial);
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial _tutorial = new Tutorial(
                tutorial.getTitle(),
                tutorial.getDescription(),
                tutorial.isPublished()
        );

        tutorialRepository.save(_tutorial);

        return ResponseEntity.status(HttpStatus.CREATED).body(_tutorial);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<List<Tutorial>> deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(tutorialRepository.findAll());
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> deleteAllTutorial() {
        tutorialRepository.deleteAll();

        return ResponseEntity.status(HttpStatus.OK).body(tutorialRepository.findAll());
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> getAllPublishedTutorial() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

        if (tutorials.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tutorials);
    }

}
