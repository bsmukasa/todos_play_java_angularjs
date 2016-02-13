package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tasks")
public class Task extends Model {
    @Id
    @GeneratedValue
    public Long id;

    @Column(length = 255, nullable = false)
    @Constraints.Required
    public String title;

    public boolean isDone = false;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    public User owner;

//    public Date dueDate;

    // TODO Consider adding validation function.

    public static final Finder<Long, Task> find = new Finder<>(Task.class);

    public void toggleIsDone() {
        this.isDone = !isDone;
    }

    public static List<Task> findAllByUserEmail(String email) {
        return find.where().eq("owner.email", email).findList();
    }

    public static List<Task> findIncompleteByUserEmail(String email) {
        return find.where().eq("isDone", false).eq("owner.email", email).findList();
    }
}
