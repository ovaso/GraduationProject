package top.irises.pbox3.room.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import top.irises.pbox3.room.db.PboxDb;

@Entity
public class Pay{

    @PrimaryKey(autoGenerate = true)
    private int pid;
    /**
     * 收入 和 支出 +-
     */
    private double expend;
    /**
     * 小计
     */
    private String ps;
    private long createTime;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public double getExpend() {
        return expend;
    }

    public void setExpend(double expend) {
        this.expend = expend;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
