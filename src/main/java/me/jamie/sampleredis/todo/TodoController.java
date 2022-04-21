package me.jamie.sampleredis.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    private static final Map<Long, Todo> localStore = new ConcurrentHashMap<>();

    @TimeTrace
    @GetMapping
    public List<Todo> list() {
        List<Todo> todos = todoService.getTodos();
        System.out.println(todos.get(0));
        todos.stream().forEach(todo -> localStore.put(todo.getId(), todo));
        return todos;
    }

    @TimeTrace
    @GetMapping("/h2")
    public List<Todo> h2db() {
        return todoService.getTodos();
    }

    @TimeTrace
    @GetMapping("/local")
    public List<Todo> localCache() {
        return new ArrayList<>(localStore.values());
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
