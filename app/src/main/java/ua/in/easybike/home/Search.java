package ua.in.easybike.home;

import ir.mirrajabi.searchdialog.core.Searchable;



public class Search implements Searchable {

    private String mTitle;

    public Search(String mTitle){
        this.mTitle = mTitle;
    }

    public void setmTitle(String mTitle){
        this.mTitle = mTitle;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }
}
