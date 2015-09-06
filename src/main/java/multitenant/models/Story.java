package multitenant.models;

import javax.persistence.*;

/**
 * Created by TUSHAR_SK on 8/4/15.
 */
@SuppressWarnings("all")
@Entity
@Table(name = "story")
public class Story {

    @ManyToOne()
    @JoinColumn(name="id")
    private Project project;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="storyId")
    private Long storyId;

    @Column(name = "Title")
    private String storyTitle;

    @Column(name = "Description")
    private String storyDescription;

    @Column(name = "totalHours")
    private int totalHours;

    @Column(name = "remainingHours")
    private int remainingHours;

    @Column(name = "assignedTo")
    private String assignedTo;

    public Story() {
    }

    public Story(String storyTitle, String storyDescription, int totalHours, int remainingHours, String assignedTo) {
        this.storyTitle = storyTitle;
        this.storyDescription = storyDescription;
        this.totalHours = totalHours;
        this.remainingHours = remainingHours;
        this.assignedTo = assignedTo;
    }

    public String getStoryDescription() {
        return storyDescription;
    }

    public void setStoryDescription(String storyDescription) {
        this.storyDescription = storyDescription;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getRemainingHours() {
        return remainingHours;
    }

    public void setRemainingHours(int remainingHours) {
        this.remainingHours = remainingHours;
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
