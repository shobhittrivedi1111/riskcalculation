package com.nagarro.riskcalculation.testcontroller;
import com.nagarro.riskcalculation.controller.ScoreCapController;
import com.nagarro.riskcalculation.model.ScoreCap;
import com.nagarro.riskcalculation.service.ScoreCapService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class ScoreCapControllerTests {

    @Mock
    private ScoreCapService scoreCapService;

    @InjectMocks
    private ScoreCapController scoreCapController;
//    @Test
//    public void testGetAllScoreCaps() {
//        List<ScoreCap> scoreCapList=Arrays.asList(
//                new ScoreCap(1,"1 very high risk","40"),
//                new ScoreCap(2,"2 very high risk","30")
//        );
//        when(scoreCapService.getAllScoreCaps()).thenReturn(scoreCapList);
//        ResponseEntity<List<ScoreCap>> response = scoreCapController.getAllScoreCaps();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(scoreCapList, response.getBody());
//        verify(scoreCapService, times(1)).getAllScoreCaps();
//        verifyNoMoreInteractions(scoreCapService);
//    }
//
//    @Test
//    void testGetScoreCapById() {
//        // Mock data
//        int id = 1;
//        ScoreCap scoreCap = new ScoreCap(1,"1 very high risk","40");
//        when(scoreCapService.getScoreCapById(id)).thenReturn(scoreCap);
//        ResponseEntity<ScoreCap> response = scoreCapController.getScoreCapById(id);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(scoreCap, response.getBody());
//        verify(scoreCapService, times(1)).getScoreCapById(id);
//    }
//
//
//
//    @Test
//    public void testCreateScoreCap() {
//        ScoreCap scoreCap= new ScoreCap(1,"1 very high risk","40");
//        when(scoreCapService.createScoreCap(scoreCap)).thenReturn(scoreCap);
//        ResponseEntity<ScoreCap> response = scoreCapController.createScoreCap(scoreCap);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(scoreCap, response.getBody());
//        verify(scoreCapService, times(1)).createScoreCap(scoreCap);
//        verifyNoMoreInteractions(scoreCapService);
//    }
//
//    @Test
//    public void testUpdateScoreCap() {
//        ScoreCap updatedScoreCap= new ScoreCap(1,"1 very high risk","40");
//        when(scoreCapService.updateScoreCap(1, updatedScoreCap)).thenReturn(updatedScoreCap);
//        ResponseEntity<ScoreCap> response = scoreCapController.updateScoreCap(1, updatedScoreCap);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(updatedScoreCap, response.getBody());
//        verify(scoreCapService, times(1)).updateScoreCap(1, updatedScoreCap);
//        verifyNoMoreInteractions(scoreCapService);
//    }
//
//    @Test
//    public void testDeleteScoreCap() {
//        when(scoreCapService.deleteScoreCap(1)).thenReturn(true);
//        ResponseEntity<?> response = scoreCapController.deleteScoreCap(1);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(scoreCapService, times(1)).deleteScoreCap(1);
//        verifyNoMoreInteractions(scoreCapService);
//    }


}

