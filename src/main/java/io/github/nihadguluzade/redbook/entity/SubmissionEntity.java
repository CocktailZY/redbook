package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "submission", schema = "redbook", catalog = "redbook")
public class SubmissionEntity {
    private int submissionId;
    private int pageId;
    private String title;
    private String content;
    private int score = 0;
    private Timestamp posted = new Timestamp(System.currentTimeMillis());
    private Boolean nsfw = false;
    private Boolean spoiler = false;
    private Boolean archived = false;
    private Boolean locked = false;
    private Boolean edited = false;
    private Timestamp editDatetime;
    private Boolean removed = false;
    private String removeReason;
    private Timestamp removeDatetime;
    private Boolean pinned = false;
    private int numComments = 0;
    private Collection<CommentsEntity> commentsBySubmissionId;
    private PageEntity pageByPageId;
    private UsersEntity usersByAuthorId;
    private SubmissiontypeEntity submissiontypeBySubmissionTypeId;
    private ImageEntity imageByMedia;
    private Collection<UserpagemapEntity> userpagemapsBySubmissionId;
    private Collection<UservotemapEntity> uservotemapsBySubmissionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    @Basic
    @Column(name = "page_id", insertable = false, updatable = false, nullable = false)
    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    @Column(name = "nsfw")
    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
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
    @Column(name = "archived")
    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @Basic
    @Column(name = "_locked")
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
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
    @Column(name = "removed")
    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    @Basic
    @Column(name = "remove_reason")
    public String getRemoveReason() {
        return removeReason;
    }

    public void setRemoveReason(String removeReason) {
        this.removeReason = removeReason;
    }

    @Basic
    @Column(name = "remove_datetime")
    public Timestamp getRemoveDatetime() {
        return removeDatetime;
    }

    public void setRemoveDatetime(Timestamp removeDatetime) {
        this.removeDatetime = removeDatetime;
    }

    @Basic
    @Column(name = "pinned")
    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    @Basic
    @Column(name = "num_comments")
    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubmissionEntity that = (SubmissionEntity) o;

        if (submissionId != that.submissionId) return false;
        if (score != that.score) return false;
        if (nsfw != that.nsfw) return false;
        if (numComments != that.numComments) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (posted != null ? !posted.equals(that.posted) : that.posted != null) return false;
        if (spoiler != null ? !spoiler.equals(that.spoiler) : that.spoiler != null) return false;
        if (archived != null ? !archived.equals(that.archived) : that.archived != null) return false;
        if (locked != null ? !locked.equals(that.locked) : that.locked != null) return false;
        if (edited != null ? !edited.equals(that.edited) : that.edited != null) return false;
        if (editDatetime != null ? !editDatetime.equals(that.editDatetime) : that.editDatetime != null) return false;
        if (removed != null ? !removed.equals(that.removed) : that.removed != null) return false;
        if (removeReason != null ? !removeReason.equals(that.removeReason) : that.removeReason != null) return false;
        if (removeDatetime != null ? !removeDatetime.equals(that.removeDatetime) : that.removeDatetime != null)
            return false;
        if (pinned != null ? !pinned.equals(that.pinned) : that.pinned != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = submissionId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + (posted != null ? posted.hashCode() : 0);
        result = 31 * result + (nsfw ? 1 : 0);
        result = 31 * result + (spoiler != null ? spoiler.hashCode() : 0);
        result = 31 * result + (archived != null ? archived.hashCode() : 0);
        result = 31 * result + (locked != null ? locked.hashCode() : 0);
        result = 31 * result + (edited != null ? edited.hashCode() : 0);
        result = 31 * result + (editDatetime != null ? editDatetime.hashCode() : 0);
        result = 31 * result + (removed != null ? removed.hashCode() : 0);
        result = 31 * result + (removeReason != null ? removeReason.hashCode() : 0);
        result = 31 * result + (removeDatetime != null ? removeDatetime.hashCode() : 0);
        result = 31 * result + (pinned != null ? pinned.hashCode() : 0);
        result = 31 * result + numComments;
        return result;
    }

    @OneToMany(mappedBy = "submissionBySubmissionId")
    public Collection<CommentsEntity> getCommentsBySubmissionId() {
        return commentsBySubmissionId;
    }

    public void setCommentsBySubmissionId(Collection<CommentsEntity> commentsBySubmissionId) {
        this.commentsBySubmissionId = commentsBySubmissionId;
    }

    @ManyToOne
    @JoinColumn(name = "page_id", referencedColumnName = "page_id")
    public PageEntity getPageByPageId() {
        return pageByPageId;
    }

    public void setPageByPageId(PageEntity pageByPageId) {
        this.pageByPageId = pageByPageId;
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
    @JoinColumn(name = "submission_type_id", referencedColumnName = "submissiontype_id")
    public SubmissiontypeEntity getSubmissiontypeBySubmissionTypeId() {
        return submissiontypeBySubmissionTypeId;
    }

    public void setSubmissiontypeBySubmissionTypeId(SubmissiontypeEntity submissiontypeBySubmissionTypeId) {
        this.submissiontypeBySubmissionTypeId = submissiontypeBySubmissionTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "media", referencedColumnName = "image_id")
    public ImageEntity getImageByMedia() {
        return imageByMedia;
    }

    public void setImageByMedia(ImageEntity imageByMedia) {
        this.imageByMedia = imageByMedia;
    }

    @OneToMany(mappedBy = "submissionByPageId")
    public Collection<UserpagemapEntity> getUserpagemapsBySubmissionId() {
        return userpagemapsBySubmissionId;
    }

    public void setUserpagemapsBySubmissionId(Collection<UserpagemapEntity> userpagemapsBySubmissionId) {
        this.userpagemapsBySubmissionId = userpagemapsBySubmissionId;
    }

    @OneToMany(mappedBy = "submissionBySubmissionId")
    public Collection<UservotemapEntity> getUservotemapsBySubmissionId() {
        return uservotemapsBySubmissionId;
    }

    public void setUservotemapsBySubmissionId(Collection<UservotemapEntity> uservotemapsBySubmissionId) {
        this.uservotemapsBySubmissionId = uservotemapsBySubmissionId;
    }
}
