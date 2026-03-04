package bws.animations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Frame {
	public static final Frame NULL_FRAME = new Frame(0).withKey(Key.NULL_KEY);

	public List<Key> keys = new ArrayList<>();
	public int frameTime;
	public EAnimationEvent event;
	public int eventDuration;

	public Frame(int time){
		this.frameTime = time;
	}

	public Key getKeyByName(String name){
		for(Key key : keys){
			String keyName = key.limbName;
			if(Objects.equals(keyName, name)){
				return key;
			}
		}
		return Key.NULL_KEY;
	}

	public Frame withKey(Key key){
		keys.add(key);
		return this;
	}

	public EAnimationEvent getEvent(){
		return this.event;
	}

	public Frame withEvent(EAnimationEvent event, int duration){
		this.event = event;
		this.eventDuration = duration > 0 ? duration : 1;
		return this;
	}

	public Frame setFrameTime(int newtime){
		this.frameTime = newtime;
		return this;
	}
}
