package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.exceptions.BadRequestException;
import com.openclassrooms.mddapi.exceptions.NotFoundException;

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
     * Retrieves all topics the current user is subscribed to.
     *
     * @return a list of topics the current user is subscribed to
     */
    List<Topic> getUserSubscriptions();

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
     * @param userId the ID of the user
     * @return a list of topics the user is subscribed to
     * @throws NotFoundException if the user is not found
     */
    List<Topic> getAllTopicsUserIsSubscribedTo(Long userId);

    /**
     * Subscribes a user to a topic.
     *
     * @param topicId the ID of the topic
     * @param userId the ID of the user
     * @throws NotFoundException if the topic or user is not found
     * @throws BadRequestException if the user is already subscribed to the topic
     */
    void subscribe(Long topicId, Long userId);

    /**
     * Unsubscribes a user from a topic.
     *
     * @param topicId the ID of the topic
     * @param userId the ID of the user
     * @throws NotFoundException if the topic is not found
     * @throws BadRequestException if the user is not subscribed to the topic
     */
    void unsubscribe(Long topicId, Long userId);
}
