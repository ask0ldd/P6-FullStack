package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.reponses.TopicResponseDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService){
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public ResponseEntity<?> findAll() {
        try {
            List<Topic> topics = topicService.getAll();

            if (topics.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            List<TopicResponseDto> responsesDtoList = topics.stream()
                    .map(TopicResponseDto::new)
                    .toList();

            return ResponseEntity.ok().body(responsesDtoList);
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e + "\u001B[0m");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/topics/byuser/{userId}")
    public ResponseEntity<?> findAllTopicsUserIsSubscribedTo(@PathVariable("userId") String userId) {
        try {
            /// !!! should check if user exists
            List<Topic> topics = topicService.getAllTopicsUserIsSubscribedTo(Long.parseLong(userId));

            if (topics.isEmpty()) {
                return ResponseEntity.ok().body(new ArrayList<TopicResponseDto>());
            }

            List<TopicResponseDto> responsesDtoList = topics.stream()
                    .map(TopicResponseDto::new)
                    .toList();

            return ResponseEntity.ok().body(responsesDtoList);
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e + "\u001B[0m");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/topic/{topicId}/subscribe/{userId}")
    public ResponseEntity<?> subscribe(@PathVariable("topicId") String topicId, @PathVariable("userId") String userId) {
        try {
            topicService.subscribe(Long.parseLong(topicId), Long.parseLong(userId));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e + "\u001B[0m");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/topic/{topicId}/unsubscribe/{userId}")
    public ResponseEntity<?> unsubscribe(@PathVariable("topicId") String topicId, @PathVariable("userId") String userId) {
        try {
            topicService.unsubscribe(Long.parseLong(topicId), Long.parseLong(userId)); // throw NumberFormatException
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e + "\u001B[0m");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
