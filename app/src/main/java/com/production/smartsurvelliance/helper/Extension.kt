package com.production.smartsurvelliance.helper

import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.ImageView
import androidx.annotation.Nullable
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.production.smartsurvelliance.R
import com.squareup.picasso.Picasso
import timber.log.Timber

fun PlayerView.preparePlayer(player: ExoPlayer, context: Context, musicUri: Uri) {
    val dataSourceFactory = DefaultHttpDataSourceFactory(
        Util.getUserAgent(context, context.packageName)
    )
// This is the MediaSource representing the media to be played.
    val videoSource = buildMediaSource(dataSourceFactory, musicUri, null)
// Prepare the player with the source.
    player.prepare(videoSource)

}

private fun buildMediaSource(
    dataSourceFactory: DataSource.Factory,
    uri: Uri, @Nullable overrideExtension: String?
): MediaSource {
    /*val downloadRequest = (getApplication() as DemoApplication).getDownloadTracker().getDownloadRequest(uri)
    if (downloadRequest != null) {
        return DownloadHelper.createMediaSource(downloadRequest!!, dataSourceFactory)
    }*/
    @C.ContentType val type = Util.inferContentType(uri, overrideExtension)
    return when (type) {
        C.TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        C.TYPE_SS -> SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        C.TYPE_OTHER -> ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        else -> throw IllegalStateException("Unsupported type: $type")
    }
}

fun ImageView.loadImage(url:String?){
    Timber.d(url.toString())
    Picasso.get().load(url).into(this)
}

