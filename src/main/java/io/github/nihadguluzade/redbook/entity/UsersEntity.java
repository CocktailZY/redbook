package io.github.nihadguluzade.redbook.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "users", schema = "redbook", catalog = "redbook")
public class UsersEntity {
    private int userId;
    private String username;
    private String email;
    private String password;
    private Date dateOfBirth;
    private LocalDate joinDate = LocalDate.now();
    private String redditUserId;
    private String redditAuthToken;
    private String description;
    private Integer karma = 0;
    private BigDecimal coins = new BigDecimal("0");
    private Boolean banned = false;
    private Boolean deleted = false;
    private Timestamp deleteDate;
    private Collection<BookmarkmapEntity> bookmarkmapsByUserId;
    private Collection<CommentsEntity> commentsByUserId;
    private Collection<MovementlogEntity> movementlogsByUserId;
    private Collection<PrivilegemapEntity> privilegemapsByUserId;
    private Collection<SubmissionEntity> submissionsByUserId;
    private Collection<UserpagemapEntity> userpagemapsByUserId;
    private ImageEntity imageByAvatarId;
    private Collection<UservotemapEntity> uservotemapsByUserId;
    private List<PrivilegeEntity> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @DateTimeFormat(pattern="MM/dd/yyyy")
    @Column(name = "date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "join_date")
    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    @Basic
    @Column(name = "reddit_user_id")
    public String getRedditUserId() {
        return redditUserId;
    }

    public void setRedditUserId(String redditUserId) {
        this.redditUserId = redditUserId;
    }

    @Basic
    @Column(name = "reddit_auth_token")
    public String getRedditAuthToken() {
        return redditAuthToken;
    }

    public void setRedditAuthToken(String redditAuthToken) {
        this.redditAuthToken = redditAuthToken;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "karma")
    public Integer getKarma() {
        return karma;
    }

    public void setKarma(Integer karma) {
        this.karma = karma;
    }

    @Basic
    @Column(name = "coins")
    public BigDecimal getCoins() {
        return coins;
    }

    public void setCoins(BigDecimal coins) {
        this.coins = coins;
    }

    @Basic
    @Column(name = "banned")
    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
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
    @Column(name = "delete_date")
    public Timestamp getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Timestamp deleteDate) {
        this.deleteDate = deleteDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (userId != that.userId) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (joinDate != null ? !joinDate.equals(that.joinDate) : that.joinDate != null) return false;
        if (redditUserId != null ? !redditUserId.equals(that.redditUserId) : that.redditUserId != null) return false;
        if (redditAuthToken != null ? !redditAuthToken.equals(that.redditAuthToken) : that.redditAuthToken != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (karma != null ? !karma.equals(that.karma) : that.karma != null) return false;
        if (coins != null ? !coins.equals(that.coins) : that.coins != null) return false;
        if (banned != null ? !banned.equals(that.banned) : that.banned != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (deleteDate != null ? !deleteDate.equals(that.deleteDate) : that.deleteDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (joinDate != null ? joinDate.hashCode() : 0);
        result = 31 * result + (redditUserId != null ? redditUserId.hashCode() : 0);
        result = 31 * result + (redditAuthToken != null ? redditAuthToken.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (karma != null ? karma.hashCode() : 0);
        result = 31 * result + (coins != null ? coins.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (deleteDate != null ? deleteDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<BookmarkmapEntity> getBookmarkmapsByUserId() {
        return bookmarkmapsByUserId;
    }

    public void setBookmarkmapsByUserId(Collection<BookmarkmapEntity> bookmarkmapsByUserId) {
        this.bookmarkmapsByUserId = bookmarkmapsByUserId;
    }

    @OneToMany(mappedBy = "usersByAuthorId")
    public Collection<CommentsEntity> getCommentsByUserId() {
        return commentsByUserId;
    }

    public void setCommentsByUserId(Collection<CommentsEntity> commentsByUserId) {
        this.commentsByUserId = commentsByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<MovementlogEntity> getMovementlogsByUserId() {
        return movementlogsByUserId;
    }

    public void setMovementlogsByUserId(Collection<MovementlogEntity> movementlogsByUserId) {
        this.movementlogsByUserId = movementlogsByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<PrivilegemapEntity> getPrivilegemapsByUserId() {
        return privilegemapsByUserId;
    }

    public void setPrivilegemapsByUserId(Collection<PrivilegemapEntity> privilegemapsByUserId) {
        this.privilegemapsByUserId = privilegemapsByUserId;
    }

    @OneToMany(mappedBy = "usersByAuthorId")
    public Collection<SubmissionEntity> getSubmissionsByUserId() {
        return submissionsByUserId;
    }

    public void setSubmissionsByUserId(Collection<SubmissionEntity> submissionsByUserId) {
        this.submissionsByUserId = submissionsByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UserpagemapEntity> getUserpagemapsByUserId() {
        return userpagemapsByUserId;
    }

    public void setUserpagemapsByUserId(Collection<UserpagemapEntity> userpagemapsByUserId) {
        this.userpagemapsByUserId = userpagemapsByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "image_id")
    public ImageEntity getImageByAvatarId() {
        return imageByAvatarId;
    }

    public void setImageByAvatarId(ImageEntity imageByAvatarId) {
        this.imageByAvatarId = imageByAvatarId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UservotemapEntity> getUservotemapsByUserId() {
        return uservotemapsByUserId;
    }

    public void setUservotemapsByUserId(Collection<UservotemapEntity> uservotemapsByUserId) {
        this.uservotemapsByUserId = uservotemapsByUserId;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "privilegemap", schema = "redbook", catalog = "redbook",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "privilege_id")})
    private Set<PrivilegemapEntity> privileges;

    public void getRolesFromPrivilegeMapCollection() {
        this.roles = new ArrayList<>();
        for (PrivilegemapEntity role : privilegemapsByUserId) {
            System.out.println(role.getPrivilegeByPrivilegeId().getPrivilegeName() + " : " + role.getUsersByUserId().getUsername());
            this.roles.add(role.getPrivilegeByPrivilegeId());
        }
    }

    @OneToMany
    public List<PrivilegeEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<PrivilegeEntity> privilegemapsByUserId) {
        getRolesFromPrivilegeMapCollection();
    }

    public boolean hasRole(String roleName) {
        for (PrivilegeEntity role : this.roles)
            if (roleName.equals(role.getPrivilegeName()))
                return true;

        return false;
    }
}
