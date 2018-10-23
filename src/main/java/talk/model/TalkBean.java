package talk.model;


import java.sql.Date;
import java.sql.Timestamp;

public class TalkBean {
	private int talknum;
	private String roomhoner;
	private String talker;
	private Timestamp talktime;
	private String talk;
	
	
	
	public int getTalknum() {
		return talknum;
	}
	public void setTalknum(int talknum) {
		this.talknum = talknum;
	}
	public String getRoomhoner() {
		return roomhoner;
	}
	public void setRoomhoner(String roomhoner) {
		this.roomhoner = roomhoner;
	}
	public String getTalker() {
		return talker;
	}
	public void setTalker(String talker) {
		this.talker = talker;
	}
	public Timestamp getTalktime() {
		return talktime;
	}
	public void setTalktime(Timestamp talktime) {
		this.talktime = talktime;
	}
	public String getTalk() {
		return talk;
	}
	public void setTalk(String talk) {
		this.talk = talk;
	}
	
	
}
