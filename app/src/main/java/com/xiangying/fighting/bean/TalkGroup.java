package com.xiangying.fighting.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/26.
 */
public class TalkGroup {
    public static final int ZHANYOU=1;//战友
    public static final int JUNTUAN=2;//军团
    public static final int FENDUI=3;//分队

    private String  name,state;
    private int totalCount,currentCount,type;

    public TalkGroup(String name, String state) {
        this.name = name;
        this.state=state;
    }

    public TalkGroup(String name, String state, int type) {
        this.state = state;
        this.name = name;
        this.type = type;
    }

    public static ArrayList<TalkGroup>  getTalkGroups(){
        ArrayList<TalkGroup>  talkGroups=new ArrayList<>();
        TalkGroup talkGroup=new TalkGroup("战友","4/10", TalkGroup.ZHANYOU);
        talkGroups.add(talkGroup);
        talkGroup=new TalkGroup("军团","10/20", TalkGroup.JUNTUAN);
        talkGroups.add(talkGroup);
        talkGroup=new TalkGroup("创建军团","");
        talkGroups.add(talkGroup);
        talkGroup=new TalkGroup("添加分队","");
        talkGroups.add(talkGroup);
        return talkGroups;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }
}
