package com.wfq.graph.data.bean.douyu;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by weifuqing on 2017/7/3 0003.
 */

public class RoomInfo implements Parcelable {


    /**
     * room_id : 13005
     * room_src : https://rpic.douyucdn.cn/a1707/03/17/13005_170703172541.jpg
     * vertical_src : https://rpic.douyucdn.cn/a1707/03/17/13005_170703172541.jpg
     * isVertical : 0
     * cate_id : 5
     * room_name : 大逆罪人—7.25
     * owner_uid : 314517
     * nickname : 大逆罪人
     * online : 6877
     * url : http://www.douyu.com/dani
     * game_url : http://www.douyu.com/directory/game/WOW
     * game_name : 魔兽世界
     * avatar : https://apic.douyucdn.cn/upload/avatar/000/31/45/17_avatar_big.jpg
     * avatar_mid : https://apic.douyucdn.cn/upload/avatar/000/31/45/17_avatar_middle.jpg
     * avatar_small : https://apic.douyucdn.cn/upload/avatar/000/31/45/17_avatar_small.jpg
     * jumpUrl :
     */

    private String room_id;
    private String room_src;
    private String vertical_src;
    private int isVertical;
    private int cate_id;
    private String room_name;
    private String owner_uid;
    private String nickname;
    private int online;
    private String url;
    private String game_url;
    private String game_name;
    private String avatar;
    private String avatar_mid;
    private String avatar_small;
    private String jumpUrl;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_src() {
        return room_src;
    }

    public void setRoom_src(String room_src) {
        this.room_src = room_src;
    }

    public String getVertical_src() {
        return vertical_src;
    }

    public void setVertical_src(String vertical_src) {
        this.vertical_src = vertical_src;
    }

    public int getIsVertical() {
        return isVertical;
    }

    public void setIsVertical(int isVertical) {
        this.isVertical = isVertical;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getOwner_uid() {
        return owner_uid;
    }

    public void setOwner_uid(String owner_uid) {
        this.owner_uid = owner_uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar_mid() {
        return avatar_mid;
    }

    public void setAvatar_mid(String avatar_mid) {
        this.avatar_mid = avatar_mid;
    }

    public String getAvatar_small() {
        return avatar_small;
    }

    public void setAvatar_small(String avatar_small) {
        this.avatar_small = avatar_small;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.room_id);
        dest.writeString(this.room_src);
        dest.writeString(this.vertical_src);
        dest.writeInt(this.isVertical);
        dest.writeInt(this.cate_id);
        dest.writeString(this.room_name);
        dest.writeString(this.owner_uid);
        dest.writeString(this.nickname);
        dest.writeInt(this.online);
        dest.writeString(this.url);
        dest.writeString(this.game_url);
        dest.writeString(this.game_name);
        dest.writeString(this.avatar);
        dest.writeString(this.avatar_mid);
        dest.writeString(this.avatar_small);
        dest.writeString(this.jumpUrl);
    }

    public RoomInfo() {
    }

    protected RoomInfo(Parcel in) {
        this.room_id = in.readString();
        this.room_src = in.readString();
        this.vertical_src = in.readString();
        this.isVertical = in.readInt();
        this.cate_id = in.readInt();
        this.room_name = in.readString();
        this.owner_uid = in.readString();
        this.nickname = in.readString();
        this.online = in.readInt();
        this.url = in.readString();
        this.game_url = in.readString();
        this.game_name = in.readString();
        this.avatar = in.readString();
        this.avatar_mid = in.readString();
        this.avatar_small = in.readString();
        this.jumpUrl = in.readString();
    }

    public static final Parcelable.Creator<RoomInfo> CREATOR = new Parcelable.Creator<RoomInfo>() {
        @Override
        public RoomInfo createFromParcel(Parcel source) {
            return new RoomInfo(source);
        }

        @Override
        public RoomInfo[] newArray(int size) {
            return new RoomInfo[size];
        }
    };
}
