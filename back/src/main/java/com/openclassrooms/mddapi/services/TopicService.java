package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.BadRequestException;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public List<Topic> getAll(){
        return topicRepository.findAll();
    }

    public Topic getById(Long id){
        return topicRepository.findById(id).orElse(null);
    }

    public List<Topic> getUserSubscriptions(){
        return topicRepository.findAll();
    }

    public Topic create(Topic topic){
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopicsUserIsSubscribedTo(Long userId){
        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new NotFoundException();
        }
        return topicRepository.findAllByUsersContaining(user);
    }

    public void subscribe(Long topicId, Long userId){
        Topic topic = this.topicRepository.findById(topicId).orElse(null);
        User user = this.userRepository.findById(userId).orElse(null);
        if (topic == null || user == null) {
            throw new NotFoundException();
        }
        /*
            boolean alreadyParticipate = session.getUsers().stream().anyMatch(o -> o.getId().equals(userId));
            if(alreadyParticipate) {
                throw new BadRequestException();
            }
        */
        topic.getUsers().add(user);
        this.topicRepository.save(topic);

    }

    public void unsubscribe(Long topicId, Long userId) {
        Topic topic = this.topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            throw new NotFoundException();
        }

        boolean alreadySubscribed = topic.getUsers().stream().anyMatch(o -> o.getId().equals(userId));
        if(!alreadySubscribed) {
            throw new BadRequestException();
        }

        topic.setUsers(topic.getUsers().stream().filter(user -> !user.getId().equals(userId)).collect(Collectors.toList()));

        this.topicRepository.save(topic);
    }
}
