package com.openclassrooms.mddapi;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MddApiApplication implements CommandLineRunner {

	private final UserService userService;
	private final ArticleService articleService;
	private final TopicService topicService;
	private final CommentService commentService;

	public MddApiApplication(UserService userService, ArticleService articleService, TopicService topicService, CommentService commentService) {
		this.userService = userService;
		this.articleService = articleService;
		this.topicService = topicService;
		this.commentService = commentService;
	}

	public static void main(String[] args) {
		SpringApplication.run(MddApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = User.builder().id(1L)
				.admin(true)/*.lastName("LN")
				.firstName("FN")*/.email("ced@ced.com")
				.userName("userName1")
				.password("password").build();
		userService.create(user1);

		Topic topic1 = Topic.builder().id(1L)
				.name("Javascript").description("JavaScript is a high-level, interpreted programming language that is primarily used for web development, creating dynamic and interactive web pages. It is one of the core technologies of the World Wide Web, alongside HTML and CSS. JavaScript is also widely used in server-side programming, game development, and mobile app development.")
				// .users(List.of(user1))
				.build();
		topicService.create(topic1);

		Topic topic2 = Topic.builder().id(2L)
				.name("Angular").description("Angular is a popular open-source web application framework developed and maintained by Google. It is primarily used for building single-page applications (SPAs) and provides a comprehensive set of tools and features for building complex, scalable, and maintainable web applications. Angular utilizes TypeScript, a superset of JavaScript, and follows a component-based architecture, making it easier to manage and organize large-scale projects.")
				.build();
		topicService.create(topic2);

		Topic topic3 = Topic.builder().id(3L)
				.name("React").description("React is a popular JavaScript library for building user interfaces. It uses a component-based architecture, allowing developers to create reusable UI elements. React's virtual DOM and efficient diffing algorithm make it a powerful tool for building fast and responsive web applications.")
				.build();
		topicService.create(topic3);

		Topic topic4 = Topic.builder().id(4L)
				.name("Spring Boot").description("Spring Boot is a popular open-source Java framework that simplifies the development of enterprise-level applications. It provides an opinionated approach to building web applications, reducing the amount of boilerplate code and configuration required. Spring Boot's features include an embedded web server, automatic configuration, and a wide range of built-in libraries and tools, making it a powerful and efficient choice for building modern, cloud-native applications.")
				.build();
		topicService.create(topic4);

		Article article1 = Article.builder().id(1L)
				.title("ArticleTitle1").content(genericText)
				.user(user1).topic(topic1)
				// .comments()
				.build();
		articleService.create(article1);

		Article article2 = Article.builder().id(2L)
				.title("ArticleTitle2").content(genericText)
				.user(user1).topic(topic1)
				// .comments()
				.build();
		articleService.create(article2);

		Article article3 = Article.builder().id(3L)
				.title("ArticleTitle3").content(genericText)
				.user(user1).topic(topic1)
				// .comments()
				.build();
		articleService.create(article3);

		Article article4 = Article.builder().id(4L)
				.title("ArticleTitle4").content(genericText)
				.user(user1).topic(topic1)
				// .comments()
				.build();
		articleService.create(article4);

		Comment comment1 = Comment.builder()
				.id(1L).user(user1)
				.content("JavaScript is a powerful and versatile programming language that has become an essential tool for web development.")
				.article(article1)
				.build();
		commentService.create(comment1);

		Comment comment2 = Comment.builder()
				.id(2L).user(user1)
				.content("While JavaScript has come a long way since its early days, it can still be a bit quirky and challenging to work with at times.")
				.article(article1)
				.build();
		commentService.create(comment2);

	}

	String genericText = "JavaScript is a high-level, dynamic, and interpreted programming language. It is primarily used for web development, creating interactive web pages, and building server-side applications. JavaScript was originally developed by Brendan Eich at Netscape in the 1990s and has since become one of the most widely used programming languages in the world. It is a versatile language that can be used for a variety of tasks, from simple scripting to complex application development. JavaScript is an essential tool for modern web development and continues to evolve and expand its capabilities.";
}
