package me.jamie.sampleredis.todo;

import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.jamie.sampleredis.aop.TimeTrace;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController
public class TodoController {

    private final TodoService todoService;

    @TimeTrace
    @GetMapping
    public List<Todo> list() {
        return todoService.getTodos();
    }

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.doInit();
    }

    @RequiredArgsConstructor
    @Transactional
    @Component
    static class InitService {

        private final EntityManager em;

        public void doInit() {
            IntStream.range(0, 100)
                .forEach(i -> {
                    Todo todo = Todo.builder()
                        .content("content")
                        .todoStatus(TodoStatus.READY)
                        .build();

                    em.persist(todo);
                });
        }
    }
}
