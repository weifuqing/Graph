package com.wfq.graph.data.bean.douyu;

/**
 * Created by weifuqing on 2017/11/13.
 */

public class Game {


    /**
     * game_url : http://www.douyu.com/directory/game/DOTA2
     * game_name : DOTA2
     * game_src : https://staticlive.douyucdn.cn/upload/game_cate/dfb7341f7c3119fdc2e8cf0d8bf2592c.jpg
     * cate_id : 3
     * game_icon : https://staticlive.douyucdn.cn/upload/game_cate/a887fa9dc9d6901b5fd5c86c8e169436.jpg
     * short_name : DOTA2
     */

    private String game_url;
    private String game_name;
    private String game_src;
    private String cate_id;
    private String game_icon;
    private String short_name;

    public String getGame_url() {
        return game_url;
    }

    public void setGame_url(String game_url) {
        this.game_url = game_url;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getGame_src() {
        return game_src;
    }

    public void setGame_src(String game_src) {
        this.game_src = game_src;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getGame_icon() {
        return game_icon;
    }

    public void setGame_icon(String game_icon) {
        this.game_icon = game_icon;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
}
