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
    public ResponseEntity<List<TopicResponseDto>> findAll() {
        List<Topic> topics = topicService.getAll();
        List<TopicResponseDto> responsesDtoList = topics.stream()
                .map(TopicResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(responsesDtoList);
    }

    @GetMapping("/topics/byuser/{userId}")
    public ResponseEntity<List<TopicResponseDto>> findAllTopicsUserIsSubscribedTo(@PathVariable("userId") String userId) {
        List<Topic> topics = topicService.getAllTopicsUserIsSubscribedTo(Long.parseLong(userId));
        if (topics.isEmpty()) {
            return ResponseEntity.ok().body(new ArrayList<TopicResponseDto>());
        }

        List<TopicResponseDto> responsesDtoList = topics.stream()
                .map(TopicResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(responsesDtoList);
    }

    @PostMapping("/topic/{topicId}/subscribe/{userId}")
    public ResponseEntity<Void> subscribe(@PathVariable("topicId") String topicId, @PathVariable("userId") String userId) {
        topicService.subscribe(Long.parseLong(topicId), Long.parseLong(userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/topic/{topicId}/unsubscribe/{userId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable("topicId") String topicId, @PathVariable("userId") String userId) {
        topicService.unsubscribe(Long.parseLong(topicId), Long.parseLong(userId)); // throw NumberFormatException
        return ResponseEntity.ok().build();
    }
}
