package com.guosun.lovego.entity;

/**
 * Created by liuguosheng on 2016/10/27.
 */
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ResultModle implements Serializable{


    private static final long serialVersionUID = 1L;
    public String returnCode ="";
    public String returnMessage="";

    public ResultModle(JSONObject jsn) throws JSONException {

        if(jsn.has("returnCode"))
            this.returnCode = jsn.getString("returnCode");
        if(jsn.has("returnMessage"))
            this.returnMessage = jsn.getString("returnMessage");
    }
}
