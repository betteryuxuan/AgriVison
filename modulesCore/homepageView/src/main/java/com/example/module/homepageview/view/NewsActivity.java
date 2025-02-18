package com.example.module.homepageview.view;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.module.homepageview.R;


public class NewsActivity extends AppCompatActivity {

    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.news_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        initListenter();

        WebView webView = findViewById(R.id.wv_news_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient());

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"zh\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>智慧农业</title>\n" +
                "    <style>\n" +
                "        /* 基础样式，确保段落首行缩进 */\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            padding: 10px;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            text-indent: 2em; /* 段落首行空两格 */\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            max-width: 100%;\n" +
                "            height: auto;\n" +
                "            display: block;\n" +
                "            margin: 20px auto;\n" +
                "        }\n" +
                "\n" +
                "        .content {\n" +
                "            text-align: justify;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .center-text {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "    <div class=\"content\">\n" +
                "        <p>一个多月前，我们采用水培技术种植的奶白菜、油麦菜、苦菊等12项农产品获得‘湾区认证证书’，济南的蔬菜也能便捷通行粤港澳大湾区了。”尽管一轮寒潮刚过，但在济南莱芜区的山东艾绿吉泰农业科技有限公司温室大棚内，生菜、油菜、奶白菜等十几种蔬菜生机盎然。公司负责人董立新对记者说：“这是济南市首次有企业获此证书，公司近期蔬菜销量激增，快忙不过来了。</p>\n" +
                "\n" +
                "        <div class=\"center-text\">\n" +
                "            <img src=\"https://agricultural-vision.oss-cn-beijing.aliyuncs.com/W020231107349975888348.jpg\" alt=\"山东艾绿吉泰农业科技有限公司大棚内，采用水培技术种植的蔬菜\">\n" +
                "        </div>\n" +
                "\n" +
                "        <p>山东艾绿吉泰农业科技有限公司大棚内，采用水培技术种植的蔬菜。新华社记者邵鲁文 摄</p>\n" +
                "\n" +
                "        <p>“一张认证书，使得很多南方省份的知名商超纷纷抛来合作的橄榄枝。”董立新说，自从使用自动水培温室技术后，绿叶蔬菜不受天气影响，生长周期缩短、产量提升，蔬菜供应实现四季不断。“现在既卖北方，也供南方。”</p>\n" +
                "\n" +
                "        <p>记者看到，除了水培技术，艾绿吉泰农业在蔬菜种植中使用了多项科技手段，进一步提升了种植效率。例如，通过智能水质净化系统，实现了水和营养液的循环利用，一亩蔬菜用水量是传统大棚的十分之一。</p>\n" +
                "\n" +
                "        <p>在济南莱芜区，智慧农业模式使得果蔬种植打破了时空限制，实现了“南来北往”。记者在杨庄镇的济南科百智慧农业产业园内看到，工作人员正忙着监测莲雾、柠檬、木瓜等生长状况。这些南方水果，为何能在莱芜区大面积种植？用济南科百数字农业技术有限公司总经理宋伟的话来说，是因为“这里的水果会说话”。</p>\n" +
                "\n" +
                "        <div class=\"center-text\">\n" +
                "            <img src=\"https://agricultural-vision.oss-cn-beijing.aliyuncs.com/W020231107349975888348.jpg\" alt=\"济南科百智慧农业产业园内的温室大棚\">\n" +
                "        </div>\n" +
                "\n" +
                "        <p>济南科百智慧农业产业园内的温室大棚。新华社记者邵鲁文 摄</p>\n" +
                "\n" +
                "        <p>“园区内铺设有气象传感器、土壤传感器、植物生理传感器，实时采集和监测空气的温湿度、二氧化碳浓度、土壤pH值、叶面温湿度等信息。有了这些，作物可以随时‘表达’对肥料、水等的需求。”宋伟说，以遍布园区的各类传感器所收集的海量数据为底座，打造形成集环境监测、信息传输、指令控制等功能于一体的农业物联网，实现对作物生长各环节的高效管理，这让“南果北种”不再遥不可及。</p>\n" +
                "\n" +
                "        <p>“北方日照充足、昼夜温差大，有利于果实积累营养、提升甜度。”科百智慧农业产业园农艺师曹耀鹏说，通过合理使用自动化温湿度调节技术，部分品种还能实现错峰上市，受到消费者欢迎。</p>\n" +
                "\n" +
                "        <div class=\"center-text\">\n" +
                "            <img src=\"https://agricultural-vision.oss-cn-beijing.aliyuncs.com/W020231107349975888348.jpg\" alt=\"济南科百智慧农业产业园的智慧中控大屏\">\n" +
                "        </div>\n" +
                "\n" +
                "        <p>济南科百智慧农业产业园的智慧中控大屏。新华社记者邵鲁文 摄</p>\n" +
                "\n" +
                "        <p>“有智慧农业加持，农人们不再靠天吃饭。”杨庄镇副镇长殷秀娟说，莱芜区地处济南东南部，是传统农业区，农产品出口额占全市的比重超过七成。仅杨庄镇就建起12家农业龙头企业，农产品年出口创汇稳定在20亿元左右。</p>\n" +
                "\n" +
                "        <p>莱芜区今年提出，将大力发展数字农业，促进数字技术与农业深度融合。持续强化农业科技和装备支撑，新建高标准农田1万亩，农业生产综合机械化率达到92.5%。</p>\n" +
                "\n" +
                "        <p>“我们已成功打造以杨庄镇为核心的智慧农业特色小镇，创建了9个智慧农业应用示范基地。”莱芜区农业农村局副局长刘青说，南方果蔬北方种，未来智慧农业将继续助推莱芜向农业高质高效、乡村宜居宜业、农民富裕富足前进。</p>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";



        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
    }

    private void initListenter() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.iv_news_back);
    }
}

