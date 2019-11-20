

import main.annotation.MergeAlias;

public class Pojo2 {

    @MergeAlias(value = "list", convert = test.ConvertImpl.class)
    private String list;

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}



