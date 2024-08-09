package tn.esprit.manajero.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import tn.esprit.manajero.Entities.Question;
import tn.esprit.manajero.Services.QuestionService;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        List<Question> questions = questionService.getQuestionsByCategory(category);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        return ResponseEntity.ok("Question added successfully");
    }

    @PutMapping("update/{_id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable String _id, @RequestBody Question updatedQuestion) {
        Question question = questionService.updateQuestion(_id, updatedQuestion);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteQuestion(@RequestParam String _id){
        questionService.deleteQuestion(_id);
        return ResponseEntity.ok("Question deleted successfully");
    }

    @GetMapping("all")
    public List<Question> getAllQuestion() {
        return questionService.getAllQuestions();
    }


}
