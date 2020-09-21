package Util;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import DataModels.Item;

public class DiffUtilCallbacks extends DiffUtil.Callback {

    private List<Item> oldList;
    private List<Item> newList;

    public DiffUtilCallbacks(List<Item> oldList, List<Item> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
