package com.fosung.xuanchuanlan.xuanchuanlan.main.activity

/**
 * Created by fosung on 2019/9/20.
 */
//import com.yuntongxun.plugin.common.common.utils.GlideApp
//import com.yuntongxun.plugin.common.common.utils.TextUtil
//import kotlinx.android.synthetic.main.activity_xcl_main_frame.*
//import kotlinx.android.synthetic.main.xcl_notice_bar.view.*
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.*
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.fosung.frameutils.http.response.ZResponse
import com.fosung.frameutils.util.*
import com.fosung.xuanchuanlan.R
import com.fosung.xuanchuanlan.common.app.ConfApplication
import com.fosung.xuanchuanlan.common.base.ActivityParam
import com.fosung.xuanchuanlan.common.base.BaseActivity
import com.fosung.xuanchuanlan.common.consts.PathConst
import com.fosung.xuanchuanlan.common.util.UpdateMgr
import com.fosung.xuanchuanlan.mian.activity.VideoActivity
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster
import com.fosung.xuanchuanlan.mian.http.entity.UpdateBean
import com.fosung.xuanchuanlan.mian.http.entity.UpdateReple
import com.fosung.xuanchuanlan.mian.http.entity.VideoDetailBean
import com.fosung.xuanchuanlan.mian.http.entity.VideoSelectEvent
import com.fosung.xuanchuanlan.xuanchuanlan.daketang.activity.DKTMainActivity
import com.fosung.xuanchuanlan.xuanchuanlan.localprofile.activity.LocalProfileActivity
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity.CheckFirstVersion
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity.CustomerService
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity.FirstLayout
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity.XCLMainNoticeListReplayBean
import com.fosung.xuanchuanlan.xuanchuanlan.notice.activity.DynamicListActivity
import com.fosung.xuanchuanlan.xuanchuanlan.notice.activity.NoticeListActivity
import com.fosung.xuanchuanlan.xuanchuanlan.notice.activity.RedBackplaneListActivity
import com.fosung.xuanchuanlan.xuanchuanlan.personalcenter.activity.LoginActivity
import com.fosung.xuanchuanlan.xuanchuanlan.personalcenter.activity.PersonalCenterActivity
import com.fosung.xuanchuanlan.xuanchuanlan.tools.BlurPopWin
import com.fosung.xuanchuanlan.xuanchuanlan.tools.LongClickUtils
import com.fosung.xuanchuanlan.xuanchuanlan.widget.MarqueeView
import com.tencent.smtt.sdk.QbSdk
import com.zcolin.gui.ZConfirm
import com.zplayer.library.ZPlayer
import okhttp3.Response
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.io.FileOutputStream
import java.util.*

@ActivityParam(isShowToolBar = false)
class XCLMainActivity : BaseActivity(), View.OnClickListener {
    private var user_id: String? = ""
    private var user_tel: String? = ""
    private var orgCode: String? = ""

    //设备id
    private var machineId: String? = ""

    //模板id
    private var grantId: String? = ""

    private var hasBillVideo: Boolean = false

    private var firstLayout: FirstLayout.Layout? = null
    private val columnList = mutableListOf<FirstLayout.Common>()  //栏目list
    private val modularList = mutableListOf<View>() //模块list


    private var menuStyle: FirstLayout.Style? = null
    private var drawableNormal: GradientDrawable? = null
    private var drawableCheck: GradientDrawable? = null

    private var zpPlayer: ZPlayer? = null
    private var playerBackground: LinearLayout? = null
    private var fr: FrameLayout? = null

    private var curTag: String = "" //当前播放栏目
    private val curMap = mutableMapOf<String, Int>() //播放进度存储
    private val lmMap = mutableMapOf<String, Boolean>() //存储栏目是否有视频模块
    private var playUrl: String? = null   //当前播放地址

    private var listMarquee: List<XCLMainNoticeListReplayBean.XCLNoticeData>? = null
    private val handler = Handler()


    private var noticeBar: View? = null
    private var marqueeView: MarqueeView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xcl_main_frame)
//        EventBus.getDefault().register(this)
        LongClickUtils.setLongClick(handler, findViewById(R.id.enter_personalCenter), 5000) {
            startActivity(Intent(this@XCLMainActivity, PersonalCenterActivity::class.java))
            false
        }

        LongClickUtils.setLongClick(handler, findViewById(R.id.enter_file), 3000) {
            try {
                val intent = Intent()
                intent.setClassName("com.android.rockchip", "com.android.rockchip.RockExplorer")
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this@XCLMainActivity, "此设备无法打开文件管理器", Toast.LENGTH_SHORT).show()
            }

            false
        }

        LongClickUtils.setLongClick(handler, findViewById(R.id.enter_setting), 3000) {
            try {
                val intent = Intent()
                intent.setClassName(
                    "com.moos.launcher3.rk3288",
                    "com.android.setting.iot.SettingsActivity"
                )
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this@XCLMainActivity, "此设备无法打开系统设置", Toast.LENGTH_SHORT).show()
            }

            false
        }

        LongClickUtils.setLongClick(handler, findViewById(R.id.enter_update), 3000) {
//            UpdateAppUtils.from(this@XCLMainActivity)
//                    .serverVersionCode(getVersionCode(this@XCLMainActivity) + 1)
//                    .serverVersionName(getVersionName(this@XCLMainActivity))
//                    .apkPath("https://www.pgyer.com/axeF")
//                    .update()

            // TODO 继承webviewactivity不请求接口
            // Activity act = (Activity) container.getContext();
            // HiosHelper.resolveAd(act, mReceiver, "");
            // 如果是activity中的fragment 那么 HiosHelper.resolveAd(activity, fragment, "");
//            HiosHelper.resolveAd(this@XCLMainActivity, this@XCLMainActivity, "https://www.pgyer.com/axeF")
            // TODO
            val uri: Uri = Uri.parse("https://www.pgyer.com/axeF")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            false
        }

        val sysSp =
            ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE)
        grantId = sysSp.getString("grantId", "")
        machineId = sysSp.getString("machineMac", "")//客户端唯一标识//"3HX0217919003605";
        if (TextUtils.isEmpty(grantId)) {
            Toast.makeText(this, "模板获取失败，请联系管理员进行配置并点击后退重试。", Toast.LENGTH_LONG).show()
        } else {
            checkVersion()
        }
    }

    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    override fun onBackPressed() {
        //do nothing
    }


    private fun initPage() {
//      val layout = GsonUtil.stringToBean(AssetsUtil.getJson("firstLayout.json", this), FirstLayout::class.java)
//      firstLayout = layout.data
        val commonList = firstLayout!!.commonList
        commonList.forEach {
            when (it.type) {
                "background" -> {
                    loadBackground(findViewById(R.id.backgroundLayout), it.image)
                }

                "logo" -> {
                    val ivLogo = ImageView(this)
                    ivLogo.layoutParams = ViewGroup.LayoutParams(it.width, it.height)
                    ivLogo.scaleType = ImageView.ScaleType.FIT_XY
                    ivLogo.x = it.x.toFloat()
                    ivLogo.y = it.y.toFloat()
                    loadImage(ivLogo, it.image)
                    fr!!.addView(ivLogo)
                }

                "time" -> {
                    val tvDate = TextClock(this)
                    tvDate.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    tvDate.x = it.x.toFloat()
                    tvDate.y = it.y.toFloat()
                    val style = GsonUtil.stringToBean(it.style, FirstLayout.Style::class.java)
                    tvDate.textSize = style.fontsize.toFloat()
                    tvDate.setTextColor(Color.parseColor(style.color))
                    setFormatHour(tvDate, style.foemat)
                    fr!!.addView(tvDate)
                }

                "Personalcenter" -> {
                    val ivHead = ImageView(this)
                    ivHead.id = R.id.iv_personal
                    ivHead.layoutParams = ViewGroup.LayoutParams(it.width, it.height)
                    ivHead.x = it.x.toFloat()
                    ivHead.y = it.y.toFloat()
                    loadImage(ivHead, it.image)
                    ivHead.setOnClickListener(this)
                    fr!!.addView(ivHead)
                }

                "menu" -> {
                    menuStyle = GsonUtil.stringToBean(it.style, FirstLayout.Style::class.java)


                    drawableNormal = GradientDrawable()
                    drawableNormal!!.cornerRadius = 15f

                    try {
                        drawableNormal!!.setColor(Color.parseColor(menuStyle!!.initcolor))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    drawableCheck = GradientDrawable()
                    drawableCheck!!.cornerRadius = 15f
                    try {
                        drawableCheck!!.setColor(Color.parseColor(menuStyle!!.checkedcolor))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
//
                "grantmenu" -> {
                    if (firstLayout!!.isshowmenu) {
                        val tvColumn = TextView(this)
                        tvColumn.layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        tvColumn.x = it.x.toFloat()
                        tvColumn.y = it.y.toFloat()
                        tvColumn.setPadding(15, 10, 15, 10)
                        tvColumn.background = drawableNormal
                        tvColumn.textSize = menuStyle!!.fontsize.toFloat() - 10f
                        tvColumn.setTextColor(Color.parseColor(menuStyle!!.color))
                        tvColumn.text = it.name
                        if (it.sort == 1) {
                            it.check = true
                            curTag = it.name
                        }
                        tvColumn.tag = it.name
                        tvColumn.setOnClickListener { changeColumnCheck(tvColumn.tag) }
                        fr!!.addView(tvColumn)


                    }
                    columnList.add(it)
                }

                "announcement" -> {
//                    setupNoticeBar()

                    var announcementStyle =
                        GsonUtil.stringToBean(it.style, FirstLayout.AnnouncementStyle::class.java)

                    val noticeBar = layoutInflater.inflate(R.layout.xcl_notice_bar, fr, false)
//                    val layoutParams = noticeBar.layoutParams as? RelativeLayout.LayoutParams
//                    layoutParams?.addRule(RelativeLayout.ALIGN_PARENT_TOP)
//                    backgroundLayout.addView(noticeBar)
                    val marqueeView: MarqueeView = noticeBar.findViewById(R.id.marqueeView)
                    noticeBar.layoutParams = ViewGroup.LayoutParams(it.width, it.height)
                    noticeBar.x = it.x.toFloat()
                    noticeBar.y = it.y.toFloat()
                    marqueeView.setMaxLines(announcementStyle.lines.toInt())
                    marqueeView.setTextSize(announcementStyle.size.toInt())
                    fr!!.addView(noticeBar)

                    this.noticeBar = noticeBar

                    showNoticeBar()
                }

                "billVideo" -> {

                    hasBillVideo = true

                    if (zpPlayer == null) {
                        zpPlayer = ZPlayer(this)
                        zpPlayer!!.setPadding(2, 2, 2, 2)
                        fr!!.addView(zpPlayer)
                    }
                    isPlayed = false
                    zpPlayer!!.layoutParams.height = it.height
                    zpPlayer!!.layoutParams.width = it.width
                    zpPlayer!!.x = it.x.toFloat()
                    zpPlayer!!.y = it.y.toFloat()
                    zpPlayer!!.visibility = View.VISIBLE
                    zpPlayer!!.setNetChangeListener(false)//设置监听手机网络的变化,这个参数是内部是否处理网络监听，和setOnNetChangeListener没有关系
                        .setShowTopControl(false)
                        .setShowCenterControl(true)
                        .setSupportGesture(false)
                        .setShowNavIcon(false)
                        .setOnFullScreenListener { isFullScreen: Boolean ->

                            val videoNew = it.videonews
                            val videoZdy = it.medialibrary
                            if (!videoNew && !videoZdy) {
                                ToastUtil.toastShort("暂无更多视频")
                            } else {
                                if (!logged()) {
                                    val intent =
                                        Intent(this@XCLMainActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this, VideoActivity::class.java)
                                    intent.putExtra(VideoActivity.isNews, videoNew)
                                    intent.putExtra(VideoActivity.isLibrary, videoZdy)
                                    startActivity(intent)
                                }
                            }

                        }
                        .onComplete {
                            zpPlayer!!.play(playUrl)
                        }
                        .setScaleType(ZPlayer.SCALETYPE_FITPARENT)

                    if (!playUrl.equals(it.videourl)) {
                        zpPlayer!!.play(it.videourl)
                        if (curMap.containsKey(it.videourl)) {
                            zpPlayer!!.seekTo(curMap.get(it.videourl)!!, false)
                        }
                    } else {
                        zpPlayer!!.onResume()
                    }
                    playUrl = it.videourl

                    if (playerBackground == null) {
                        playerBackground = LinearLayout(this)
                        fr!!.addView(playerBackground)
                    }
                    playerBackground!!.layoutParams = FrameLayout.LayoutParams(
                        it.width + DisplayUtil.dip2px(this, 2f),
                        it.height + DisplayUtil.dip2px(this, 2f)
                    )
                    playerBackground!!.x = it.x.toFloat() - DisplayUtil.dip2px(this, 1f)
                    playerBackground!!.y = it.y.toFloat() - DisplayUtil.dip2px(this, 1f)
                    playerBackground!!.setBackgroundResource(R.drawable.player_bg)
                    playerBackground!!.visibility = View.VISIBLE
                }
            }
        }
        changeColumn()
//        setupNoticeBar()
    }

//    private fun setupNoticeBar(){
//        val noticeBar = layoutInflater.inflate(R.layout.xcl_notice_bar,backgroundLayout,false)
//        val layoutParams = noticeBar.layoutParams as? RelativeLayout.LayoutParams
//        layoutParams?.addRule(RelativeLayout.ALIGN_PARENT_TOP)
//        backgroundLayout.addView(noticeBar)
//
////        noticeBar.layoutParams = ViewGroup.LayoutParams(940, 100)
////        noticeBar.x = 75.0f
////        noticeBar.y = 1650.0f
////        fr.addView(noticeBar)
//
//        this.noticeBar = noticeBar
//
//        showNoticeBar()
//    }

    private fun changeColumnCheck(tag: Any) {
        if (curTag == tag) {
            return
        }
        curTag = tag.toString()
        columnList.forEach {
            it.check = it.name == tag
        }
        changeColumn()
    }

    private fun changeColumn() {
        if (!hasBillVideo) {
            releaseZp()
        }

        modularList.forEach {
            fr!!.removeView(it)
        }
        modularList.clear()

        columnList.forEach {//迭代栏目
                column: FirstLayout.Common ->
            if (firstLayout!!.isshowmenu) {
                val tvColum: TextView = fr!!.findViewWithTag(column.name) as TextView
                if (column.check) {
                    val drawableNormal = GradientDrawable()
                    drawableNormal.cornerRadius = 15f
                    try {
                        drawableNormal.setColor(Color.parseColor(menuStyle!!.checkedcolor))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    tvColum.background = drawableNormal
                    column.appList?.forEach { app ->
                        addModular(app, column.name)
                    }
                } else {
                    val drawableNormal = GradientDrawable()
                    drawableNormal.cornerRadius = 15f
                    try {
                        drawableNormal.setColor(Color.parseColor(menuStyle!!.initcolor))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    tvColum.background = drawableNormal
                }
            } else {
                column.appList?.forEach { app ->
                    addModular(app, column.name ?: "默认栏目")
                }
            }
        }
    }

    private fun addModular(it: FirstLayout.Common, tag: String) {
        if (it.type != "appvideo") {
            val ivMo = ImageView(this)
            ivMo.layoutParams = ViewGroup.LayoutParams(it.width, it.height)
            ivMo.x = it.x.toFloat()
            ivMo.y = it.y.toFloat()
            ivMo.scaleType = ImageView.ScaleType.FIT_XY
            loadImage(ivMo, it.image)
            ivMo.setOnClickListener { _ ->
                when (it.code) {
                    "xcl-dengta-app" -> {//灯塔App
                        try {
                            AppUtil.startOtherApp(
                                this,
                                getString(R.string.xcl_home_packagename_dengtadangjian)
                            )
                        } catch (e: Exception) {
                            Toast.makeText(this, "未安装应用", Toast.LENGTH_SHORT).show()
                        }
                    }

                    "xcl-dazhongribao" -> {//大众日报
                        try {
                            AppUtil.startOtherApp(
                                this,
                                getString(R.string.xcl_home_packagename_xinruidazhong)
                            )
                        } catch (e: Exception) {
                            Toast.makeText(this, "未安装应用", Toast.LENGTH_SHORT).show()
                        }
                    }

                    "xcl-dangjianzixun" -> {//党建资讯
                        val intent = Intent(this, XCLWebActivity::class.java)
                        intent.putExtra("Url", getString(R.string.xcl_home_url_dangjianzixun))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }

                    "xcl-dazhongshuzibao" -> {//大众数字报

                        val intent = Intent(this, XCLWebActivity::class.java)
                        intent.putExtra("Url", getString(R.string.xcl_home_url_dazhongribao))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }

                    "xcl-tushuguan" -> {//图书馆

                        try {

                            if (!logged()) {
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                return@setOnClickListener
                            }

                            val intent = Intent()
                            intent.setClassName(
                                getString(R.string.xcl_home_packagename_dengtadangjian),
                                getString(R.string.xcl_home_classname_tushuguan)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)

                        } catch (e: Exception) {
                            Toast.makeText(this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show()
                        }
                    }

                    "xcl-weishipin" -> {//微视频

                        try {


                            if (!logged()) {
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                return@setOnClickListener
                            }
                            val intent = Intent()
                            intent.setClassName(
                                getString(R.string.xcl_home_packagename_dengtadangjian),
                                getString(R.string.xcl_home_classname_weishipin)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)


                        } catch (e: Exception) {
                            Toast.makeText(this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show()
                        }
                    }

                    "xcl-zhuxuanlv" -> {//主旋律
                        try {

                            if (!logged()) {
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                return@setOnClickListener
                            }

                            val intent = Intent()
                            intent.setClassName(
                                getString(R.string.xcl_home_packagename_dengtadangjian),
                                getString(R.string.xcl_home_classname_zhuxuanlv)
                            )
                            //                                    Intent intent = new Intent(MainActivity.this, DKTTopicDetailActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)


                        } catch (e: Exception) {
                            Toast.makeText(this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show()
                        }

                    }

                    "xcl-daketang" -> {//大课堂
                        try {

                            if (!logged()) {
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                return@setOnClickListener
                            }


                            val intent = Intent(this, DKTMainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)


                        } catch (e: Exception) {
                            Toast.makeText(this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show()
                        }

                    }

                    "xcl-sanwugongkai" -> {//三务公开
                        try {

                            val intent = Intent(this, XCLWebActivity::class.java)
                            intent.putExtra("Url", getString(R.string.xcl_home_url_sanwugongkai))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show()
                        }

                    }

                    "xcl-hongsebeiban" -> {//红色背板
                        if (!logged()) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            return@setOnClickListener
                        }

                        val intent = Intent(this, RedBackplaneListActivity::class.java)
//                        val intent = Intent(this, XCLWebActivity::class.java)
//                        intent.putExtra("Url", "file:///android_asset/dist/index.html#/cabinet/index?mac=$machineId&orgCode=$orgCode")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }

                    "xcl-hongsezhanting" -> {//红色展厅（特色旅游）
//                        val intent = Intent(this, XCLTravelActivity::class.java)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                        startActivity(intent)
//                        return@setOnClickListener
                        val intent = Intent(this, XCLWebActivity::class.java)
                        intent.putExtra("Url", getString(R.string.xcl_home_url_new1))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }


                    "xcl-zhengwufuwu" -> {//政务服务
                        if (!logged()) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            return@setOnClickListener
                        }
                        val intent = Intent(this, XCLWebActivity::class.java)
                        intent.putExtra("Url", getString(R.string.xcl_home_url_zhengwufuwu))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }

                    "xcl-bendigaikuang" -> {//本地概况
                        if (!logged()) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            return@setOnClickListener
                        }
                        val intent = Intent(this, LocalProfileActivity::class.java)
//                        intent.putExtra("Url", "file:///android_asset/dist/index.html#/index?mac=$machineId&orgCode=$orgCode")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)

                    }

                    "xcl-gongzuodongtai" -> {//工作动态
                        if (!logged()) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            return@setOnClickListener
                        }
                        val intent = Intent(this, DynamicListActivity::class.java)
                        //                                intent.putExtra("Url","file:///android_asset/dist/index.html#/serve/index");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }

                    "xcl-tongzhigonggao" -> {//通知公告
                        if (!logged()) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            return@setOnClickListener
                        }
                        val intent = Intent(this, NoticeListActivity::class.java)
                        //                                intent.putExtra("Url","file:///android_asset/dist/index.html#/inform/index");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }

                    "xcl-kehufuwu" -> {//客户服务
                        requestCustomerData(ivMo)
                    }


                    else -> {
                        ToastUtil.toastLong("正在开发中，敬请期待！")
                    }

                }

            }
            fr!!.addView(ivMo)
            modularList.add(ivMo)
        } else {
            if (!hasBillVideo) {//如果没有主视频

                lmMap.put(tag, true)
//          it.videourl = "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4"
                if (zpPlayer == null) {
                    zpPlayer = ZPlayer(this)
                    zpPlayer!!.setPadding(2, 2, 2, 2)
                    fr!!.addView(zpPlayer)
                }
                isPlayed = false
                zpPlayer!!.layoutParams.height = it.height
                zpPlayer!!.layoutParams.width = it.width
                zpPlayer!!.x = it.x.toFloat()
                zpPlayer!!.y = it.y.toFloat()
                zpPlayer!!.visibility = View.VISIBLE
                zpPlayer!!.setNetChangeListener(false)//设置监听手机网络的变化,这个参数是内部是否处理网络监听，和setOnNetChangeListener没有关系
                    .setShowTopControl(false)
                    .setShowCenterControl(true)
                    .setSupportGesture(false)
                    .setShowNavIcon(false)
                    .setOnFullScreenListener { isFullScreen: Boolean ->
                        //                        //全屏的时候使用了attrs.flags |= Window.FEATURE_NO_TITLE;此Flag会导致锁屏问题，解决此问题需要取消设置此标志位。
//                        window.clearFlags(Window.FEATURE_NO_TITLE)

                        val videoNew = it.videonews
                        val videoZdy = it.medialibrary
                        if (!videoNew && !videoZdy) {
                            ToastUtil.toastShort("暂无更多视频")
                        } else {
                            if (!logged()) {
                                val intent = Intent(this@XCLMainActivity, LoginActivity::class.java)
                                startActivity(intent)
                            } else {
                                val intent = Intent(this, VideoActivity::class.java)
                                intent.putExtra(VideoActivity.isNews, videoNew)
                                intent.putExtra(VideoActivity.isLibrary, videoZdy)
                                startActivity(intent)
                            }
                        }

                    }
                    .onComplete {
                        zpPlayer!!.play(playUrl)
                    }
                    .setScaleType(ZPlayer.SCALETYPE_FITPARENT)
                if (!playUrl.equals(it.videourl)) {
                    zpPlayer!!.play(it.videourl)
                    if (curMap.containsKey(it.videourl)) {
                        zpPlayer!!.seekTo(curMap.get(it.videourl)!!, false)
                    }
                } else {
                    zpPlayer!!.onResume()
                }
//          zpPlayer!!.start()
                playUrl = it.videourl
//          modularList.add(zpPlayer!!)
                if (playerBackground == null) {
                    playerBackground = LinearLayout(this)
                    fr!!.addView(playerBackground)
                }
                playerBackground!!.layoutParams = FrameLayout.LayoutParams(
                    it.width + DisplayUtil.dip2px(this, 2f),
                    it.height + DisplayUtil.dip2px(this, 2f)
                )
                playerBackground!!.x = it.x.toFloat() - DisplayUtil.dip2px(this, 1f)
                playerBackground!!.y = it.y.toFloat() - DisplayUtil.dip2px(this, 1f)
                playerBackground!!.setBackgroundResource(R.drawable.player_bg)
                playerBackground!!.visibility = View.VISIBLE

            }
        }
    }


    private fun requestCustomerData(view: View) {

        val map = HashMap<String, String>()

        XCLHttp.post(
            XCLHttpUrlMaster.CUSTOMERSERVICE,
            map,
            object : ZResponse<CustomerService>(CustomerService::class.java, mActivity, "加载中...") {
                override fun onSuccess(response: Response, resObj: CustomerService) {
                    if (resObj.datalist != null && resObj.datalist.size > 0) {
                        BlurPopWin.Builder(this@XCLMainActivity)
                            //                            .setContent("该配合你演出的我,眼视而不见,在比一个最爱你的人即兴表演")
                            //Radius越大耗时越长,被图片处理图像越模糊
                            .setRadius(1)
                            //                            .setTitle("我是标题")
                            //设置居中还是底部显示
                            .setIamgeURL(resObj.datalist[0].imgurl)
                            .setshowAtLocationType(0)
                            .setOutSideClickable(true)
                            .show(view.rootView)
                    }
                }

                override fun onError(code: Int, error: String?) {
                    super.onError(code, error)
                }
            })

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_personal -> {
                val intent = Intent(this, PersonalCenterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun releaseZp() {
        if (zpPlayer != null) {
            if (!TextUtils.isEmpty(playUrl)) {
                curMap.put(playUrl!!, zpPlayer!!.getCurrentPosition())
            }
            zpPlayer!!.pause()
            zpPlayer!!.visibility = View.GONE

        }
        playerBackground?.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        if (zpPlayer != null) {
            zpPlayer!!.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (zpPlayer != null) {
//          zpPlayer!!.start()
            zpPlayer!!.onResume()
//          zpPlayer!!.play(playUrl, zpPlayer!!.getonReCurrentPosition())
        }

        val usersp =
            ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        user_id = usersp.getString("user_id", "")
        user_tel = usersp.getString("user_tel", "")
        orgCode = usersp.getString("user_orgCode", "")


        showNoticeBar()

        updateApp()

    }

    private fun showNoticeBar() {
        if (logged()) {
            requestForMainNotice()
        } else {
            noticeBar?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (zpPlayer != null) {
            zpPlayer!!.onDestroy()
        }
//        EventBus.getDefault().unregister(this)
    }

    private fun loadImage(ivView: ImageView, url: String?) {
        if (TextUtils.isEmpty(url)) return
        Glide.with(this)
            .load(url)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存
            .into(ivView)
    }

    private fun loadBackground(ivView: View, url: String?) {
        if (TextUtils.isEmpty(url)) return
        Glide.with(this)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .load(url)
            .into(object : SimpleTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        val drawable = BitmapDrawable(resources, resource)
//                        ivView.background = drawable
//                    }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    ToastUtil.toastShort("出错了")
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val drawable = BitmapDrawable(resources, resource)
                    ivView.background = drawable
                }
            })
    }

    /**
     * 获取版本号
     */
    private fun getVersionName(ctx: Context): String? {
        return try {
            val pi = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
            pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }

    private fun getVersionCode(context: Context): Int {
        return try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)
            pi.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * 检查首页布局版本
     * */
    private fun checkVersion() {

        val userSp =
            ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val pageJson = userSp.getString("firstpage", "")
        val pageIdJson = userSp.getString("firstpage_id", "")
        if (TextUtils.isEmpty(pageJson) || TextUtils.isEmpty(pageIdJson) || pageIdJson != grantId) {
            initData()
            return
        }

        val map = HashMap<String, String>()
        XCLHttp.get(
            HttpUrlMaster.FirstPage_Version + grantId,
            map,
            object : ZResponse<CheckFirstVersion>(
                CheckFirstVersion::class.java,
                this@XCLMainActivity,
                "加载中..."
            ) {
                override fun onSuccess(response: Response?, resObj: CheckFirstVersion?) {
                    val pageLayout = GsonUtil.stringToBean(pageJson, FirstLayout.Layout::class.java)
                    if (pageLayout.version >= resObj!!.data.version) {
                        firstLayout = pageLayout
                        initPage()
                    } else {
                        initData()
                    }
                }

                override fun onError(code: Int, error: String) {
                    super.onError(code, error)
                    initData()
                }
            })
    }

    /**
     * 获取首页布局
     */
    private fun initData() {
        val map = HashMap<String, String>()
        //billboard是commonList中带通知栏和视频模块这一版新添加的参数
        XCLHttp.get(
            HttpUrlMaster.FirstPage + grantId + "/billboard",
            map,
            object :
                ZResponse<FirstLayout>(FirstLayout::class.java, this@XCLMainActivity, "加载中...") {
                override fun onSuccess(response: Response?, resObj: FirstLayout?) {
                    firstLayout = resObj?.data
                    val sp = ConfApplication.APP_CONTEXT.getSharedPreferences(
                        "UserInfo",
                        Context.MODE_PRIVATE
                    )
                    val editor = sp.edit()
                    editor.putString("firstpage", GsonUtil.beanToString(firstLayout))
                    editor.putString("firstpage_id", grantId)
                    editor.commit()
                    initPage()
                }


                override fun onError(code: Int, error: String) {
                    super.onError(code, error)
//                ToastUtil.toastLong("首页布局获取失败")
                    ToastUtils.showLong("首页布局获取失败")
                }
            })
    }

    /**
     * 检查x5
     */
    private fun checkX5(intent: Intent) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            if (!QbSdk.isTbsCoreInited()) {
                ToastUtil.toastLong("内核未初始化完毕，请稍后再试")
            } else {
                if (!QbSdk.canLoadX5(this)) {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("内核提示")
                    dialog.setPositiveButton("重启") { _, _ -> Process.killProcess(Process.myPid()) }
                    dialog.setNegativeButton("稍后重启", null)
                    dialog.setMessage("内核初始化失败,请重启应用重新初始化.")
                    dialog.create().show()
                } else {
                    startActivity(intent)
                }
            }
        }

        startActivity(intent)
    }

    /**
     *检查更新
     */
    private fun updateApp() {

        val sp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val user_name = sp.getString("user_tel", "")//yong户唯一标识

        val bean = UpdateReple()
        bean.updateCode = "XCL_01"
        bean.channelCode = "Android"
        bean.versionState = "lts"
        bean.currentVersion = AppUtils.getAppVersionName()

        if (!TextUtils.isEmpty(user_name)) {
            bean.expression = user_name
        } else {
            bean.expression = ""
        }
        val Jsonbean = GsonUtil.beanToString(bean)
        //  ToastUtil.toastLong(""+Jsonbean);
        XCLHttp.postJson(
            HttpUrlMaster.UPDATEINFO,
            Jsonbean,
            object : ZResponse<UpdateBean>(UpdateBean::class.java) {
                override fun onSuccess(response: Response, resObj: UpdateBean?) {
                    if (resObj?.data != null) {
                        if (resObj.data.update) {
                            if ("1" == resObj.data.updateMsg.updateStrategy) {
                                ZConfirm(this@XCLMainActivity).setTitle("版本更新  " + resObj.data.updateMsg.versionInfo + "版")
                                    .setMessage("必须完成本次更新才能继续使用本系统\n\n" + resObj.data.updateMsg.updateRemark)
                                    .setCancelBtnText("退出系统")
                                    .setOKBtnText("立即升级")
                                    .setIsCancelAble(false)
                                    .addSubmitListener {
                                        val file = File(PathConst.CACHE + "xuanchuanlan.apk")
                                        FileUtil.delete(file)
                                        UpdateMgr.downLoadApp(
                                            this@XCLMainActivity,
                                            resObj.data.updateMsg.url
                                        )
                                        true
                                    }
                                    .addCancelListener {
                                        AppUtil.quitSystem()
                                        true
                                    }
                                    .show()
                            } else {
                                ZConfirm(this@XCLMainActivity).setTitle("版本更新  " + resObj.data.updateMsg.versionInfo + "版")
                                    .setMessage(resObj.data.updateMsg.updateRemark)
                                    .setCancelBtnText("暂不升级")
                                    .setOKBtnText("立即升级")
                                    .addSubmitListener {
                                        val file = File(PathConst.CACHE + "xuanchuanlan.apk")
                                        FileUtil.delete(file)
                                        UpdateMgr.downLoadApp(
                                            this@XCLMainActivity,
                                            resObj.data.updateMsg.url
                                        )
                                        true
                                    }
                                    .show()
                            }
                        }
                    }
                }

                override fun onError(code: Int, error: String?) {
                    super.onError(code, error)
                }

            })
    }

    private fun setFormatHour(v: TextClock, format: String) {
        if (v.is24HourModeEnabled) {
            v.format24Hour = format
        } else {
            v.format12Hour = format
        }
    }

    /**
     * 保存到SD卡
     * @param context
     * @param number
     * @param psw
     * @return
     */
    fun SaveUserInfo(number: String) {

        try {
            val SDCardFile = Environment.getExternalStorageDirectory();
            val file = File(SDCardFile, "data.txt");
            val fos = FileOutputStream(file)

            val data = number
            fos.write(data.toByteArray())
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }


    //登录状态
    private fun logged(): Boolean {

        val sp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val userId = sp.getString("user_id", null)
        return userId != null

    }

    //获取通知。
    private fun requestForMainNotice() {

        val json = HashMap<String, String>()
        XCLHttp.postJson(
            XCLHttpUrlMaster.XCLMainNoitceList,
            json,
            object :
                ZResponse<XCLMainNoticeListReplayBean>(XCLMainNoticeListReplayBean::class.java) {

                override fun onSuccess(response: Response, resObj: XCLMainNoticeListReplayBean?) {
                    if (resObj?.datalist != null && resObj.datalist.isNotEmpty()) {
                        listMarquee = resObj.datalist
                        //                    setVideoCoverImageForCurrentIndex();
                        initMarquee()
                        noticeBar?.visibility = View.VISIBLE
                    } else {
                        noticeBar?.visibility = View.GONE
                    }
                }

                override fun onError(code: Int, error: String) {
                    super.onError(code, error)
                    noticeBar?.visibility = View.GONE
                }
            })
    }


    /**
     * 通知公告纵向走马灯
     */
    private fun initMarquee() {
        val titleList = ArrayList<String>()
        for (data in listMarquee!!) {
            titleList.add(data.title)
        }
        marqueeView?.startWithList(titleList)
        marqueeView?.setOnItemClickListener { position, _ ->
            val data = listMarquee!!.get(position)
            //                Log.i("首页通知公告", "点击了通知公告：" + data.title);
            val intent = Intent(this@XCLMainActivity, XCLNoticeDetailActivity::class.java)
            intent.putExtra("title", data.title)
            intent.putExtra("content", data.content)
            //                intent.putExtra("isRichContent",false);
            startActivity(intent)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onVideoSelectEvent(event: VideoSelectEvent) {
        var video = event.videoList.get(event.selectedIndex)
        if (video is VideoDetailBean.DataBean.PUBLICBean) {
            playUrl = video.storePath
        }
        if (video is VideoDetailBean.DataBean.PRIVATEBean) {
            playUrl = video.storePath
        }
//        curMap.put(playUrl!!,event.videoPosition);
//        Log.i("EventBus onEvent", "video position: ${event.videoPosition}")
        var videoPosition =
            (if (event.videoPosition <= 5000) event.videoPosition else event.videoPosition - 5000)
        zpPlayer!!.play(playUrl, videoPosition)

        columnList.forEach { column ->
            column.appList.forEach { app ->
                if (app.type == "appvideo") {
                    app.videourl = playUrl;
                }
            }
        }
    }

}
