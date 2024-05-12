package com.gav.docsapp.lists;

import com.gav.docsapp.services.sortTypes.DocType;

import java.util.List;

public abstract class BaseList<T> {
    protected List<T> _list;

    public BaseList(List<T> list){
        _list= list;
    }

    public abstract BaseList<T> sort(DocType docType);

    public abstract BaseList<T> filter(DocType docType, String value);
}
