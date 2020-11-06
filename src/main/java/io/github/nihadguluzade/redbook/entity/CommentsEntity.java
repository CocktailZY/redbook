package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments", schema = "redbook", catalog = "redbook")
public class CommentsEntity {
    private int commentId;
    private String content;
    private int score;
    private Timestamp posted;
    private Boolean spoiler;
    private Boolean edited;
    private Timestamp editDatetime;
    private Boolean deleted;
    private String deleteReason;
    private Boolean pinned;
    private UsersEntity usersByAuthorId;
    private SubmissionEntity submissionBySubmissionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "_content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Basic
    @Column(name = "posted")
    public Timestamp getPosted() {
        return posted;
    }

    public void setPosted(Timestamp posted) {
        this.posted = posted;
    }

    @Basic
    @Column(name = "spoiler")
    public Boolean getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(Boolean spoiler) {
        this.spoiler = spoiler;
    }

    @Basic
    @Column(name = "edited")
    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    @Basic
    @Column(name = "edit_datetime")
    public Timestamp getEditDatetime() {
        return editDatetime;
    }

    public void setEditDatetime(Timestamp editDatetime) {
        this.editDatetime = editDatetime;
    }

    @Basic
    @Column(name = "deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "delete_reason")
    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    @Basic
    @Column(name = "pinned")
    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentsEntity that = (CommentsEntity) o;

        if (commentId != that.commentId) return false;
        if (score != that.score) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (posted != null ? !posted.equals(that.posted) : that.posted != null) return false;
        if (spoiler != null ? !spoiler.equals(that.spoiler) : that.spoiler != null) return false;
        if (edited != null ? !edited.equals(that.edited) : that.edited != null) return false;
        if (editDatetime != null ? !editDatetime.equals(that.editDatetime) : that.editDatetime != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (deleteReason != null ? !deleteReason.equals(that.deleteReason) : that.deleteReason != null) return false;
        if (pinned != null ? !pinned.equals(that.pinned) : that.pinned != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + (posted != null ? posted.hashCode() : 0);
        result = 31 * result + (spoiler != null ? spoiler.hashCode() : 0);
        result = 31 * result + (edited != null ? edited.hashCode() : 0);
        result = 31 * result + (editDatetime != null ? editDatetime.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (deleteReason != null ? deleteReason.hashCode() : 0);
        result = 31 * result + (pinned != null ? pinned.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    public UsersEntity getUsersByAuthorId() {
        return usersByAuthorId;
    }

    public void setUsersByAuthorId(UsersEntity usersByAuthorId) {
        this.usersByAuthorId = usersByAuthorId;
    }

    @ManyToOne
    @JoinColumn(name = "submission_id", referencedColumnName = "submission_id")
    public SubmissionEntity getSubmissionBySubmissionId() {
        return submissionBySubmissionId;
    }

    public void setSubmissionBySubmissionId(SubmissionEntity submissionBySubmissionId) {
        this.submissionBySubmissionId = submissionBySubmissionId;
    }
}
