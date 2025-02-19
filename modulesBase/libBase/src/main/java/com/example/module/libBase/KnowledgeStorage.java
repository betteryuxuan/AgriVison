package com.example.module.libBase;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.module.libBase.bean.Knowledge;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeStorage {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public KnowledgeStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("knowledge", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    // 将 Knowledge 列表存储到 SharedPreferences
    public void saveKnowledgeList(List<Knowledge> knowledgeList) {
        // 将 List 转换为 JSON 字符串
        String json = gson.toJson(knowledgeList);

        // 存储 JSON 字符串
        editor.putString("knowledge_list", json);
        editor.apply();
    }

    // 从 SharedPreferences 中读取 Knowledge 列表
    public List<Knowledge> loadKnowledgeList() {
        // 获取存储的 JSON 字符串
        String json = sharedPreferences.getString("knowledge_list", null);

        if (json != null) {
            // 将 JSON 字符串转换为 Knowledge 列表
            Type type = new TypeToken<List<Knowledge>>() {}.getType();
            return gson.fromJson(json, type);
        }

        return null;
    }

    // 更新 Knowledge 列表（不覆盖原数据）
    public void updateKnowledgeList(Knowledge newKnowledge) {
        // 先加载已有的列表
        List<Knowledge> currentList = loadKnowledgeList();
        if (currentList == null) {
            currentList = new ArrayList<>(); // 如果没有数据，则初始化一个空列表
        }

        // 将新的知识点添加到现有列表
        currentList.add(newKnowledge);

        // 将更新后的列表存储回 SharedPreferences
        saveKnowledgeList(currentList);
    }

    // 删除指定 title 的 Knowledge
    public void deleteKnowledge(String titleToDelete) {
        // 先加载已有的列表
        List<Knowledge> currentList = loadKnowledgeList();
        if (currentList == null) {
            currentList = new ArrayList<>(); // 如果没有数据，则初始化一个空列表
        }

        // 删除列表中符合条件的 Knowledge 对象
        for (int i = 0; i < currentList.size(); i++) {
            Knowledge knowledge = currentList.get(i);
            if (knowledge.getTitle().equals(titleToDelete)) {
                currentList.remove(i);
                break;  // 删除一个后可以跳出循环（如果只删除第一个匹配的对象）
            }
        }

        // 将更新后的列表存储回 SharedPreferences
        saveKnowledgeList(currentList);
    }
}
