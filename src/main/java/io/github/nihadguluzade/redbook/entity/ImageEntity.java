package io.github.nihadguluzade.redbook.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image", schema = "redbook", catalog = "redbook")
public class ImageEntity {
    private int imageId;
    private String imagePath;
    private Collection<PageEntity> pagesByImageId;
    private Collection<PageEntity> pagesByImageId_0;
    private Collection<SubmissionEntity> submissionsByImageId;
    private Collection<UsersEntity> usersByImageId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "image_path")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageEntity that = (ImageEntity) o;

        if (imageId != that.imageId) return false;
        if (imagePath != null ? !imagePath.equals(that.imagePath) : that.imagePath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imageId;
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "imageByBannerId")
    public Collection<PageEntity> getPagesByImageId() {
        return pagesByImageId;
    }

    public void setPagesByImageId(Collection<PageEntity> pagesByImageId) {
        this.pagesByImageId = pagesByImageId;
    }

    @OneToMany(mappedBy = "imageByIconId")
    public Collection<PageEntity> getPagesByImageId_0() {
        return pagesByImageId_0;
    }

    public void setPagesByImageId_0(Collection<PageEntity> pagesByImageId_0) {
        this.pagesByImageId_0 = pagesByImageId_0;
    }

    @OneToMany(mappedBy = "imageByMedia")
    public Collection<SubmissionEntity> getSubmissionsByImageId() {
        return submissionsByImageId;
    }

    public void setSubmissionsByImageId(Collection<SubmissionEntity> submissionsByImageId) {
        this.submissionsByImageId = submissionsByImageId;
    }

    @OneToMany(mappedBy = "imageByAvatarId")
    public Collection<UsersEntity> getUsersByImageId() {
        return usersByImageId;
    }

    public void setUsersByImageId(Collection<UsersEntity> usersByImageId) {
        this.usersByImageId = usersByImageId;
    }
}
