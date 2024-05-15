package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.exceptions.BadRequestException;
import com.openclassrooms.mddapi.exceptions.TopicNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Topic;

import java.util.List;

public interface ITopicService {
    /**
     * Retrieves all topics.
     *
     * @return a list of all topics
     */
    List<Topic> getAll();

    /**
     * Retrieves a topic by its ID.
     *
     * @param id the ID of the topic to retrieve
     * @return the topic with the specified ID, or null if not found
     */
    Topic getById(Long id);

    /**
     * Creates a new topic.
     *
     * @param topic the topic to create
     * @return the created topic
     */
    Topic create(Topic topic);

    /**
     * Retrieves all topics a user is subscribed to.
     *
     * @param email the email of the user
     * @return a list of topics the user is subscribed to
     * @throws UserNotFoundException if the user is not found
     */
    List<Topic> getAllTopicsUserIsSubscribedTo(String email);

    /**
     * Subscribes a user to a topic.
     *
     * @param topicId the ID of the topic
     * @param userEmail the email of the user
     * @throws TopicNotFoundException if the topic is not found
     * @throws UserNotFoundException if the topic is not found
     * @throws BadRequestException if the user is already subscribed to the topic
     */
    void subscribe(Long topicId, String userEmail);

    /**
     * Unsubscribes a user from a topic.
     *
     * @param topicId the ID of the topic
     * @param userEmail the email of the user
     * @throws TopicNotFoundException if the topic is not found
     * @throws UserNotFoundException if the topic is not found
     * @throws BadRequestException if the user is not subscribed to the topic
     */
    void unsubscribe(Long topicId, String userEmail);
}
