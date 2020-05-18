package top.irises.pbox3.room.domain;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Cont implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int cid;
    private String name; //姓名
    private String email; //电子邮件
    private String tel; //电话
    private String ps; //备注
    private int level; //亲密度关系级别
    private long createTime;
    private long modifyTime;
    private boolean trashFlag;
    private long trashTime;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isTrashFlag() {
        return trashFlag;
    }

    public void setTrashFlag(boolean trashFlag) {
        this.trashFlag = trashFlag;
    }

    public long getTrashTime() {
        return trashTime;
    }

    public void setTrashTime(long trashTime) {
        this.trashTime = trashTime;
    }

    @Override
    public String toString() {
        return "Cont{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", ps='" + ps + '\'' +
                ", level=" + level +
                ", createTime=" + createTime +
                ", trashFlag=" + trashFlag +
                ", trashTime=" + trashTime +
                '}';
    }
}
