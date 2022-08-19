package mhl.domain;

/**
 *      id INT PRIMARY KEY auto_increment,
 * 		state VARCHAR(20) NOT NULL DEFAULT '',  餐桌状态
 * 		orderName VARCHAR(50) NOT NULL DEFAULT '', 预订人的姓名
 * 		oderTel VARCHAR(20) NOT NULL DEFAULT '', 预订人电话
 */

public class DiningTable {

    private Integer id;
    private String state;
    private String oderName;
    private String oderTel;

    public DiningTable() {
    }

    public DiningTable(Integer id, String state, String oderName, String oderTel) {
        this.id = id;
        this.state = state;
        this.oderName = oderName;
        this.oderTel = oderTel;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getOderName() {
        return oderName;
    }

    public String getOderTel() {
        return oderTel;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setOderName(String oderName) {
        this.oderName = oderName;
    }

    public void setOderTel(String oderTel) {
        this.oderTel = oderTel;
    }

    @Override
    public String toString() {
        return id + "\t\t\t" + state;
    }
}
