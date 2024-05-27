package com.example.basecommon.view;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Vibrator;

import com.example.basecommon.R;

import java.util.HashMap;

public class SoundManager {
    private SoundPool mSoundPool;
    private HashMap<Integer,Integer> mSoundPoolMap;
    private AudioManager mAudioManager;
    private Context mContext;

    public SoundManager(Context mContext){
        this.mContext = mContext;
        ((Activity)mContext).setVolumeControlStream(AudioManager.STREAM_MUSIC); // 볼륨컨트롤의 기본 설정을 (STREAM_MUSIC) 음악 및 미디어로 바꾸겠다는 뜻
        mSoundPoolMap = new HashMap<Integer, Integer>();
        mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
        //mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * 0.5), AudioManager.FLAG_PLAY_SOUND); // 0.9 = 90% 크기로 설정
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * 0.1), AudioManager.FLAG_PLAY_SOUND); // 0.9 = 90% 크기로 설정
        initSoundPool();
    }

    // 사운드 출력 객체 초기화
    public void initSoundPool(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){ // 롤리팝 이후의 버전이라면 build() 호출하여 생성
            mSoundPool = new SoundPool.Builder().build();
        }
        else{
            mSoundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0); // 강제 생성
        }

        // 리스트 첫번째 위치에 효과음 저장(초기화)
        this.addSound(0, R.raw.beep1);
    }

    public void addSound(int index, int soundId){ //효과음 추가  => index: 효과음 담을 위치, soundId: 효과음파일
        mSoundPoolMap.put(index,mSoundPool.load(mContext, soundId,1));
    }

    public int playSound(int index, int loop, int rate){ //효과음 재생
        int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if(loop >= 2) { // Error -> 진동을 울린다.
            Vibrator vibrator = (Vibrator) this.mContext.getSystemService(mContext.VIBRATOR_SERVICE);
            vibrator.vibrate(1000); // 1초간 진동
        }

        return mSoundPool.play(mSoundPoolMap.get(index),streamVolume,streamVolume,1, loop, rate); // 출력음악의 ID(리스트에 저장된 해당 인덱스 위치의 효과음 파일 호출), 왼쪽볼륨, 오른쪽볼륨, ?, 반복횟수(0=1번반복), 재생속도(1=1배)
    }

    public void stopSound(int streamId){
        mSoundPool.stop(streamId);
    } //효과음 정지

    public void pauseSound(int streamId){
        mSoundPool.pause(streamId);
    } //효과음 일시정지

    public void resumeSound(int streamId){
        mSoundPool.resume(streamId);
    } //효과음 재시작
}