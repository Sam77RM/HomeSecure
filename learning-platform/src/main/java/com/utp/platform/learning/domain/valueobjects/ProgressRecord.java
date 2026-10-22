package com.utp.platform.learning.domain.valueobjects;

import com.utp.platform.learning.domain.entities.ProgressRecordItem;

import java.util.ArrayList;
import java.util.List;

public class ProgressRecord {

private List<ProgressRecordItem> progressRecordItems;


    public ProgressRecord() {
     progressRecordItems = new ArrayList<>();
    }
    public void setProgressRecordItems(List<ProgressRecordItem>
                                       progressRecordItems){
        this.progressRecordItems = progressRecordItems==null? new ArrayList<>():
                progressRecordItems;
    }
}
