package com.msb.hjycommunity.system.domain.vo;

import java.util.List;

/**
 * 路由配置信息VO
 */
public class RouterVo {
    /**
     * 路由名字
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private boolean hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    /**
     * 其他元素
     */
    private MetaVo meta;

    /**
     * 子路由
     */
    private List<RouterVo> children;

    public RouterVo() {
    }

    public RouterVo(String name, String path, boolean hidden, String redirect, String component, Boolean alwaysShow, MetaVo meta, List<RouterVo> children) {
        this.name = name;
        this.path = path;
        this.hidden = hidden;
        this.redirect = redirect;
        this.component = component;
        this.alwaysShow = alwaysShow;
        this.meta = meta;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public MetaVo getMeta() {
        return meta;
    }

    public void setMeta(MetaVo meta) {
        this.meta = meta;
    }

    public List<RouterVo> getChildren() {
        return children;
    }

    public void setChildren(List<RouterVo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "RouterVo{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", hidden=" + hidden +
                ", redirect='" + redirect + '\'' +
                ", component='" + component + '\'' +
                ", alwaysShow=" + alwaysShow +
                ", meta=" + meta +
                ", children=" + children +
                '}';
    }
}
