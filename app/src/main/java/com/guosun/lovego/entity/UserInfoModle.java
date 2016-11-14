package com.guosun.lovego.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoModle extends ResultModle {
    public String sn;

    private static final long serialVersionUID = 1L;

    public UserInfoModle(JSONObject jsn) throws JSONException {
        super(jsn);
        if (jsn.has("sn"))
            this.sn = jsn.getString("sn");
    }

}