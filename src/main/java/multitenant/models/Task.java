package multitenant.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by TUSHAR_SK on 8/4/15.
 */
@SuppressWarnings("all")
@Entity
@Table(name = "task")
public class Task {

    @ManyToOne()
    @JoinColumn(name="id")
    private Project project;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="taskId")
    private Long taskId;

    @Column(name = "Name")
    private String taskName;

    @Column(name = "Description")
    private String taskDescription;

    @Column(name = "Startdate")
    private Date startDate;

    @Column(name = "Finishdate")
    private Date finishDate;

    @Column(name = "Tasktype")
    private String taskType;

    @Column(name = "assignedTo")
    private String assignedTo;

    public Task() {
    }

    public Task(String taskName, String taskDescription, Date startDate, Date finishDate, String taskType, String assignedTo) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.taskType = taskType;
        this.assignedTo = assignedTo;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getStartdate() {
        return startDate;
    }

    public void setStartdate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishdate() {
        return finishDate;
    }

    public void setFinishdate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getTasktype() {
        return taskType;
    }

    public void setTasktype(String taskType) {
        this.taskType = taskType;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
