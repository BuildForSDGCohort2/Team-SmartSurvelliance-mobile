package com.production.smartsurvelliance

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import kotlinx.android.synthetic.main.ss_view_live_feed_fragment.*
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.io.IOException


private const val USE_TEXTURE_VIEW = false
private const val ENABLE_SUBTITLES = true

class ViewLiveFeedFragment : Fragment() {
    private lateinit var exoPlayer: ExoPlayer
    private var mLibVLC: LibVLC? = null
    private var mMediaPlayer: MediaPlayer? = null

    private val ASSET_FILENAME = "bbb.m4v"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLibVLC = LibVLC(requireContext(), ArrayList<String>().apply {
              add("--no-drop-late-frames")
              add("--no-skip-frames")
              add("--rtsp-tcp")
              add("--aout=opensles")
              add("--audio-time-stretch") // time stretching
              add("-vvv") // verbosity
              add("--http-reconnect")
              add("--network-caching="+6*1000);
        })
        mMediaPlayer = MediaPlayer(mLibVLC)
    }

    override fun onStart() {
        super.onStart()
        mMediaPlayer?.attachViews(videoLayout, null, ENABLE_SUBTITLES, USE_TEXTURE_VIEW)

//        try {
//            val name = "login";
//            val password = "password";
//            val cameraUrl = "100.00.00.01:9982";
//            val rtspUrl = "rtsp://" + name + ":" + password + "@" + cameraUrl
////            val httpUrl = "https://fetch-mc.s3.amazonaws.com/fetchmc/165_Daphne-ft.-Featurist---Allez-SHOW2BABI.COM.mp3"
//            val uri = Uri.parse(rtspUrl) // ..whatever you want url...or even file fromm asset
//
//            Media(mLibVLC, uri).apply {
//                setHWDecoderEnabled(true, false);
//                addOption(":network-caching=150");
//                addOption(":clock-jitter=0");
//                addOption(":clock-synchro=0");
//                mMediaPlayer?.media = this
//
//            }.release()
//
//            mMediaPlayer?.play()
//
////            val media: Media = Media(mLibVLC, getAssets().openFd(ASSET_FILENAME))
////            mMediaPlayer!!.media = media
////            media.release()
//
//
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

    }

    companion object {
        fun newInstance() = ViewLiveFeedFragment()
    }

    private lateinit var viewModel: ViewLiveFeedViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory())

        exoPlayer = ExoPlayerFactory.newSimpleInstance(this.context, trackSelector)

        return inflater.inflate(R.layout.ss_view_live_feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        player_view.player = exoPlayer
//        player_view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
//        player_view.requestFocus()

//        val simpleVideoView = findViewById(android.R.id.simpleVideoView) as VideoView
//        simpleVideoView.setVideoURI(Uri.parse()







        view_live_feed_button.setOnClickListener {
            //If User valid to play play song
            try {
                it?.let {
//                    val rtspStream  = "rtsp://192.168.8.168:8554/ds-test"
//                    val uri = Uri.parse(rtspStream)

//                    simpleVideoView.setVideoURI(uri)
//                    simpleVideoView.requestFocus()
//                    simpleVideoView.start()


                    val edittext= EditText(requireContext())
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    edittext.layoutParams = lp

                    val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())

                    alert.setMessage("Enter url")
                    alert.setTitle("Enter Your RTSP Url")
                    alert.setView(edittext) //

                    alert.setPositiveButton("Stream",
                        DialogInterface.OnClickListener { dialog, whichButton -> //What ever you want to do with the value
//                            val youEditTextValue: Editable = edittext.getText()
                            //OR
                            val youEditTextValue: String = edittext.getText().toString()

                            if ( !youEditTextValue.startsWith("rtsp://")) {
                                Toast.makeText(requireContext(), "Not a valid RTSP Url", Toast.LENGTH_LONG).show()
                                return@OnClickListener
                            }



                            Toast.makeText(requireContext(), "RTSP Url: $youEditTextValue", Toast.LENGTH_SHORT).show()
                            val rtspStream  = youEditTextValue
                            val uri = Uri.parse(rtspStream)


//                            simpleVideoView.setVideoURI(uri)
//                            simpleVideoView.requestFocus()
//                            simpleVideoView.start()

                            try {
                                val name = "login";
                                val password = "password";
                                val cameraUrl = "100.00.00.01:9982";
                                val rtspUrl = "rtsp://" + name + ":" + password + "@" + cameraUrl
//            val httpUrl = "https://fetch-mc.s3.amazonaws.com/fetchmc/165_Daphne-ft.-Featurist---Allez-SHOW2BABI.COM.mp3"
                                //val uri = Uri.parse(rtspUrl) // ..whatever you want url...or even file fromm asset

                                Media(mLibVLC, uri).apply {
                                    setHWDecoderEnabled(true, false);
                                    addOption(":network-caching=150");
                                    addOption(":clock-jitter=0");
                                    addOption(":clock-synchro=0");
                                    mMediaPlayer?.media = this

                                }.release()

                                mMediaPlayer?.play()

                                //            val media: Media = Media(mLibVLC, getAssets().openFd(ASSET_FILENAME))
                                //            mMediaPlayer!!.media = media
                                //            media.release()


                            } catch (e: IOException) {
                                e.printStackTrace()
                                Log.d(this.tag, "Here")
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()//
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                            }



                        })

                    alert.setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            // what ever you want to do with No option.
                            Toast.makeText(requireContext(), "No RTSP url provide", Toast.LENGTH_SHORT).show()

                        })

                    alert.show()

//                    val movieUrl  = "rtsp://192.168.8.168:8554/ds-test"
//
//                    if ( rtspStream.startsWith("rtsp://")) {
//                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(rtspStream))
//                        startActivity(intent)
//                    }

//                    player_view.preparePlayer(
//                        exoPlayer!!,
//                        this.requireContext(),
//                        Uri.parse("rtsp://192.168.8.168:8554/ds-test")
//                    )

//                    val factory: DataSource.Factory =
//                        DataSource.Factory { UdpDataSource(3000, 100000) }
//
//                    val tsExtractorFactory = ExtractorsFactory {
//                        arrayOf(
//                            TsExtractor(
//                                MODE_SINGLE_PMT,
//                                TimestampAdjuster(0), DefaultTsPayloadReaderFactory()
//                            )
//                        )
//                    }
//
//                    val videoUri = "https://www.youtube.com/watch?reload=9&v=c-ni0M3EtK8"
//
//                    val mediaSource: MediaSource = ExtractorMediaSource(
//                        Uri.parse(videoUri),
//                        factory,
//                        tsExtractorFactory,
//                        null,
//                        null
//                    )
//
//                    exoPlayer.prepare(mediaSource)
//                    exoPlayer.playWhenReady = true

                }
            } catch (e: Exception) {
               e.printStackTrace()
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }


        }



        view_live_full_feed_button.setOnClickListener {
            //If User valid to play play song
            try {
                it?.let {
//                    val rtspStream  = "rtsp://192.168.8.168:8554/ds-test"
//                    val uri = Uri.parse(rtspStream)

//                    simpleVideoView.setVideoURI(uri)
//                    simpleVideoView.requestFocus()
//                    simpleVideoView.start()


                    val edittext= EditText(requireContext())
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    edittext.layoutParams = lp

                    val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())

                    alert.setMessage("Enter url")
                    alert.setTitle("Enter Your RTSP Url")
                    alert.setView(edittext) //

                    alert.setPositiveButton("Stream",
                        DialogInterface.OnClickListener { dialog, whichButton -> //What ever you want to do with the value
//                            val youEditTextValue: Editable = edittext.getText()
                            //OR
                            val youEditTextValue: String = edittext.getText().toString()

                            if (!youEditTextValue.startsWith("rtsp://")) {
                                Toast.makeText(
                                    requireContext(),
                                    "Not a valid RTSP Url",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@OnClickListener
                            }

                            Toast.makeText(
                                requireContext(),
                                "RTSP Url: $youEditTextValue",
                                Toast.LENGTH_SHORT
                            ).show()
                            val rtspStream = youEditTextValue
                            val uri = Uri.parse(rtspStream)

                            try {
                                val name = "login";
                                val password = "password";
                                val cameraUrl = "100.00.00.01:9982";
                                val rtspUrl = "rtsp://" + name + ":" + password + "@" + cameraUrl
//            val httpUrl = "https://fetch-mc.s3.amazonaws.com/fetchmc/165_Daphne-ft.-Featurist---Allez-SHOW2BABI.COM.mp3"
                                //val uri = Uri.parse(rtspUrl) // ..whatever you want url...or even file fromm asset

                                Media(mLibVLC, uri).apply {
                                    setHWDecoderEnabled(true, false);
                                    addOption(":network-caching=150");
                                    addOption(":clock-jitter=0");
                                    addOption(":clock-synchro=0");
                                    mMediaPlayer?.media = this

                                }.release()

                                mMediaPlayer?.play()

                            //            val media: Media = Media(mLibVLC, getAssets().openFd(ASSET_FILENAME))
                            //            mMediaPlayer!!.media = media
                            //            media.release()


                            } catch (e: IOException) {
                                e.printStackTrace()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }


//                            val intent = Intent(Intent.ACTION_VIEW, uri)
//                            startActivity(intent)
                            // startMediaPlayerActivity(uri: Uri?)
                        })

                    alert.setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            // what ever you want to do with No option.
                            Toast.makeText(
                                requireContext(),
                                "No RTSP url provide",
                                Toast.LENGTH_SHORT
                            ).show()

                        })

                    alert.show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }

    }

    override fun onStop() {
        super.onStop()
        mMediaPlayer?.stop()
        mMediaPlayer?.detachViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.release()
        mLibVLC?.release()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ViewLiveFeedViewModel::class.java)
        // TODO: Use the ViewModel
    }

//    private fun startMediaPlayerActivity(videoUri: Uri?) =
//        startActivity(Intent(this, MediaPlayerActivity::class.java).apply {
//            putExtra(MediaPlayerActivity.MediaUri, videoU

}