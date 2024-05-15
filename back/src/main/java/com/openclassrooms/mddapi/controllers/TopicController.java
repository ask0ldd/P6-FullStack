package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.reponses.TopicResponseDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService){
        this.topicService = topicService;
    }

    /**
     * Retrieves all available topics.
     *
     * @return a ResponseEntity containing a list of TopicResponseDto objects representing all topics
     */
    @GetMapping("/topics")
    public ResponseEntity<List<TopicResponseDto>> findAll() {
        List<Topic> topics = topicService.getAll();
        List<TopicResponseDto> responsesDtoList = topics.stream()
                .map(TopicResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(responsesDtoList);
    }

    /**
     * Retrieves all topics that the authenticated user is subscribed to.
     *
     * @param principal the authenticated user's principal object, which contains the user's name
     * @return a ResponseEntity containing a list of TopicResponseDto objects representing the topics the user is subscribed to, or an empty list if the user is not subscribed to any topics
     */
    @GetMapping("/topics/byuser")
    public ResponseEntity<List<TopicResponseDto>> findAllTopicsUserIsSubscribedTo(Principal principal) {
        List<Topic> topics = topicService.getAllTopicsUserIsSubscribedTo(principal.getName());
        if (topics.isEmpty()) {
            return ResponseEntity.ok().body(new ArrayList<TopicResponseDto>());
        }

        List<TopicResponseDto> responsesDtoList = topics.stream()
                .map(TopicResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(responsesDtoList);
    }

    /**
     * Subscribes a user to a specific topic.
     *
     * @param topicId the ID of the topic to subscribe to
     * @param principal the authenticated user's principal object, used to retrieve the username
     * @return A ResponseEntity with an empty body and a status of 200 (OK) if the subscription was successful
     */
    @PostMapping("/topic/{topicId}/subscribe")
    public ResponseEntity<Void> subscribe(@PathVariable("topicId") String topicId, Principal principal) {
        topicService.subscribe(Long.parseLong(topicId), principal.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * Unsubscribes the currently authenticated user from the specified topic.
     *
     * @param topicId the ID of the topic to unsubscribe from
     * @param principal the currently authenticated user
     * @return a ResponseEntity with an empty body and a status of 200.
     */
    @DeleteMapping("/topic/{topicId}/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@PathVariable("topicId") String topicId, Principal principal) {
        topicService.unsubscribe(Long.parseLong(topicId), principal.getName()); // throw NumberFormatException
        return ResponseEntity.ok().build();
    }
}
