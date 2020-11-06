package io.github.nihadguluzade.redbook.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "reaction", schema = "redbook", catalog = "redbook")
public class ReactionEntity {
    private int reactionId;
    private String reactionName;
    private Collection<UservotemapEntity> uservotemapsByReactionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    public int getReactionId() {
        return reactionId;
    }

    public void setReactionId(int reactionId) {
        this.reactionId = reactionId;
    }

    @Basic
    @Column(name = "reaction_name")
    public String getReactionName() {
        return reactionName;
    }

    public void setReactionName(String reactionName) {
        this.reactionName = reactionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReactionEntity that = (ReactionEntity) o;

        if (reactionId != that.reactionId) return false;
        if (reactionName != null ? !reactionName.equals(that.reactionName) : that.reactionName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reactionId;
        result = 31 * result + (reactionName != null ? reactionName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reactionByReactionId")
    public Collection<UservotemapEntity> getUservotemapsByReactionId() {
        return uservotemapsByReactionId;
    }

    public void setUservotemapsByReactionId(Collection<UservotemapEntity> uservotemapsByReactionId) {
        this.uservotemapsByReactionId = uservotemapsByReactionId;
    }
}
