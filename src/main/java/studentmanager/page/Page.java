package studentmanager.page;

import org.springframework.stereotype.Component;

@Component
public class Page {
    private int page;//当前页码

    private int offset;//当前查询的条数
    private int rows;//每页显示的数量

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }



    public int getOffset() {
       offset = (page -1)*rows;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = (page -1)*rows;
    }
}
