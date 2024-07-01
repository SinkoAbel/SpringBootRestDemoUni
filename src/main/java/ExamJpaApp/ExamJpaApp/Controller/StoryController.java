package ExamJpaApp.ExamJpaApp.Controller;

import ExamJpaApp.ExamJpaApp.Model.Story;
import ExamJpaApp.ExamJpaApp.Repository.StoryRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StoryController {
    @Autowired
    StoryRepository storyRepository;

    @GetMapping("/show-stories")
    public String showStories(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        model.addAttribute("story", new Story());
        return "stories";
    }

    @GetMapping("/stories")
    public ResponseEntity<List<Story>> returnAllStories() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storyRepository.findAll());
    }

    @PostMapping("/create-story")
    public String createStory(@ModelAttribute("story") Story story) {
        Story s = new Story(story.getTitle(), story.getContent());

        storyRepository.save(s);
        return "redirect:/show-stories";
    }

}
