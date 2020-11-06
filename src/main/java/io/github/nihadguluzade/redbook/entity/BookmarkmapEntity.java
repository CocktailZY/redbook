package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;

@Entity
@Table(name = "bookmarkmap", schema = "redbook", catalog = "redbook")
public class BookmarkmapEntity {
    private int bookmarkMapId;
    private boolean submission;
    private Integer postId;
    private UsersEntity usersByUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_map_id")
    public int getBookmarkMapId() {
        return bookmarkMapId;
    }

    public void setBookmarkMapId(int bookmarkMapId) {
        this.bookmarkMapId = bookmarkMapId;
    }

    @Basic
    @Column(name = "submission")
    public boolean isSubmission() {
        return submission;
    }

    public void setSubmission(boolean submission) {
        this.submission = submission;
    }

    @Basic
    @Column(name = "post_id")
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookmarkmapEntity that = (BookmarkmapEntity) o;

        if (bookmarkMapId != that.bookmarkMapId) return false;
        if (submission != that.submission) return false;
        if (postId != null ? !postId.equals(that.postId) : that.postId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookmarkMapId;
        result = 31 * result + (submission ? 1 : 0);
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
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
}
