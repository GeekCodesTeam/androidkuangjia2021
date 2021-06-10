package xyz.doikki.dkplayer.widget.render;

import android.content.Context;

import xyz.doikki.videoplayer.render.IRenderView;
import xyz.doikki.videoplayer.render.RenderViewFactory;

public class SurfaceRenderViewFactoryDk extends RenderViewFactory {

    public static SurfaceRenderViewFactoryDk create() {
        return new SurfaceRenderViewFactoryDk();
    }

    @Override
    public IRenderView createRenderView(Context context) {
        return new SurfaceRenderViewDk(context);
    }
}
