package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "movementlog", schema = "redbook", catalog = "redbook")
public class MovementlogEntity {
    private int movementlogId;
    private Timestamp datetime;
    private String act;
    private UsersEntity usersByUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movementlog_id")
    public int getMovementlogId() {
        return movementlogId;
    }

    public void setMovementlogId(int movementlogId) {
        this.movementlogId = movementlogId;
    }

    @Basic
    @Column(name = "datetime")
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "act")
    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovementlogEntity that = (MovementlogEntity) o;

        if (movementlogId != that.movementlogId) return false;
        if (datetime != null ? !datetime.equals(that.datetime) : that.datetime != null) return false;
        if (act != null ? !act.equals(that.act) : that.act != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = movementlogId;
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + (act != null ? act.hashCode() : 0);
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
