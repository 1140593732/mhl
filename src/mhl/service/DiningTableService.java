package mhl.service;

import mhl.dao.DiningTableDAO;
import mhl.domain.DiningTable;

import javax.accessibility.AccessibleKeyBinding;
import java.util.List;

public class DiningTableService {
    //定义一个DiningtableDAO对象
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    //返回所有餐桌信息
    public List<DiningTable> list() {
        return diningTableDAO.queryMulti("select id, state from diningTable", DiningTable.class);
    }

    //根据id, 查询对应的餐桌DiningTable 对象
    public DiningTable getDiningTableById(int id) {
        return diningTableDAO.querySingle("select * from diningTable where id = ?", DiningTable.class, id);
    }

    //如果餐桌可预订, 调用方法对其进行更新(包括预订人的名字和电话)
    public boolean alterDiningTable(int id, String orderName, String orderTel) {
        int update =
                diningTableDAO.update("update diningTable set state = '已经预定', orderName = ?, orderTel = ? where id = ?", orderName, orderTel, id);
        return update > 0;
    }

    //更新餐桌状态
    public boolean updateDiningTableState(int id, String state) {
        int update = diningTableDAO.update("update diningTable set state=? where id=?", state, id);
        return update > 0;
    }

    //将指定餐桌设置为空闲状态
    public boolean updateDiningTableToFree(int id) {
        int update =
                diningTableDAO.update("update diningTable set state='空', orderName = '', orderTel = '' where id=?", id);
        return update > 0;
    }
}
