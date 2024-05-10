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

    public List<Topic> getAll(){
        List<Topic> topics = topicRepository.findAll();
        if (topics.isEmpty()) {
            throw new TopicNotFoundException("No topic can be found.");
        }
        return topics;
    }

    public Topic getById(Long id){
        return topicRepository.findById(id).orElse(null);
    }

    public List<Topic> getUserSubscriptions(){
        List<Topic> topicsSubscribedTo = topicRepository.findAll();
        if (topicsSubscribedTo.isEmpty()) {
            throw new ResourceNotFoundException("The user is subscribed to no topic.");
        }
        return topicsSubscribedTo;
    }

    public Topic create(Topic topic){
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopicsUserIsSubscribedTo(Long userId){
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Can't find user with id : " + userId));
        return topicRepository.findAllByUsersContaining(user);
    }

    public void subscribe(Long topicId, Long userId){
        Topic topic = this.topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException("Can't find topic with id : " + topicId));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Can't find subscriber with id : " + userId));

        // check if the user is not already subscribed
        boolean alreadySubscribed = topic.getUsers().stream().anyMatch(o -> o.getId().equals(userId));
        if(alreadySubscribed) {
            throw new BadRequestException();
        }

        topic.getUsers().add(user);
        this.topicRepository.save(topic);

    }

    public void unsubscribe(Long topicId, Long userId) {
        Topic topic = this.topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException("Can't find topic with id : " + topicId));

        // check if the user is subscribed, if not, can't be unsub
        boolean alreadySubscribed = topic.getUsers().stream().anyMatch(o -> o.getId().equals(userId));
        if(!alreadySubscribed) {
            throw new BadRequestException();
        }

        topic.setUsers(topic.getUsers().stream().filter(user -> !user.getId().equals(userId)).collect(Collectors.toList()));

        this.topicRepository.save(topic);
    }
}
