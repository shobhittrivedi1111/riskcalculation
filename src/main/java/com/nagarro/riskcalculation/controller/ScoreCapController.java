package com.nagarro.riskcalculation.controller;
import com.nagarro.riskcalculation.model.ScoreCap;
import com.nagarro.riskcalculation.service.ScoreCapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/score-caps")
public class ScoreCapController {
    @Autowired
    private ScoreCapService scoreCapService;


    @GetMapping
    public ResponseEntity<List<ScoreCap>> getAllScoreCaps() {
        log.info("Retrieved all Score Caps: {}");
        return new ResponseEntity<>(scoreCapService.getAllScoreCaps(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreCap> getScoreCapById(@PathVariable int id) {
        log.info("Retrieved Score Cap: {}");
        return new ResponseEntity<>(scoreCapService.getScoreCapById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ScoreCap> createScoreCap(@RequestBody ScoreCap scoreCap) {
        log.info("Retriving Score Cap: {}", scoreCap);
        return new ResponseEntity<>(scoreCapService.createScoreCap(scoreCap), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreCap> updateScoreCap(@PathVariable int id, @RequestBody ScoreCap scoreCap) {
        log.info("Updating Score Cap: {}", scoreCap);
        return new ResponseEntity<>(scoreCapService.updateScoreCap(id, scoreCap), HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScoreCap(@PathVariable int id) {
        log.info("Deleting Score Cap: {}", id);
        return new ResponseEntity<>(scoreCapService.deleteScoreCap(id), HttpStatus.NO_CONTENT);
    }
}

