package com.datemodule.Entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.datemodule.greendao.DaoSession;
import com.datemodule.greendao.StudentDao;
import com.datemodule.greendao.ParentDao;

/**
 * @author ckckck   2019/1/19
 * life is short ,bugs are too many!
 */
@Entity
public class Parent {
    @Id
    Long id;
    Long studentid;
    String name;
    @ToOne(joinProperty = "studentid")
    Student student;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 936059064)
    private transient ParentDao myDao;
    @Generated(hash = 334997515)
    public Parent(Long id, Long studentid, String name) {
        this.id = id;
        this.studentid = studentid;
        this.name = name;
    }
    @Generated(hash = 981245553)
    public Parent() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStudentid() {
        return this.studentid;
    }
    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Generated(hash = 79695740)
    private transient Long student__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 157986435)
    public Student getStudent() {
        Long __key = this.studentid;
        if (student__resolvedKey == null || !student__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StudentDao targetDao = daoSession.getStudentDao();
            Student studentNew = targetDao.load(__key);
            synchronized (this) {
                student = studentNew;
                student__resolvedKey = __key;
            }
        }
        return student;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 923646262)
    public void setStudent(Student student) {
        synchronized (this) {
            this.student = student;
            studentid = student == null ? null : student.getId();
            student__resolvedKey = studentid;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1321580708)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getParentDao() : null;
    }
}
