package mhl.service;

import mhl.dao.EmployeeDAO;
import mhl.domain.Employee;

/**
 * 完成对employee表的各种操作(通过调用EmployeeDAO对象完成)
 */
public class EmployeeService {

    //定义一个EmployeeDAO对象
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    //方法, 根据empId 和 pwd 返回一个Employee对象
    public Employee getEmployeeByIdAndPwd(String empId, String pwd) {
        return employeeDAO.querySingle("select * from employee where empId = ? and pwd = md5(?)", Employee.class, empId, pwd);
    }
}
