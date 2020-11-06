package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;

@Entity
@Table(name = "userpagemap", schema = "redbook", catalog = "redbook")
public class UserpagemapEntity {
    private int userPageMapId;
    private boolean moderator;
    private UsersEntity usersByUserId;
    private SubmissionEntity submissionByPageId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_page_map_id")
    public int getUserPageMapId() {
        return userPageMapId;
    }

    public void setUserPageMapId(int userPageMapId) {
        this.userPageMapId = userPageMapId;
    }

    @Basic
    @Column(name = "moderator")
    public boolean isModerator() {
        return moderator;
    }

    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserpagemapEntity that = (UserpagemapEntity) o;

        if (userPageMapId != that.userPageMapId) return false;
        if (moderator != that.moderator) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userPageMapId;
        result = 31 * result + (moderator ? 1 : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "page_id", referencedColumnName = "submission_id")
    public SubmissionEntity getSubmissionByPageId() {
        return submissionByPageId;
    }

    public void setSubmissionByPageId(SubmissionEntity submissionByPageId) {
        this.submissionByPageId = submissionByPageId;
    }
}
