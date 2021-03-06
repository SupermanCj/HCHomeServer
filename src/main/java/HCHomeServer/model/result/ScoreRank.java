package HCHomeServer.model.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import HCHomeServer.model.db.User;

/**
 * 积分排行返回数据管理类
 * 用于构建一个积分排行，每一个实例只能构建一个积分排行，若要构建第二个排行列表，需要先调用clear
 * 根据每一个项加入的顺序构建排行，暂不支持内部重排序
 * @author cj
 *
 */
@SuppressWarnings("unused")
public class ScoreRank implements Serializable {
	
	private static final long serialVersionUID = 6799154930806728730L;
	
	/**
	 * 内部类，一条积分排行的具体信息项
	 * @author cj
	 *
	 */
	static public class ScoreRankItem implements Serializable {
		
		private static final long serialVersionUID = 1824464383700837296L;
		//用户Id
		private int userId;
		//昵称
		private String nickname;
		//头像
		private String avatar;
		//积分
		private int signScore;
		//积分排名
		private int scoreRank;
		
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public int getSignScore() {
			return signScore;
		}
		public void setSignScore(int signScore) {
			this.signScore = signScore;
		}
		public int getScoreRank() {
			return scoreRank;
		}
		public void setScoreRank(int scoreRank) {
			this.scoreRank = scoreRank;
		}
	}

	//用户的积分排行信息
	private ScoreRankItem userScoreRank;
	//top积分排行信息列表
	private List<ScoreRankItem> scoreRankList;
	//当前排行名次，不做为打包返回的数据。
	private int currentRank;
	//构造函数
	public ScoreRank() {
		init();
	}
	//初始化管理类状态
	private void init() {
		this.currentRank = 1;
		this.scoreRankList = new ArrayList<>();
		this.userScoreRank = null;
	}
	public void clear() {
		init();
	}
	//生成用户的积分排行信息
	public void setUserScoreRankFromUser(User user,int scoreRank) {
		this.setUserScoreRank(this.buildItem(user, scoreRank));
	}
	//积分排行新增一个项
	public void addRankListItem(User user) {
		this.scoreRankList.add(this.buildItem(user, currentRank));
		currentRank += 1;
	}
	//积分排行新增一整个列表
	public void addRankList(List<User> users) {
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			this.addRankListItem(iterator.next());
		}
	}
	//将积分排行数据转换为合适的输出结构
	public Map<String, Object> convertToMap(){
		Map<String, Object> data = new HashMap<>();
		data.put("userScoreRank", this.userScoreRank);
		data.put("scoreRankList", this.scoreRankList);
		return data;
	}
	//构造一个积分排行信息项（内部类）
	private ScoreRankItem buildItem(User user, int scoreRank) {
		ScoreRankItem scoreRankItem=new ScoreRank.ScoreRankItem();
		scoreRankItem.setAvatar(user.getAvatar());
		scoreRankItem.setNickname(user.getNickname());
		scoreRankItem.setScoreRank(scoreRank);
		scoreRankItem.setSignScore(user.getSignScore());
		scoreRankItem.setUserId(user.getUserId());
		return scoreRankItem;
	}
	

	//getter and setter，私有
	private ScoreRankItem getUserScoreRank() {
		return userScoreRank;
	}
	private void setUserScoreRank(ScoreRankItem userScoreRank) {
		this.userScoreRank = userScoreRank;
	}
	private List<ScoreRankItem> getScoreRankList() {
		return scoreRankList;
	}
	private void setScoreRankList(List<ScoreRankItem> scoreRankList) {
		this.scoreRankList = scoreRankList;
	}
}
