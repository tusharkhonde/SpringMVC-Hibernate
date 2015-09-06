package multitenant.models;

import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

/**
 * Created by TUSHAR_SK on 8/4/15.
 */
@SuppressWarnings("all")
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters=@org.hibernate.annotations.Parameter(name="property", value="user"))
    private Long id;

    @NotNull
    @Column(name = "projectname")
    private String projectname;

    @Type(type = "date")
    @Column(name = "startdate")
    private Date startdate;

    @Type(type = "date")
    @Column(name = "enddate")
    private Date enddate;

    @NotNull
    @Column(name= "assignedto")
    private String assignedto;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private User user;

    @OneToMany(mappedBy = "project")
    private List<Story> story;

    @OneToMany(mappedBy = "project")
    private List<Task> task;

    @OneToMany(mappedBy = "project")
    private List<Card> card;

    public Project() {
    }

    public Project(String projectname, Date startdate, Date enddate, String assignedto) {
        this.projectname = projectname;
        this.startdate = startdate;
        this.enddate = enddate;
        this.assignedto = assignedto;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public List<Story> getStory() {
        return story;
    }

    public void setStory(List<Story> story) {
        this.story = story;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }

    public List<Card> getCard() {
        return card;
    }

    public void setCard(List<Card> card) {
        this.card = card;
    }

    public String getAssignedto() {
        return assignedto;
    }

    public void setAssignedto(String assignedto) {
        this.assignedto = assignedto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
