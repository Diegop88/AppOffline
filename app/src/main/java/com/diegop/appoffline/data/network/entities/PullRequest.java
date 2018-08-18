
package com.diegop.appoffline.data.network.entities;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class PullRequest {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("html_url")
    @Expose
    public String htmlUrl;
    @SerializedName("diff_url")
    @Expose
    public String diffUrl;
    @SerializedName("patch_url")
    @Expose
    public String patchUrl;

}
