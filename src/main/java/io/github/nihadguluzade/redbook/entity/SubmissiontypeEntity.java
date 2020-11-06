package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "submissiontype", schema = "redbook", catalog = "redbook")
public class SubmissiontypeEntity {
    private int submissiontypeId;
    private String submissiontypeName;
    private Collection<SubmissionEntity> submissionsBySubmissiontypeId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submissiontype_id")
    public int getSubmissiontypeId() {
        return submissiontypeId;
    }

    public void setSubmissiontypeId(int submissiontypeId) {
        this.submissiontypeId = submissiontypeId;
    }

    @Basic
    @Column(name = "submissiontype_name")
    public String getSubmissiontypeName() {
        return submissiontypeName;
    }

    public void setSubmissiontypeName(String submissiontypeName) {
        this.submissiontypeName = submissiontypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubmissiontypeEntity that = (SubmissiontypeEntity) o;

        if (submissiontypeId != that.submissiontypeId) return false;
        if (submissiontypeName != null ? !submissiontypeName.equals(that.submissiontypeName) : that.submissiontypeName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = submissiontypeId;
        result = 31 * result + (submissiontypeName != null ? submissiontypeName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "submissiontypeBySubmissionTypeId")
    public Collection<SubmissionEntity> getSubmissionsBySubmissiontypeId() {
        return submissionsBySubmissiontypeId;
    }

    public void setSubmissionsBySubmissiontypeId(Collection<SubmissionEntity> submissionsBySubmissiontypeId) {
        this.submissionsBySubmissiontypeId = submissionsBySubmissiontypeId;
    }
}
