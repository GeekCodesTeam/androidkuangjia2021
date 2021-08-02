package xyz.doikki.dkplayer.widget.render;

import android.content.Context;

import xyz.doikki.videoplayer.render.IRenderView;
import xyz.doikki.videoplayer.render.RenderViewFactory;
import xyz.doikki.videoplayer.render.TextureRenderView;

public class TikTokRenderViewFactoryDk extends RenderViewFactory {

    public static TikTokRenderViewFactoryDk create() {
        return new TikTokRenderViewFactoryDk();
    }

    @Override
    public IRenderView createRenderView(Context context) {
        return new TikTokRenderViewDk(new TextureRenderView(context));
    }
}
