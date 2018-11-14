package com.xiangri.dongdong.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HistoryEntity   {
    @Id(autoincrement = true)
    private Long id;
    private String history;
    private String tpye;
    @Generated(hash = 1210713338)
    public HistoryEntity(Long id, String history, String tpye) {
        this.id = id;
        this.history = history;
        this.tpye = tpye;
    }
    @Generated(hash = 1235354573)
    public HistoryEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHistory() {
        return this.history;
    }
    public void setHistory(String history) {
        this.history = history;
    }
    public String getTpye() {
        return this.tpye;
    }
    public void setTpye(String tpye) {
        this.tpye = tpye;
    }

   

}
