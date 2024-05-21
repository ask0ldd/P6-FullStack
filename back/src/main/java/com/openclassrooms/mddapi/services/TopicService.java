package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.ITopicService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService implements ITopicService {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    public List<Topic> getAll(){
        List<Topic> topics = topicRepository.findAll();
        if (topics.isEmpty()) {
            throw new TopicNotFoundException("No topic can be found.");
        }
        return topics;
    }

    /**
     * {@inheritDoc}
     * Retrieves a topic by its ID.
     */
    public Topic getById(Long id){
        return topicRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     * Creates a new topic.
     */
    public Topic create(Topic topic){
        return topicRepository.save(topic);
    }

    /**
     * {@inheritDoc}
     * Retrieves all topics a user is subscribed to.
     */
    public List<Topic> getAllTopicsUserIsSubscribedTo(String userEmail){
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Can't find user with email : " + userEmail));
        return topicRepository.findAllByUsersContaining(user);
    }

    /**
     * {@inheritDoc}
     * Subscribes a user to a topic.
     */
    public void subscribe(Long topicId, String userEmail){
        // checks if the topics exists before any subscription and retrieves it
        Topic topic = this.topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException("Can't find topic with id : " + topicId));

        // checks if the user exists and retrieves it
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Can't find subscriber."));

        // check if the user is not already subscribed
        boolean alreadySubscribed = topic.getUsers().stream().anyMatch(o -> o.getId().equals(user.getId()));
        if(alreadySubscribed) {
            throw new BadRequestException();
        }

        topic.getUsers().add(user);
        this.topicRepository.save(topic);
    }

    /**
     * {@inheritDoc}
     * Unsubscribes a user from a topic.
     */
    public void unsubscribe(Long topicId, String userEmail) {
        // checks if the topics exists before any unsub and retrieves it
        Topic topic = this.topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException("Can't find topic with id : " + topicId));
        // checks if the user exists and retrieves it
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Can't find subscriber."));

        // check if the user is subscribed, if not, can't be unsub
        boolean alreadySubscribed = topic.getUsers().stream().anyMatch(o -> o.getId().equals(user.getId()));
        if(!alreadySubscribed) {
            throw new BadRequestException();
        }

        topic.setUsers(topic.getUsers().stream().filter(topicUser -> !topicUser.getId().equals(user.getId())).collect(Collectors.toList()));

        this.topicRepository.save(topic);
    }
}
