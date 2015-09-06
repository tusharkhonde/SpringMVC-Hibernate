package multitenant.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TUSHAR_SK on 8/5/15.
 */
@SuppressWarnings("all")
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="cardId")
    private Long cardId;

    @Column(name = "Name")
    private String cardName;

    @Column(name = "Description")
    private String cardDescription;

    @Column(name = "Type")
    private String cardType;

    @Column(name = "assignedTo")
    private String assignedTo;

    @ManyToOne()
    @JoinColumn(name="id")
    private Project project;

    public Card() {
    }

    public Card(String cardName, String cardDescription, String cardType, String assignedTo) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardType = cardType;
        this.assignedTo = assignedTo;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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
