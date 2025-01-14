package com.skirlez.fabricatedexchange.util.config;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.skirlez.fabricatedexchange.FabricatedExchange;
import com.skirlez.fabricatedexchange.util.DataFile;
import com.skirlez.fabricatedexchange.util.GeneralUtil;

public class NbtItemsFile extends DataFile<HashMap<String, List<String>>>  {

    public NbtItemsFile(Type type, String name) {
        super(type, name);
    }

    HashMap<String, String> itemsToTag = new HashMap<String, String>();


    @Override
    protected void process() {
        itemsToTag.clear();
        Iterator<String> iterator = value.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (!key.startsWith("#"))
                continue;
            key = key.substring(1);
            // tag found
            String[] items = GeneralUtil.getItemStringsFromTagString(key);
            for (int i = 0; i < items.length; i++) {
                itemsToTag.put(items[i], key);
            }
        }
    }

    public boolean hasItem(String idName) {
        if (value.containsKey(idName))
            return true;
        String tag = itemsToTag.get(idName);
        if (tag == null)
            return false;
        return value.containsKey("#" + tag);
    }

    public List<String> getAllowedKeys(String idName) {
        if (value.containsKey(idName))
            return value.get(idName);
        else {
            String tag = itemsToTag.get(idName);
            if (tag == null) {
                FabricatedExchange.LOGGER.error("Item " + idName + " does not have an entry in nbt_items.json!");
                return null;
            }
            return value.get("#" + tag);
        }
    }
    
}
