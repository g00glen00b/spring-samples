package codes.dimitri.htmx.todoitem.internal;

import codes.dimitri.htmx.todoitem.TodoItemDTO;
import org.mapstruct.Mapper;

@Mapper
interface TodoItemMapper {
    TodoItemDTO toDto(TodoItem item);
}
