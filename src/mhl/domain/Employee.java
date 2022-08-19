package mhl.domain;

/**
 * 这是一个javabean 和 employee对应
 *      id INT PRIMARY KEY auto_increment,  员工序号
 * 	    empId VARCHAR(50) UNIQUE NOT NULL DEFAULT '',  员工id
 * 		pwd CHAR(32) NOT NULL DEFAULT '',  密码
 * 		name VARCHAR(50) NOT NULL DEFAULT '', 员工姓名
 * 		job VARCHAR(50) NOT NULL DEFAULT ''  职位
 */

public class Employee {

    private Integer id;
    private String empId;
    private String pwd;
    private String name;
    private String job;

    public Employee() { //无参构造器,底层apache-dbutils反射需要
    }

    public Employee(Integer id, String empId, String pwd, String name, String job) {
        this.id = id;
        this.empId = empId;
        this.pwd = pwd;
        this.name = name;
        this.job = job;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getId() {
        return id;
    }

    public String getEmpId() {
        return empId;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
