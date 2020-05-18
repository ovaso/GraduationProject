package top.irises.pbox3.room.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


/**
 * 时间日期类型全部改成unix时间戳,避免转换问题
 */
@Entity
public class Assign implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int aid; //唯一id


    private String title; //标题
    private String content; //内容
    private long createTime;  //创建时间

    private boolean doneFlag; //完成标志

    private boolean autoFlag;  //自动删除标志
    private long startTime; //开始时间
    private long endTime; //结束时间

    private boolean trashFlag;  //垃圾标志
    private long trashTime; //删除时间

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isDoneFlag() {
        return doneFlag;
    }

    public void setDoneFlag(boolean doneFlag) {
        this.doneFlag = doneFlag;
    }

    public boolean isAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(boolean autoFlag) {
        this.autoFlag = autoFlag;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
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
        return "Assign{" +
                "aid=" + aid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", doneFlag=" + doneFlag +
                ", autoFlag=" + autoFlag +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", trashFlag=" + trashFlag +
                ", trashTime=" + trashTime +
                '}';
    }
}
