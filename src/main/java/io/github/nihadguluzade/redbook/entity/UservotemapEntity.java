package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;

@Entity
@Table(name = "uservotemap", schema = "redbook", catalog = "redbook")
public class UservotemapEntity {
    private int voteId;
    private UsersEntity usersByUserId;
    private SubmissionEntity submissionBySubmissionId;
    private ReactionEntity reactionByReactionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UservotemapEntity that = (UservotemapEntity) o;

        if (voteId != that.voteId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return voteId;
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
    @JoinColumn(name = "submission_id", referencedColumnName = "submission_id")
    public SubmissionEntity getSubmissionBySubmissionId() {
        return submissionBySubmissionId;
    }

    public void setSubmissionBySubmissionId(SubmissionEntity submissionBySubmissionId) {
        this.submissionBySubmissionId = submissionBySubmissionId;
    }

    @ManyToOne
    @JoinColumn(name = "reaction_id", referencedColumnName = "reaction_id")
    public ReactionEntity getReactionByReactionId() {
        return reactionByReactionId;
    }

    public void setReactionByReactionId(ReactionEntity reactionByReactionId) {
        this.reactionByReactionId = reactionByReactionId;
    }
}
