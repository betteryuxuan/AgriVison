package com.example.homepageview.model;

import com.example.homepageview.R;
import com.example.homepageview.contract.IHomeFirstContract;
import com.example.homepageview.presenter.HomePagePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFirstModel implements IHomeFirstContract.IHomeFirstModel<HomePagePresenter> {

    @Override
    public List<Integer> getBannerDatas() {

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.example_pic1);
        list.add(R.drawable.example_pic2);
        list.add(R.drawable.example_pic3);

        return list;
    }

    @Override
    public List<Corn> getCornRecyclerViewDatas() {

        List<Corn> corns = new ArrayList<>();
        corns.add(new Corn("草莓", R.drawable.strawberry_icon));
        corns.add(new Corn("香蕉", R.drawable.banana_icon));
        corns.add(new Corn("草莓", R.drawable.strawberry_icon));
        corns.add(new Corn("香蕉", R.drawable.banana_icon));
        corns.add(new Corn("草莓", R.drawable.strawberry_icon));
        corns.add(new Corn("香蕉", R.drawable.banana_icon));
        corns.add(new Corn("草莓", R.drawable.strawberry_icon));
        corns.add(new Corn("香蕉", R.drawable.banana_icon));
        return corns;
    }

    @Override
    public List<News> getNewsRecyclerViewDatas() {
        List<News> newsList = new ArrayList<>();
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));
        newsList.add(new News("在新疆阿克苏地区沙雅县古勒巴格镇，农用机械在一处采收完的棉田里进行残膜回收、秸秆粉碎还田作业（2020年10月23日摄，无人机照片）。阿克苏地区沙雅县地处塔里木盆地西北缘，是产棉大县。近年来，沙雅县先后吸引棉纺、农机制造、节水设备制造等多家企业落户。\n" +
                "\n" + "“十四五”时期，我国明确提出要全面推进乡村振兴，加快农业农村现代化，提高农业质量效益和竞争力，完善农业科技创新体系，建设智慧农业。在农业领域，无人化和人工智能技术正走进山间和田地。同时，智慧农业系统通过收集数据并分析，让农户更好了解农田的全方位信息，实现精准科学的农业生产管理规划，大幅提升农业生产效益。", R.drawable.news1_pic));

        return newsList;
    }
}
