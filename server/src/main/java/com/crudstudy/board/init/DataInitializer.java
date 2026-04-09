package com.crudstudy.board.init;

import com.crudstudy.board.domain.Comment;
import com.crudstudy.board.domain.Post;
import com.crudstudy.board.domain.Role;
import com.crudstudy.board.domain.User;
import com.crudstudy.board.repository.CommentRepository;
import com.crudstudy.board.repository.PostRepository;
import com.crudstudy.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if(postRepository.count() > 0) return;

        Faker faker = new Faker(new Locale("ko"));

        //유저 생성
        List<User> users = IntStream.range(0, 10)
                .mapToObj(i -> User.builder()
                        .name(faker.name().fullName())
                        .email(faker.internet().emailAddress())
                        .password(passwordEncoder.encode("test1234!"))
                        .role(Role.ROLE_USER)
                        .build())
                .collect(java.util.stream.Collectors.toList());
        userRepository.saveAll(users);

        // 2. 포스트 50개 생성 (유저 랜덤 배정)
        List<Post> posts = IntStream.range(0, 50)
                .mapToObj(i -> Post.builder()
                        .title(faker.lorem().sentence())
                        .content(faker.lorem().paragraph())
                        .user(users.get(i % users.size())) // 유저 순환 배정
                        .build())
                .collect(java.util.stream.Collectors.toList());
        postRepository.saveAll(posts);

        // 3. 댓글 생성 (포스트당 3개)
        List<Comment> comments = posts.stream()
                .flatMap(post -> IntStream.range(0, 3)
                        .mapToObj(i -> Comment.builder()
                                .post(post)
                                .user(users.get(i % users.size())) // 유저 순환 배정
                                .content(faker.lorem().sentence())
                                .build()))
                .collect(java.util.stream.Collectors.toList());
        commentRepository.saveAll(comments);

        log.info("initData count: {}", postRepository.count()
                + commentRepository.count()
                + userRepository.count());
    }
}
