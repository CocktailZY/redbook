package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "page", schema = "redbook", catalog = "redbook")
public class PageEntity {
    private int pageId;
    private String pageName;
    private String pageDescription;
    private Boolean nsfw;
    private Integer iconId = 1;
    private Date openDate;
    private Boolean confidential;
    private Boolean banned;
    private Boolean quaratined;
    private ImageEntity imageByBannerId;
    private ImageEntity imageByIconId;
    private Collection<SubmissionEntity> submissionsByPageId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_id")
    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Basic
    @Column(name = "page_name")
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @Basic
    @Column(name = "page_description")
    public String getPageDescription() {
        return pageDescription;
    }

    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

    @Basic
    @Column(name = "nsfw")
    public Boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    @Basic
    @Column(name = "icon_id", insertable = false, updatable = false, nullable = false)
    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    @Basic
    @Column(name = "open_date")
    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    @Basic
    @Column(name = "confidential")
    public Boolean getConfidential() {
        return confidential;
    }

    public void setConfidential(Boolean confidential) {
        this.confidential = confidential;
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
    @Column(name = "quaratined")
    public Boolean getQuaratined() {
        return quaratined;
    }

    public void setQuaratined(Boolean quaratined) {
        this.quaratined = quaratined;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageEntity that = (PageEntity) o;

        if (pageId != that.pageId) return false;
        if (pageName != null ? !pageName.equals(that.pageName) : that.pageName != null) return false;
        if (pageDescription != null ? !pageDescription.equals(that.pageDescription) : that.pageDescription != null)
            return false;
        if (nsfw != null ? !nsfw.equals(that.nsfw) : that.nsfw != null) return false;
        if (openDate != null ? !openDate.equals(that.openDate) : that.openDate != null) return false;
        if (confidential != null ? !confidential.equals(that.confidential) : that.confidential != null) return false;
        if (banned != null ? !banned.equals(that.banned) : that.banned != null) return false;
        if (quaratined != null ? !quaratined.equals(that.quaratined) : that.quaratined != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pageId;
        result = 31 * result + (pageName != null ? pageName.hashCode() : 0);
        result = 31 * result + (pageDescription != null ? pageDescription.hashCode() : 0);
        result = 31 * result + (nsfw != null ? nsfw.hashCode() : 0);
        result = 31 * result + (openDate != null ? openDate.hashCode() : 0);
        result = 31 * result + (confidential != null ? confidential.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        result = 31 * result + (quaratined != null ? quaratined.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "banner_id", referencedColumnName = "image_id")
    public ImageEntity getImageByBannerId() {
        return imageByBannerId;
    }

    public void setImageByBannerId(ImageEntity imageByBannerId) {
        this.imageByBannerId = imageByBannerId;
    }

    @ManyToOne
    @JoinColumn(name = "icon_id", referencedColumnName = "image_id")
    public ImageEntity getImageByIconId() {
        return imageByIconId;
    }

    public void setImageByIconId(ImageEntity imageByIconId) {
        this.imageByIconId = imageByIconId;
    }

    @OneToMany(mappedBy = "pageByPageId")
    public Collection<SubmissionEntity> getSubmissionsByPageId() {
        return submissionsByPageId;
    }

    public void setSubmissionsByPageId(Collection<SubmissionEntity> submissionsByPageId) {
        this.submissionsByPageId = submissionsByPageId;
    }
}
