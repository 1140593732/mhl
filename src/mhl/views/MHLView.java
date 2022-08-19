package mhl.views;

/**
 * description: View调用service实现各种服务功能, 而各种service服务调用各种DAO来对domain里的数据表进行操作
 */

import mhl.domain.*;
import mhl.service.BillService;
import mhl.service.DiningTableService;
import mhl.service.EmployeeService;
import mhl.service.MenuService;
import mhl.utils.Utility;

import java.util.List;

public class MHLView {


    private boolean exit = false; //控制是否退出菜单
    private String key = ""; //接收用户的输入
    //定义EmployeeService 属性
    private EmployeeService employeeService = new EmployeeService();
    //定义DiningTableService 属性
    private DiningTableService diningTableService = new DiningTableService();
    //定义MenuService属性
    private MenuService menuService = new MenuService();
    //定义BillService属性
    private BillService billService = new BillService();

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }


    //显示主菜单
    public void mainMenu() {
        while(!exit) {
            System.out.println("===============满汉楼===============");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.print("请输入你的选择:");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.print("请输入你的员工号:");
                    String empId = Utility.readString(50);
                    System.out.print("请输入你的密码:");
                    String pwd = Utility.readString(50);
                    Employee employee = employeeService.getEmployeeByIdAndPwd(empId, pwd);
                    if (employee != null) {
                        System.out.println("===============用户[" + employee.getName() +"]登陆成功===============\n");
                        //显示二级菜单
                        while (!exit) {
                            System.out.println("\n===============请选择服务===============");
                            System.out.println("\t\t 1 显示餐桌状态");
                            System.out.println("\t\t 2 预定餐桌");
                            System.out.println("\t\t 3 显示所有菜品");
                            System.out.println("\t\t 4 点餐服务");
                            System.out.println("\t\t 5 查看账单");
                            System.out.println("\t\t 6 结账");
                            System.out.println("\t\t 7 退出满汉楼系统");
                            System.out.print("请输入你要选择的服务:");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    showDiningTableState(); //显示餐桌状态
                                    break;
                                case "2":
                                    orderDiningTable(); //预订餐桌
                                    break;
                                case "3":
                                    showMenu();
                                    break;
                                case "4":
                                    orderMenu();
                                    break;
                                case "5":
                                    showBill();
                                    break;
                                case "6":
                                    payBill();
                                    break;
                                case "7":
                                    exit = true;
                                    break;
                                default:
                                    System.out.println("你的输入有误,请重新输入!");
                                    break;
                            }
                        }

                    } else {
                        System.out.println("===============登录失败===============\n");
                    }
                    break;
                case "2":
                    exit = true;
                    break;
                default:
                    System.out.print("你的输入有误,请重新输入:");
            }
        }
        System.out.println("你已退出满汉楼系统");
    }

    //显示餐桌状态服务
    public void showDiningTableState() {
        List<DiningTable> list = diningTableService.list();
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for (DiningTable dt : list) {
            System.out.println(dt);
        }
        System.out.println("=============显示完毕=============");
    }

    //订座服务
    public void orderDiningTable() {
        System.out.println("=============预定餐桌=============");
        System.out.print("请选择要预定的餐桌编号(-1退出):");
        int orderId = Utility.readInt();
        if (orderId == -1) {
            System.out.println("=============已取消=============");
            return;
        }
        //该方法得到Y或者N
        char c = Utility.readConfirmSelection();
        if (c == 'Y') {  //预定
            //根据orderId返回对应DingTable对象
            DiningTable diningTable = diningTableService.getDiningTableById(orderId);
            if (diningTable == null) {
                System.out.println("=============查询不到该餐桌!=============");
                return;
            }
            //判断该餐桌状态是否为空
            if (!"空".equals(diningTable.getState())) {
                System.out.println("=============该餐桌已被预订或就餐中=============");
                return;
            }
            //进行预订
            System.out.print("请输入预订人姓名:");
            String orderName = Utility.readString(50);
            System.out.print("请输入预订人联系电话:");
            String orderTel = Utility.readString(50);
            if (diningTableService.alterDiningTable(orderId, orderName, orderTel)) {
                System.out.println("=============预订成功=============");
            } else {
                System.out.println("=============预订失败=============");
            }
        } else {
            System.out.println("=============已取消=============");
        }
    }

    //显示所有菜品服务
    public void showMenu() {
        List<Menu> list = menuService.list();
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        for (Menu menu : list) {
            System.out.println(menu);
        }
        System.out.println("=============显示完毕=============");
    }

    //点餐服务
    public void orderMenu() {
        System.out.println("=============点餐服务=============");
        System.out.print("请输入点餐的桌号(-1退出):");
        int orderDiningTableId = Utility.readInt();
        if (orderDiningTableId == -1) {
            System.out.println("=============点餐取消=============");
            return;
        }
        System.out.print("请输入要点的菜品号(-1退出):");
        int orderMenuId = Utility.readInt();
        if (orderMenuId == -1) {
            System.out.println("=============点餐取消=============");
            return;
        }
        System.out.print("请输入菜品的数量(-1退出):");
        int orderNums = Utility.readInt();
        if (orderNums == -1) {
            System.out.println("=============点餐取消=============");
            return;
        }

        //验证餐桌号是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(orderDiningTableId);
        if (diningTable == null) {
            System.out.println("=============餐桌号不存在=============");
            return;
        }

        //验证菜品号是否存在
        Menu menu = menuService.getMenuById(orderMenuId);
        if (menu == null) {
            System.out.println("=============菜品号不存在=============");
            return;
        }

        //点餐
        if (billService.orderMenu(orderMenuId, orderNums, orderDiningTableId)) {
            System.out.println("=============点餐成功=============");
        } else {
            System.out.println("=============点餐失败=============");
        }
    }

    //显示所有账单
    public void showBill() {
//        List<Bill> bills = billService.list();
//        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t状态");
//        for (Bill bill : bills) {
//            System.out.println(bill);
//        }
//        System.out.println("=============账单查询完毕=============");
        List<MultiTableBean> multiTableBeans = billService.list2();
        System.out.println("\n编号\t\t菜品号\t\t菜品名\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t状态");
        for (MultiTableBean multiTableBean : multiTableBeans) {
            System.out.println(multiTableBean);
        }
        System.out.println("=============账单查询完毕=============");
    }

    //结账服务
    public void payBill() {
        System.out.println("=============结账服务=============");
        System.out.print("请选择要结账的餐桌号(-1退出):");
        int diningTableId = Utility.readInt();
        if (diningTableId == -1) {
            System.out.println("=============取消结账=============");
            return;
        }
        //验证该餐桌是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(diningTableId);
        if (diningTable == null) {
            System.out.println("=============不存在此餐桌=============");
            return;
        }
        //验证该餐桌是否有未结账的账单
        if (!billService.hasPayBillByDiningTableId(diningTableId)) {
            System.out.println("=============该餐桌无未结账账单=============");
            return;
        }
        System.out.print("结账方式(现金/支付宝/微信)回车表示退出:");
        String payMode = Utility.readString(50, "");
        if (payMode.equals("")) {
            System.out.println("=============取消结账=============");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {   //结账
            if (!billService.payBill(diningTableId, payMode)) {
                System.out.println("=============结账失败=============");
                return;
            }
            System.out.println("=============结账完成=============");
        } else {
            System.out.println("=============取消结账=============");
        }
    }
}
