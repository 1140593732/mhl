package mhl.service;

import mhl.dao.BillDAO;
import mhl.dao.MultiTableDAO;
import mhl.domain.Bill;
import mhl.domain.DiningTable;
import mhl.domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

public class BillService {

    //定义BillDAO属性
    private BillDAO billDAO = new BillDAO();
    //定义MenuService属性
    private MenuService menuService = new MenuService();
    //定义DiningTableService属性
    private DiningTableService diningTableService = new DiningTableService();

    private MultiTableDAO multiTableDAO = new MultiTableDAO();

    //生成账单
    public boolean orderMenu(int menuId, int nums, int diningTableId) {
        //生成一个账单号, UUID
        String billID = UUID.randomUUID().toString();

        //将账单生成到bill表
        int update = billDAO.update("insert into bill values(null, ?, ?, ?, ?, ?, now(), '未结账')",
                billID, menuId, nums, menuService.getMenuById(menuId).getPrice() * nums, diningTableId);
        if (update <= 0) {
            return false;
        }

        //更新对应餐桌的状态
        return diningTableService.updateDiningTableState(diningTableId, "就餐中");
    }

    //返回所有的账单, 提供给View调用
    public List<Bill> list() {
        return billDAO.queryMulti("select * from bill", Bill.class);
    }

    //返回所有的账单并带有菜品名, 提供给view使用
    public List<MultiTableBean> list2() {
        return multiTableDAO.queryMulti("select bill.*, name " +  //注意语句后有空格, 否则sql语句就出错了
                "from bill, menu " +
                "where bill.menuId = menu.id", MultiTableBean.class);
    }

    //查看某个餐桌是否有未结账的账单
    public boolean hasPayBillByDiningTableId(int diningTableId) {
        Bill bill =
                billDAO.querySingle("select * from bill where diningTableId = ? and state = '未结账' LIMIT 0, 1", Bill.class, diningTableId);
        return bill != null;
    }

    //完成结账(餐桌存在并且有未结账账单)
    public boolean payBill(int diningTableId, String payMode) {

        //1. 修改bill表
        int update =
                billDAO.update("update bill set state = ? where diningTableId = ?", payMode, diningTableId);
        if (update <= 0) {
            return false;
        }

        //2. 修改diningTable表(为了让业务层更清晰,各司其职,应该将该功能放到DiningTableService当中实现)
        if(!diningTableService.updateDiningTableToFree(diningTableId)) {
            return false;
        }
        return true;
    }

}


