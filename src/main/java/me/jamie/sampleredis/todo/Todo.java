package me.jamie.sampleredis.todo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo {

    @Column(name = "todo_id")
    @GeneratedValue
    @Id
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private TodoStatus todoStatus;

    @Builder
    private Todo(Long id, String content, TodoStatus todoStatus) {
        this.id = id;
        this.content = content;
        this.todoStatus = todoStatus;
    }
}
