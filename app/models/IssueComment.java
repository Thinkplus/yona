/**
 * @author Taehyun Park
 */

package models;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import utils.JodaDateUtil;

@Entity
public class IssueComment extends Model {
    private static final long serialVersionUID = 1L;
    private static Finder<Long, IssueComment> find = new Finder<Long, IssueComment>(
            Long.class, IssueComment.class);

    @Id
    public Long id;

    @ManyToOne
    @Constraints.Required
    public Issue issue;

    @Constraints.Required
    public String contents;

    @Constraints.Required
    public Date date;

     
    public Long authorId;
    public String filePath;

    public IssueComment() {
        date = JodaDateUtil.today();
    }

    public static List<IssueComment> findCommentsByIssueId(Long issueId) {
        return find.where().eq("issue.id", issueId).findList();
    }

    public static Long create(IssueComment issueComment) {
        issueComment.save();
        return issueComment.id;
    }

    public static void deleteByIssueId(Long issueId) {
        List<IssueComment> targets = IssueComment.find.where()
                .eq("issue.id", "" + issueId).findList();

        Iterator<IssueComment> target = targets.iterator();
        while (target.hasNext()) {
            IssueComment issueComment = target.next();
            issueComment.delete();
        }
    }
}
