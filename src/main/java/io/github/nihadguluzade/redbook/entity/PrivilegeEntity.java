package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "privilege", schema = "redbook", catalog = "redbook")
public class PrivilegeEntity {
    private int privilegeId;
    private String privilegeName;
    private String privilegeDescription;
    private Collection<PrivilegemapEntity> privilegemapsByPrivilegeId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id")
    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Basic
    @Column(name = "privilege_name")
    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    @Basic
    @Column(name = "privilege_description")
    public String getPrivilegeDescription() {
        return privilegeDescription;
    }

    public void setPrivilegeDescription(String privilegeDescription) {
        this.privilegeDescription = privilegeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivilegeEntity that = (PrivilegeEntity) o;

        if (privilegeId != that.privilegeId) return false;
        if (privilegeName != null ? !privilegeName.equals(that.privilegeName) : that.privilegeName != null)
            return false;
        if (privilegeDescription != null ? !privilegeDescription.equals(that.privilegeDescription) : that.privilegeDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = privilegeId;
        result = 31 * result + (privilegeName != null ? privilegeName.hashCode() : 0);
        result = 31 * result + (privilegeDescription != null ? privilegeDescription.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "privilegeByPrivilegeId")
    public Collection<PrivilegemapEntity> getPrivilegemapsByPrivilegeId() {
        return privilegemapsByPrivilegeId;
    }

    public void setPrivilegemapsByPrivilegeId(Collection<PrivilegemapEntity> privilegemapsByPrivilegeId) {
        this.privilegemapsByPrivilegeId = privilegemapsByPrivilegeId;
    }
}
