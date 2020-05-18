package top.irises.pbox3.room.domain;


import androidx.room.Entity;

import java.math.BigDecimal;

@Entity
public class BaseAttr{
    /**
     * SHP记录基本属性
     * 本域只是一个映射对象
     */
    private static String name;
    private static BigDecimal salary;
    private static int height;
    private static int weight;
    private static String sex;

    private static BaseAttr INSTANCE;

    public synchronized BaseAttr getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new BaseAttr();
        }
        return INSTANCE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
