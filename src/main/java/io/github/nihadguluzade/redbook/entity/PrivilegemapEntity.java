package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;

@Entity
@Table(name = "privilegemap", schema = "redbook", catalog = "redbook")
public class PrivilegemapEntity {
    private int privilegeMapId;
    private UsersEntity usersByUserId;
    private PrivilegeEntity privilegeByPrivilegeId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_map_id")
    public int getPrivilegeMapId() {
        return privilegeMapId;
    }

    public void setPrivilegeMapId(int privilegeMapId) {
        this.privilegeMapId = privilegeMapId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivilegemapEntity that = (PrivilegemapEntity) o;

        return privilegeMapId == that.privilegeMapId;
    }

    @Override
    public int hashCode() {
        return privilegeMapId;
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
    @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id")
    public PrivilegeEntity getPrivilegeByPrivilegeId() {
        return privilegeByPrivilegeId;
    }

    public void setPrivilegeByPrivilegeId(PrivilegeEntity privilegeByPrivilegeId) {
        this.privilegeByPrivilegeId = privilegeByPrivilegeId;
    }
}
